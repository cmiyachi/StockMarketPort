/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.util.Map;
import java.util.Set;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author zyyang1
 */
public class PersistSymbolMysql implements PersistSymbol{

    private String url ;
    private String user;
    private String password;
    
    private String pkname;
    private String dbname;
    private String tablename;
    
    //Remove later//
    private String testtablename;
    private String testpkname;
    private List<String> testColumnNames;
    //////
    
    private Map<String, StockSymbol> dataContainer;
    private Map<String, StockSymbol> wip;
    
    private List<String> changed;
    private List<String> deleted;
    private List<String> columnNames;
    
    private int varchar;
    
    public PersistSymbolMysql() {
        url = "jdbc:mysql://localhost/umass308dev";
        user = "root";
        password = "root";
        
        pkname = "stockidpk";
        dbname = "x";
        tablename = "stocks";
        
        testtablename = "stocks";
        testpkname = "stockidpk";
        
        
        dataContainer = new HashMap<>();
        wip = new HashMap<>();
        changed = new ArrayList<>();
        deleted = new ArrayList<>();
        columnNames = new ArrayList<>();
        
        
       varchar = 60;
       
       //Get a description of the table we will be using
       //As columns could have been added
       //make sure that connection is possible
        
        
    }
    
    public PersistSymbolMysql(String user, String password) {
        this();
        this.user = user;
        this.password = password;
    }
    
    
    
    
        /**
     * Read a symbol.
     * @param symbol
     * @return
     */
    public Map<String,String> readSymbol(String symbol){
    
        boolean checkname = this.checkSymbolName(symbol);
        
        StockSymbol readsymbol = null;
        
        if (checkname) {
        
        
         try {
                Connection sqlcon = DriverManager.getConnection(url, user, password);
                String sqlcommand = "select * from " + tablename + " where " + pkname + "=\"" + symbol + "\"";
                
                Statement statement = sqlcon.createStatement();
                ResultSet result = statement.executeQuery(sqlcommand);
               
                
                
                //there should only be one record result based on our query
                while(result.next()){ 
                    
                     
                    readsymbol = new StockSymbol( result.getString(pkname));
                    
                    
                    //refernce column names by index rather than hardcoding
                    ResultSetMetaData rsmd = result.getMetaData();
                    
                    int columncount = rsmd.getColumnCount();
                    for(int count = 1; count <= columncount; count++){
                        
                        String column = rsmd.getColumnName(count);
                        String value = result.getString(column);
                        readsymbol.setPair(column, value);
                        
                    }
                    
                    //added to wip as the user can modify this data if needed
                    wip.put(result.getString(pkname), readsymbol);
        
                    
                    
                }
                
                sqlcon.close();
                
               
        }
        catch(Exception doh) {
                System.out.println(doh.getMessage());
        }
        
       
        
        }
        
        //per the interface, return a hash table representing the row
        //user can update this id needed
        return (readsymbol==null) ? null : readsymbol.getHashTable();
    }

    /**
     * Create a Symbol in memory.  
     * @param symbol
     * @return
     */
    public Map<String,String> createSymbol(String symbol){
    
        //dont let user create symbol if it already exists
        //don't let user create non alphanumeric symbol name
        boolean alreadyexists = false;
        boolean checkname = false;
        
        alreadyexists = this.checkIfRecordExists(symbol);
        checkname = this.checkSymbolName(symbol);
        
        if (alreadyexists || !checkname) {
            return null;
        }
        else {
        
        
            StockSymbol newsymbol = new StockSymbol(symbol);
            wip.put(symbol,newsymbol);
            return newsymbol.getHashTable();
        }
    }

    /**
     * Delete a symbol.
     * @param symbol
     */
    public void deleteSymbol(String symbol) {
        boolean nameok  = this.checkSymbolName(symbol);
        
        boolean existok  = this.checkIfRecordExists(symbol);
        if (existok && nameok) {
            
            try{
                Connection sqlcon = DriverManager.getConnection(url, user, password);
                
                String deletecom ="delete from " + tablename + " where " + pkname + "=\"" + symbol + "\"";
                Statement statement = sqlcon.createStatement();
                boolean resultset = statement.execute(deletecom);
                        
                sqlcon.close();
            }
            catch(Exception doh) {
                System.out.println(doh.getMessage());
            }
        }
    
    }

    
    /**
     * Save the symbol and persist it into the mysql database;
     * @param symbol
     */
    public void saveSymbol(String symbol){
    
        
        StockSymbol savesymbol = wip.get(symbol);
        Connection sqlcon;
        
  
        
        try{
            sqlcon = DriverManager.getConnection(url, user, password);
    
            //Check the fields used by savesymbol against the columns in the sql table
            //Sql table must create any needed fields
            checkAndCreateMissingColumnNames(sqlcon, savesymbol);
            
     
           String command = null;
           //create new record
           if (! checkIfRecordExists(sqlcon, savesymbol)) {
            
               command = generateCreateCmd(savesymbol);
             
           }
                
          //update record
           else {
               command = this.generateUpdateCmd(savesymbol);
               
         
           }
            
           Statement statement = sqlcon.createStatement();
           statement.execute(command);
           System.out.println(command);
           sqlcon.close();
        }
        catch(Exception doh) {
            System.out.println("-E-" + doh.getMessage());
        }
     
         
    
    }
    
    
    private boolean checkSymbolName(String symbolname) {
        boolean results = true;
        
        if(symbolname == null) {
            return false;
        }
        if(! symbolname.matches("[a-zA-Z0-9]+")){
            results = false;
        }
        return results;
    }

    /**
     * Get list of symbols;
     * @return
     */
    public Set<String>  getSymbols(){
        return dataContainer.keySet();
    }

    
    /**
     * Read from the database;
     * @param dborfile
     * 
     * Loads contents of database.table into memory.
     * Should be ok, as the tables are not that large anyway.
     */
    public void readFromDB(String dborfile){
    
        try {
            Connection sqlcon = DriverManager.getConnection(url, user, password);
            
            boolean existornot = this.checkIfTableExists(sqlcon);
            System.out.println(existornot);
            
            //read
            try {
                Statement statement = sqlcon.createStatement();
                ResultSet resultset = statement.executeQuery("select * from " + tablename);
                
                
                while(resultset.next()) {
                    //System.out.println("pk:" + resultset.getString(pkname));
                    
                    ResultSetMetaData rsmd = resultset.getMetaData();
                    
                    
                    
                    StockSymbol symbol = new StockSymbol( resultset.getString(pkname));
                    int numcol = rsmd.getColumnCount();
                    
                    for (int count = 1; count <= numcol; count++) {
                        //System.out.println(rsmd.getColumnName(count));
                        String column = rsmd.getColumnName(count);
                        String value = resultset.getString(column);
                        symbol.setPair(column, value);
                        
                    }
                    
                }
                
            }
            finally {
                sqlcon.close();
            }
        }
        catch (Exception doh) {
            System.out.println(doh.getMessage());
        }
       
    
    }
    
    private String generateUpdateCmd(StockSymbol savesymbol) {
        String result = null;
        
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
          
        StringBuilder  command = new StringBuilder("update " + tablename + " set ");
        
        int count  = 0;
        for(String symbolcolumn : savesymbol.getAllColumnNames()){
        
                
            if (! symbolcolumn.equals(this.pkname)) {
                if (count > 0) {
                    command.append(", ");
                }    
                command.append(symbolcolumn + "=\"" + savesymbol.getColumnValue(symbolcolumn) +"\"");
                count++;       
            }
 
        
        }
        command.append(" where "+ pkname +"=\"" + savesymbol.getName() + "\"");
        result = command.toString();
        
        
        return result;
    }
    
    
    
    //Generates the sql command to create a record into the sql table.
    private String generateCreateCmd(StockSymbol savesymbol) {
        
        String result = null;
        
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
            
        for(String symbolcolumn : savesymbol.getAllColumnNames()){
        
                
                if (! symbolcolumn.equals(this.pkname)) {
                        keys.add(symbolcolumn);
                        values.add(savesymbol.getColumnValue(symbolcolumn));
                       
                }
 
            }
        
           StringBuilder keycom = new StringBuilder("(");
           StringBuilder valuecom = new StringBuilder("(");
                
           keycom.append(pkname);
           valuecom.append("\"" + savesymbol.getName() + "\"");
                
                
            
            for (int index = 0; index < keys.size(); index++) {
                    if (! keys.get(index).equals(pkname)) { //don't duplicate primary key in command
                        
                        
                        keycom.append("," + keys.get(index));
                        valuecom.append("," + "\"" + values.get(index) + "\"");
                        
                    }
                   
            }

            keycom.append(")");
            valuecom.append(")");
 
               
            String sqlcom = "insert into " + this.tablename +" "+ keycom  + " values " + valuecom;
                
        
            return sqlcom;
    }
    
    
    
    //overloaded
    private boolean checkIfRecordExists(String symbol) {
        
        boolean result = false;
        
        try {
        
            Connection sqlcon = DriverManager.getConnection(url, user, password);
            
            StockSymbol temp = new StockSymbol(symbol);
            result = checkIfRecordExists(sqlcon, temp);
            
            sqlcon.close();
        }
        catch(Exception doh) {
            System.out.println(doh.getMessage());
        }
        
        return result;
    }
    
    //overloaded
    private boolean checkIfRecordExists(Connection sqlcon, StockSymbol symbol) {
        
        boolean result = false;
        
        String sqlcommand = "select * from " + tablename + " where " + pkname + "=\"" + symbol.getName() + "\"";
        try {
            Statement statement = sqlcon.createStatement();
            ResultSet resultset = statement.executeQuery(sqlcommand);
            while(resultset.next()) {
                result = true;
            }
        }
        catch(Exception doh) {
            System.out.println(doh.getMessage());
        }
        return result;
    }
    
    //Overloaded 
    private boolean checkIfTableExists(){
        return false;
    }
    
    //Overloaded
    private boolean checkIfTableExists(Connection sqlcon) {
        
        boolean result = false;
        try {
            DatabaseMetaData dmd = sqlcon.getMetaData();
            ResultSet tables = dmd.getTables(null, null, tablename, null);
            if (tables.next()) {
                result = true;
                checkColumnNames(sqlcon);
            }
            else {
                //create the table
                String createtable = "create table " + this.tablename + " (" +
                                        testpkname + " varchar(" + varchar + ") not null, " +
                                        "primary key (" + testpkname + ")"
                                                                          + " )";
                
                Statement statement = sqlcon.createStatement();
                statement.execute(createtable);
            }
        }
        catch(Exception doh) {
            System.out.println("-e checkIfTableExists- " + doh.getMessage());
        }
        
        return result;
    }
    
    
    //Current development scenario allows developer to add columns names as they see fit
    //So need to see what columns have been added by the developer
    private void checkColumnNames(Connection sqlcon) {

        System.out.println("CheckColumnNames:" + sqlcon);
        System.out.println("tablename:" + tablename);
        try{
            DatabaseMetaData dmd = sqlcon.getMetaData();
            //wildcarding in sql is %
            ResultSet columns = dmd.getColumns(null, "%", tablename, "%");
            while(columns.next()){
                String column = columns.getString("COLUMN_NAME");
                columnNames.add(column);
                System.out.println("column:" + column);
            }
        }
        catch(Exception doh) {
            System.out.println("-doh-" + doh.getMessage());
        }
    }
    
    private void checkAndCreateMissingColumnNames(Connection sqlcon, StockSymbol savesymbol) {
         
        try{
             for(String symbolcolumn : savesymbol.getAllColumnNames()){
                if (! this.columnNames.contains(symbolcolumn)){
                    String addcolumn = "alter table " + this.testtablename +
                                       " add " + symbolcolumn + 
                                       " varchar(" + this.varchar + ")";
                    
                    System.out.println("add column:" + addcolumn);
                    Statement statement = sqlcon.createStatement();
                    statement.execute(addcolumn);
                    columnNames.add(symbolcolumn);
                    
                  
                    
                }

            }
        }
        catch(Exception doh) {
            System.out.println(doh.getMessage());
        }
    }
    
 

    
    /** Kept for backward compability with interface
     * Re-Write to database that you read from;
     */
    public void writeDB(){}

    /**
     * Kept for backward compatibility with interface
     * Write to database 
     * @param dborfile
     */
    public void writeDB(String dborfile){}
    
}
