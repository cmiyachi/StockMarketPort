/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 *
 * @author zyyang1
 */
public class PersistSymbolMysql implements PersistSymbol{

    
    //Databse Connection Settings
    private String url ;
    private String user;
    private String password;
    
    //Stock Table Settings
    private String pkname;
    private String dbname;
    private String tablename;
    
    //User Table Settings
    private String currentuser ;
    private String tablenameusers ;
   
    
    //Join table settings
    private String tablenamejoin;
    
    private Map<String, StockSymbol> wip;
    
    
    private List<String> columnNames;
    
    private int varchar;
    
    private Map<String, Map<String,String>> connections;
    
    private boolean productionMode;
    private boolean testMode;
    
    public PersistSymbolMysql() {
        
        
        url = "jdbc:mysql://localhost:3306/umass308dev";
        user = "root";
        password = "root";
        
        //stocks table
        tablename = "production_table"; //default table used to store stocks (ie. stock symbol)
        //primary key field used in the database for stocks
        pkname = "stockidpk";
        
        //users table (thinking ahead for multiple users)
        tablenameusers = "users";
        currentuser = "default";
        
        
        // default joined table name by joining users and stocks (thinking ahead for multiple users)
        tablenamejoin = "join_"+ tablenameusers +"_" + tablename;
        
        
        
        
        //store any WorkInProgress Stock Symbols into memory
        wip = new HashMap<>();
        
         
        columnNames = new ArrayList<>();
        connections = new HashMap<String, Map<String, String>>();
        
        
       varchar = 60;
       
       
       
        
       productionMode = true;
       testMode = false;
        
       //load up connection properties
       this.readConnectionProperties();
       this.setConnectionProperties();
       
     
      
      
    }
    
    public PersistSymbolMysql(String user, String password) {
        this();
        this.user = user;
        this.password = password;
    }
    
    
    public void setTestMode() {
        productionMode = false;
        testMode = true;
        tablenamejoin = "join_"+ tablenameusers +"_" + tablename;
        
        //reading override file trumps the connection settings
        this.setConnectionProperties();
         
        
    }
    
    public void setProductionMode() {
        productionMode = true;
        testMode = false;
        tablenamejoin = "join_"+ tablenameusers +"_" + tablename;
        
        //reading the override file trumps the connection settings
        this.setConnectionProperties();
         
         
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
                System.out.println("-E- readSymbol:" + doh.getMessage());
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
        
        boolean existok  = this.checkIfRecordExists(symbol); //check if this stockid actually exists
        if (existok && nameok) {
            
            try{
                Connection sqlcon = DriverManager.getConnection(url, user, password);
                
                String deletecom ="delete from " + tablename + " where " + pkname + "=\"" + symbol + "\"";
                Statement statement = sqlcon.createStatement();
                boolean resultset = statement.execute(deletecom);
                
                //once this symbol is removed from the stocks table, remove it from the join table
                deleteSymbolJoin(symbol, sqlcon);
                
                sqlcon.close();
            }
            catch(Exception doh) {
                System.out.println("-E- deleteSymbol:" + doh.getMessage());
            }
        }
    
    }
    
    //Once this symbol is removed, the join table should not contain contain it either
    public void deleteSymbolJoin(String symbol, Connection sqlcon) {
        
         
        
        String deletecom ="delete from " + tablenamejoin + " where userid" + "=\"" + currentuser+ "\"" +
                " and stockid=" + "\"" + symbol +"\"";
        
        try{
            Statement statement = sqlcon.createStatement();
            statement.execute(deletecom);
        }
        catch(Exception doh) {
            System.out.println("-E- deleteSymbolJoin:" + doh.getMessage());
        }
    }

    
    /**
     * Save the symbol and persist it into the mysql database;
     * @param symbol
     */
    public void saveSymbol(String symbol){
    
        //Check if table exists and allow to create table if it doesn't exist
        checkIfTableExists(tablename); //ie. the stocks table
        checkOrCreateUsersTable(); // users
        checkOrCreateJoinTable();//joined users and stocks
        
        //check before save whether user is in the database
        boolean checkuser = checkIfUserExists(currentuser);
        
        
        
        StockSymbol savesymbol = wip.get(symbol);
        Connection sqlcon;
        
  
        boolean needcreatejoin = false;
        
        try{
            sqlcon = DriverManager.getConnection(url, user, password);
    
            //Check the fields used by savesymbol against the columns in the sql table
            //Sql table must create any needed fields
             
            checkAndCreateMissingColumnNames(sqlcon, savesymbol);
            
     
           String command = null;
           //create new record
           if (! checkIfRecordExists(sqlcon, savesymbol)) {
            
               command = generateCreateCmd(savesymbol);
               needcreatejoin = true;
             
           }
                
          //update record
           else {
               command = this.generateUpdateCmd(savesymbol);
               
         
           }
            
           Statement statement = sqlcon.createStatement();
           statement.execute(command);
           
           if (needcreatejoin) {
               //persist to the join table too since you are creating this record as a currentuser
               saveSymbolInJoinTable(symbol, sqlcon);
           }
           
           sqlcon.close();
        }
        catch(Exception doh) {
            System.out.println("-E- saveSymbol:" + doh.getMessage());
        }
     
         
    
    }
    
    private void saveSymbolInJoinTable(String symbol, Connection sqlcon) {
        
         String createuser = "insert into " + tablenamejoin +" (userid,stockid)  values (" 
                        + "\"" + currentuser + "\"," +   "\"" + symbol+ "\"" + ")";
             
         try{
            Statement statement = sqlcon.createStatement();
            statement.execute(createuser);}
         catch (Exception doh) {
            System.out.println("-E- saveSymbolInJoinTable:" + doh.getMessage());
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
     
        String sqlcommand = "select * from " + tablename ;
        Set <String> result  = new HashSet<>();
        try {
            Connection sqlcon = DriverManager.getConnection(url, user, password);
            Statement statement = sqlcon.createStatement();
            ResultSet resultset = statement.executeQuery(sqlcommand);
            while(resultset.next()) {
                result.add( resultset.getString(pkname));
            }
        }
        catch(Exception doh) {
            System.out.println("-E- checkIfRecordExists:" + doh.getMessage());
        }
        
        
        return result;
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
            System.out.println("-E- checkIfRecordExists:" + doh.getMessage());
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
            System.out.println("-E- checkIfRecordExists:" + doh.getMessage());
        }
        return result;
    }
    
    
    
    
    
    private boolean checkIfUserExists(String someuser) {
        boolean result = false;
        
        try{
            Connection sqlcon = DriverManager.getConnection(url, user, password);
            String sqlcommand = "select * from " + tablenameusers + " where userid" + "=\"" + someuser + "\"";
            
            Statement statement = sqlcon.createStatement();
            ResultSet resultset = statement.executeQuery(sqlcommand);
            while(resultset.next()) {
                result = true;
            }
            
            if (!result) {
                //create the user
                String createuser = "insert into " + tablenameusers +" (userid,username)  values (" 
                        + "\"" + currentuser + "\"," +   "\"" + currentuser + "\"" + ")";
                
                statement.execute(createuser);
            }
            sqlcon.close();
        }
        
        catch(Exception doh){
            System.out.println("-E- checkIfUserExists:" + doh.getMessage());
        }
        return result;
        
        
    }
    
    //Overloaded ....
    private boolean checkIfTableExists(String tablename){
        
        boolean result = false;
        try {
            
            
            Connection sqlcon = DriverManager.getConnection(url, user, password);
            result = checkIfTableExists(sqlcon);
        }
        catch(Exception doh) {
            System.out.println("-E- checkIfTableExists:" + doh.getMessage());
        }
        return result;
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
                                        pkname + " varchar(" + varchar + ") not null, " +
                                        "primary key (" + pkname + ")"
                                                                          + " )";
                
                Statement statement = sqlcon.createStatement();
                statement.execute(createtable);
            }
        }
        catch(Exception doh) {
            System.out.println("-E- checkIfTableExists: " + doh.getMessage());
        }
        
        return result;
    }
    
    
    private void checkOrCreateUsersTable() {
        boolean result = false;
        
     
        try {
            Connection sqlcon = DriverManager.getConnection(url, user, password);
            DatabaseMetaData dmd = sqlcon.getMetaData();
            ResultSet tables = dmd.getTables(null, null, tablenameusers, null);
            if (tables.next()) {
                result = true;
               
            }
            else {
                //create the table
                String createtable = "create table " + tablenameusers + " (" +
                                        "userid" + " varchar(" + varchar + ") not null, " +
                                        "username" + " varchar(" + varchar + ") not null, " +
                                        "primary key (" + "userid" + ")"
                                                                          + " )";
                
                Statement statement = sqlcon.createStatement();
                statement.execute(createtable);
            }
            sqlcon.close();
        }
        catch(Exception doh) {
            System.out.println("-E- checkOrCreateUsersTable: " + doh.getMessage());
        }
    }
    
    
    
    
   private void checkOrCreateJoinTable() {
        boolean result = false;
        
     
        try {
            Connection sqlcon = DriverManager.getConnection(url, user, password);
            DatabaseMetaData dmd = sqlcon.getMetaData();
            ResultSet tables = dmd.getTables(null, null, tablenamejoin, null);
            if (tables.next()) {
                result = true;
               
            }
            else {
                //create the table
                String createtable = "create table " + tablenamejoin + " (" +
                                        "userid" + " varchar(" + varchar + ") not null, " +
                                        "stockid" + " varchar(" + varchar + ") not null, " +
                                        "primary key (" + "userid, stockid" + ")"
                                                                          + " )";
                
                Statement statement = sqlcon.createStatement();
                statement.execute(createtable);
            }
        }
        catch(Exception doh) {
            System.out.println("-E- checkOrCreateUsersTable: " + doh.getMessage());
        }
    }
    
    
        
    
    //Current development scenario allows developer to add columns names as they see fit
    //So need to see what columns have been added by the developer
    private List<String> checkColumnNames(Connection sqlcon) {

        ArrayList<String> columnList = new ArrayList<>();
       
        try{
            DatabaseMetaData dmd = sqlcon.getMetaData();
            //wildcarding in sql is %
            ResultSet columns = dmd.getColumns(null, "%", tablename, "%");
            while(columns.next()){
                String column = columns.getString("COLUMN_NAME");
                columnList.add(column);
                
            }
        }
        catch(Exception doh) {
            System.out.println("-E- checkColumnNames:" + doh.getMessage());
        }
        
        return columnList;
    }
    
    private void checkAndCreateMissingColumnNames(Connection sqlcon, StockSymbol savesymbol) {
         
        try{
            
             List<String> columnList = this.checkColumnNames(sqlcon);
             for(String symbolcolumn : savesymbol.getAllColumnNames()){
                if (! columnList.contains(symbolcolumn)){
                    String addcolumn = "alter table " + this.tablename +
                                       " add " + symbolcolumn + 
                                       " varchar(" + this.varchar + ")";
                    
                    
                    Statement statement = sqlcon.createStatement();
                    statement.execute(addcolumn);
                     
                    
                  
                    
                }

            }
        }
        catch(Exception doh) {
            System.out.println("-E- checkAndCreateMissingColumnNames:" + doh.getMessage());
        }
    }
    
    
    
    public boolean checkSqlConnection() {
        
        boolean result = false;
        
        try {
            Connection sqlcon = DriverManager.getConnection(url, user, password);
            if (sqlcon != null) {
                result = true;
                sqlcon.close();
            }
            
        }
        catch(Exception doh){
            System.out.println("-E- checkSqlConnection:" + doh.getMessage());
        }
        
        return result;
    }
    
    
    private void readConnectionProperties() {
        PropertiesParserForMysql properties = new PropertiesParserForMysql();
        connections = properties.getConnectionTable();
        
        
        
       
    }
    
    private void setConnectionProperties() {
        String selection = (productionMode) ? "PRODUCTION" : "TEST";
        
        if (connections.containsKey(selection)) {
            for (String key : connections.get(selection).keySet()){
                if (key.equals("url")){
                    
                    url = connections.get(selection).get(key);
                }
                else if (key.equals("user")){
                  
                    user = connections.get(selection).get(key);
                }
                else if (key.equals("password")){
                    
                    password = connections.get(selection).get(key);
                }
                else if (key.equals("tablename")){
                    
                    tablename = connections.get(selection).get(key);
                }
                else if (key.equals("tablenamejoin")) {
                    tablenamejoin = connections.get(selection).get(key);
                }
                else if (key.equals("tablenameusers")) {
                    tablenameusers = connections.get(selection).get(key);
                }
                
            }
        }
        
        
        
    }
    
 
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    
    public void setUrl(String url){
        this.url = url;
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
  
    
        /**
     * Read from the database;
     * @param dborfile
     * 
     * Kept for backward compatibility with interface
     * Loads contents of database.table into memory.
     * Should be ok, as the tables are not that large anyway.
     */
    public void readFromDB(String dborfile){}
}

   