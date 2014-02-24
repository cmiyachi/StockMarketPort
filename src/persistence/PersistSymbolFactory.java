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
    
    
        public static void readPropertiesFile() {
        //The file shoud be relative to this app
        //XML format
        
        File basedir = new File (".");
        
        try {
            String path = basedir.getCanonicalPath();
            System.out.println("Iam here:" + path);
            String propfile = path + "\\mysqlconn.xml";
            
            File xmlfile = new File(propfile);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlfile);
            
            NodeList nlist = doc.getElementsByTagName("connection");
            
            
            //Get the <connection> properties
            for (int count = 0; count < nlist.getLength(); count++) {
                Node node =nlist.item(count);
            
                if(node.getNodeType()==Node.ELEMENT_NODE){
                    
                    Element element = (Element) node;
                    String connid = element.getAttribute("id");
                    
                    //slurp in all the connection properties like password, user, url
                    NodeList tags = element.getElementsByTagName("*");
                    
                    for (int cnt = 0; cnt < tags.getLength(); cnt++) {
                        Element atag = (Element) tags.item(cnt);
                        String atagname = atag.getNodeName();
                        
                       
                        if(atagname.equals("user")){
                            user=element.getElementsByTagName(atagname).item(0).getTextContent();
                        }
                        else if (atagname.equals("password")){
                            password=element.getElementsByTagName(atagname).item(0).getTextContent();
                        }
                        else if (atagname.equals("url")){
                            url=element.getElementsByTagName(atagname).item(0).getTextContent();
                        }
   
                    }
                    
                }  
              
                
            }
        }
        catch (Exception doh) {
            System.out.println(doh.getMessage());
        }
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
