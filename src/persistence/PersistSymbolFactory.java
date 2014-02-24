/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
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
public class PersistSymbolFactory {
    
    
    //Only used for Mysql implementation
    private static String user = "init";
    private static String password = "init";
    private static String url = "init";
    private static String table = "init";

    public static enum modes{
        PRODUCTION, TEST
    }
    
    
    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUser(String user) {
        PersistSymbolFactory.user = user;
    }

    public static void setPassword(String password) {
        PersistSymbolFactory.password = password;
    }

    public static void setUrl(String url) {
        PersistSymbolFactory.url = url;
    }
    
    
    
    
    
    public static PersistSymbol create() {
        
        // start off with XML
        return new PersistSymbolXML();
    }
    
    //hardcoded right now
    public static boolean checkSqlConnection() {
        
        boolean result = false;
        PersistSymbolMysql sql = new PersistSymbolMysql();
        result = sql.checkSqlConnection();
        
        return result;
    }
    
    
        
        
    //use enum for the arguement     
    public static PersistSymbol createMySql(PersistSymbolFactory.modes value) {
        
        
        PersistSymbolMysql persistence = new PersistSymbolMysql();
       
        
        
       
        if (value == PersistSymbolFactory.modes.TEST){
            persistence.setTestMode();
            ;
        }
        else if (value == PersistSymbolFactory.modes.PRODUCTION){
            persistence.setProductionMode();
             
        }
        
       
        
        
            
        return persistence;
        
    }

}
