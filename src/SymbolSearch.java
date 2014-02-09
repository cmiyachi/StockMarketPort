/**
 * Created by Ivan on 09/02/14.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class SymbolSearch {


    private Map<String, String> mapNameSymbol = new HashMap<String, String>();
    private List<String> listNames;


 /*
 * Gets Map from XLSX file
    * Puts Key values(companies' names) into List
  */

    public SymbolSearch( /*Map fromXLSX*/){
        mapNameSymbol.put("Tiffany & Co.","TIF");
        mapNameSymbol.put("Response Genetics, Inc", "RGDX"  );
        mapNameSymbol.put("Barclays ETN+ FI Enhanced Glb Hi Yld ETN" ,"FIGY" );
        mapNameSymbol.put("Embry Holdings Ltd.","1388.HK");
        mapNameSymbol.put("Asia Pacific Fund Inc.","APB" );


        listNames = new ArrayList<String>(mapNameSymbol.keySet());
        Collections.sort(listNames);
    };

    /*
    @returns list of all companies
     */


    public List<String> getNamesList(){
               return listNames;
    }

    /*
    Gets company's name
    @return matching company's symbol
     */
    public String getSymbol(String companyName){
        String symbol = mapNameSymbol.get(companyName);
        return symbol;
    }

    /*
    gets first letters of the company's name
   @return List of matching records
     */
    public List<String> getMatchingNames(String letters){
        return listNames;
    }

}
