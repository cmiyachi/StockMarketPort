/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 * For iteration 1, the persistence is implemented using XML.
 * 
 * @author zyyang1
 */
public class PersistSymbolXML implements PersistSymbol{
    
    
    /*
    These hash tables store the data in memory.
    
    The dataContainer contains the data that is read directly from the XML,
    or that is written to the XML.
    
    The wip contains user data that is work in progress.
    The wip is merged into dataContainer when the data is ready to be persisted,
    */
    
    /*Data Structure of Hash Table.
      The key is Stock Symbol Name.
      The value is a another hash table containing Stock symbol data (string field, string data).
    */
    private Map<String, Map<String,String>> dataContainer;
    private Map<String,Map<String,String> > wip;  
    
    
    //The xmlfile that will persist the stock symbol data.
    private String xmlfile;

    /**
     * Default Constructor.
     */
    public PersistSymbolXML() {
        
        //Initialize the hashes.
        dataContainer = new HashMap<String,Map<String,String>>();
        wip = new HashMap<String,Map<String,String>>();
    }
    
    /**
     * Deletes the stock from the dataContainer data structure.
     * @param symbol
     */
    @Override
    public void deleteSymbol(String symbol) {
        dataContainer.remove(symbol);
        
    }

    /**
     * Persists the stock to the datacontainer.
     * @param symbol
     */
    @Override
    public void saveSymbol(String symbol) {
        
        //updates our dataContainer hash
        
        if( wip.containsKey(symbol)) {
            dataContainer.put(symbol, wip.get(symbol));
        }
        
        
        
    }

    /**
     * Return to user the Set of Stock Symbol names.
     * @return
     */
    @Override
    public Set<String> getSymbols() {
        
        //Stock symbol names are the keys to the dataContainer hash table.
        return dataContainer.keySet();
        
    }

    /**
     * Read stock symbol data.  Stock symbol data is represented as a hash table.
     * @param symbol - This is the stock symbol name.
     * @return - Stock symbol data.  Stock symbol data is represented as a hash table.
     */
    @Override
    public Map<String,String> readSymbol(String symbol) {
        
        //Return a copy of the data (based om symbol name) so user can read or update it
        //Otherwise, if symbol name does not exist in dataContainer, return null
        HashMap<String,String> data = new HashMap<String, String>();
        Map<String,String> hashtable = dataContainer.get(symbol);
        
        if (hashtable != null) {
        
            for (String key : hashtable.keySet()) {
                data.put(key, hashtable.get(key));
            }   
        
            wip.put(symbol, data);
         
            return wip.get(symbol);
        }
        else {
            return null;
        }
    }
 
    /**
     * Creates Stock symbol data.
     * @param symbol - This is the stock symbol name.
     * @return - Hash table that user can modify.
     */
    @Override
    public Map<String,String> createSymbol(String symbol) {
        
        //Make sure that stock symbol is alpha numeric.
        if(symbol != null && symbol.matches("[a-zA-Z0-9]+")){
            HashMap <String, String> data = new HashMap<String, String>();
            wip.put(symbol, data);
        
            return data;
        }
        else {
            return null;
        }
        
    }

    /**
     * Read all Stock symbol data from the XML file and populate the data into the dataContainer.
     * @param file - Location to the XML.
     * 
     * 
     * 
     */
    @Override
    public void readFromDB(String file) {
        
        try{
            this.xmlfile = file;
            File xmlfile = new File(file);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlfile);
            
            
            NodeList nlist = doc.getElementsByTagName("stock");
            
            
            //Get all the stock entries
            for (int count = 0 ; count < nlist.getLength(); count++) {
            
                Node node = nlist.item(count);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    
                    Element element = (Element) node;
                    String stockid = element.getAttribute("id");
                    
                    
                    
                    
                    //Find tags... keep tag algorithm generic here to allow flexibility for evolving tag names.
                    //That's why we use "*" to query tag names.
                    NodeList tags = element.getElementsByTagName("*");
                    HashMap<String,String> realdata = new HashMap<String, String>();
                    for (int cnt = 0; cnt < tags.getLength(); cnt++) {
                        Element atag = (Element) tags.item(cnt);
                        String atagname = atag.getNodeName();
                      
                        realdata.put(atagname,element.getElementsByTagName(atagname).item(0).getTextContent() );
                    }
                    
                    dataContainer.put(stockid, realdata);
                
                }
                
               
                
            }
            
        }
        catch(Exception doh) {
            System.out.println(doh.getMessage());
        }
    }
    
    
    

    /**
     * Overloaded method.
     * Commits changes and re-writes to the same XML file you read from.
     */
    @Override
    public void writeDB() {
        writeDB(this.xmlfile);
    }
    
    /**
     * Commit changes and writes to the xml file that is specified.
     * @param file
     */
    @Override
    public void writeDB(String file) {
        File outfile = new File(file);
        
        
        try {
            if (! outfile.exists()) {
                outfile.createNewFile();
            }
            
            FileWriter fwt = new FileWriter(outfile.getAbsoluteFile());
            BufferedWriter bwt = new BufferedWriter(fwt);
            
            //write contents of dataCOntainer out to the XML file
            bwt.write("<?xml version=\"1.0\"?>");
            bwt.newLine();
            
            bwt.write("<portfolio>");
            bwt.newLine();
            
            for(String stock : dataContainer.keySet()){
                bwt.write("\t\t<stock id=\"" + stock + "\">");
                bwt.newLine();
                
                Map<String,String> data = dataContainer.get(stock);
                for(String tag : data.keySet()){
                    
                    bwt.write("\t\t\t<"+tag+">"  + data.get(tag ) +  "</"+tag+">");
                    bwt.newLine();
                    
                }
                
                bwt.write("\t\t</stock>");
                bwt.newLine();
            }
            
            bwt.write("</portfolio>");
            bwt.newLine();
            bwt.close();
            
        }
        catch(Exception doh) {
            System.out.println(doh.getMessage());
        }
        
    }
    
  
}
