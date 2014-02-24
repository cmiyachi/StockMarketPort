/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
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
public class PropertiesParserForMysql {
    
    
    private Map<String, Map<String,String>> hashtable;
    
    public PropertiesParserForMysql() {
        hashtable = new HashMap<String, Map<String,String>>();
        readPropertiesFile();
    }
    
    public Map<String, Map<String, String> > getConnectionTable() {
        return hashtable;
    }
    
    public void readPropertiesFile() {
        //The file shoud be relative to this app
        //XML format
        
        File basedir = new File (".");
        
        try {
            String path = basedir.getCanonicalPath();
             
            String propfile = path + "\\conf\\mysqlconn.xml";
            
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
                    
                    HashMap<String,String> temp = new HashMap<>();
                    
                    //slurp in all the connection properties like password, user, url
                    NodeList tags = element.getElementsByTagName("*");
                    
                    for (int cnt = 0; cnt < tags.getLength(); cnt++) {
                        Element atag = (Element) tags.item(cnt);
                        String atagname = atag.getNodeName();
                        
                        temp.put(atagname,element.getElementsByTagName(atagname).item(0).getTextContent() );
                       
                     
   
                    }
                    
                    hashtable.put(connid, temp);
                    
                }  
              
                
            }
        }
        catch (Exception doh) {
            System.out.println("-I- readPropertiesFile to override sql connections:" + doh.getMessage());
        }
    }
    
    
    public static void main(String[] args) {
        PropertiesParserForMysql parser = new PropertiesParserForMysql();
        Map<String, Map<String,String>> hash = parser.getConnectionTable();
        
        ;
        for (String connid : hash.keySet()){
            System.out.println(connid);
            for (String key : hash.get(connid).keySet()){
                String value = hash.get(connid).get(key);
                
                System.out.println(key + value);
            }
            
        }
    }
        
}
