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
 *
 * @author zyyang1
 */
public class PersistSymbolXML implements PersistSymbol{
    
    private Map<String, Map<String,String>> dataContainer;
    private Map<String,Map<String,String> > wip;  //anything work in progresss
    
    private String xmlfile;

    /**
     *
     */
    public PersistSymbolXML() {
        dataContainer = new HashMap<>();
        wip = new HashMap<>();
    }
    
    /**
     * Deletes the stock.
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
     * Get all the symbols in the db.
     * @return
     */
    @Override
    public Set<String> getSymbols() {
        
        
        return dataContainer.keySet();
        
    }

    /**
     * Read a stock
     * @param symbol
     * @return
     */
    @Override
    public Map<String,String> readSymbol(String symbol) {
        
        //return a copy of the data so user can play with it
        //otherwise, if symbol does not exist in dataContainer, return null
        HashMap<String,String> data = new HashMap<>();
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
     * Creates a stock.
     * @param symbol
     * @return
     */
    @Override
    public Map<String,String> createSymbol(String symbol) {
        
        if(symbol != null && symbol.matches("[a-zA-Z0-9]+")){
            HashMap <String, String> data = new HashMap<>();
            wip.put(symbol, data);
        
            return data;
        }
        else {
            return null;
        }
        
    }

    /**
     * Read from XML;
     * @param file
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
            
            //For right now, dont make xml too recursive
            
            //get all the stock entries
            for (int count = 0 ; count < nlist.getLength(); count++) {
            
                Node node = nlist.item(count);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    
                    Element element = (Element) node;
                    String stockid = element.getAttribute("id");
                    
                    
                    
                    
                    //find tags... keep tag algorithm generic here to allow flexibility for evolving strings
                    NodeList tags = element.getElementsByTagName("*");
                    HashMap<String,String> realdata = new HashMap<>();
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
     * Commits changes and re-writes to the same file you read from.
     */
    @Override
    public void writeDB() {
        writeDB(this.xmlfile);
    }
    
    /**
     * Commit changes and writes to the xml file.
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
            
            //write contents of dataCOntainer out
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
