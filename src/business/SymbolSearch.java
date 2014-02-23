package business;



/**
 * Created by Ivan on 09/02/14.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Uses SymbolSearch.xml (companies' names with matching symbols)
 * @returns list of all companies (could be use in ComboBox)
 * @return company's symbol matching company's name.
 * @return List of matching records (not case sensitive)(could be used in ComboBox)
 */

public class SymbolSearch {



    private Map<String, String> mapNameSymbol = new HashMap<String, String>();
    private List<String> listNames;


    /**
     * Creates HashMap from SymbolSearch.xml -> Map<"Any Company name","Any Symbol">
     *
     * Creates sorted List with company names
     */

    public SymbolSearch(){

        try {
            File fXmlFile = new File(".\\SymbolSearch.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Company");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    mapNameSymbol.put(eElement.getElementsByTagName("Name").item(0).getTextContent(),eElement.getAttribute("id"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        listNames = new ArrayList<String>(mapNameSymbol.keySet());
        Collections.sort(listNames, String.CASE_INSENSITIVE_ORDER);
    };

    /*
    @returns list of all companies
     */


    public List<String> getNamesList(){
        return listNames;
    }

    /**
     *
     * @param companyName
     * @return company's symbol matching company's name.
     */
    public String getSymbol(String companyName){
        String symbol = mapNameSymbol.get(companyName);
        return symbol;
    }

    /*
    gets first letters of the company's name
   @return List of matching records (not case sensitive)
     */
    public List<String> getMatchingNames(String letters){
        List<String> result = new ArrayList<>();
        for(String name : listNames){
            if (name.toLowerCase().startsWith(letters.toLowerCase())) result.add(name);

        }
        return result;
    }

}