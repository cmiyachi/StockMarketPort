/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author zyyang1
 */
public class PersistSymbolFactory {
    
    private static String user = "root";
    private static String password = "root";
    private static String url = "jdbc:mysql://localhost/umass308dev";
    
    
    
    public static PersistSymbol create() {
        
        // start off with XML
        return new PersistSymbolXML();
    }
    
    //hardcoded right now
    public static boolean checkSqlConnection() {
        
        boolean result = false;
        
        try {
            Connection sqlcon = DriverManager.getConnection(url, user, password);
            if (sqlcon != null) {
                result = true;
                sqlcon.close();
            }
        }
        catch(Exception doh){
            System.out.println("-E-" + doh.getMessage());
        }
        
        return result;
    }
}
