

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author Ivan
 */
public class ScrapStockData {


    Map<String, String> mapStockInfoCodes = new HashMap<String, String>();


    /*
     *  Constructs HashMap with available stock information options
     */

    public ScrapStockData(){
        //constructs HashMap??? Here or get it from XML
        mapStockInfoCodes.put("Ask", "a");
        mapStockInfoCodes.put("Bid", "b");
        mapStockInfoCodes.put("Price/Sale", "p5");


    }


    /*
     * creates a list for drop down menu
     *
     * @return list of available stock information options
     */


    private List<String> getListInfoNames(){

        List<String> listInfoNames = new ArrayList<String>();
        //Set<String> set = new HashSet<String>();
        listInfoNames.add("Ask");
        listInfoNames.add("Bid");
        listInfoNames.add("Price/Sale");


        return listInfoNames;

    }

   /*
    * constructs URL for history Report
    *
    * @returns
    * History URL
    */

    private String constructURLHistory(String Symbol, Date fromDate, Date toDate){
        String URL="www...history";
        return URL;
    }

    private String constructURLInfo(String Symbol, String infoName){
        String URL="www......info";
        return URL;
    }

    /*
     * Gets Symbol and From- To Dates from GUI for History Report
     *
     * @return List of String Arrays;
     */


    public List<String[]> stockHistory(String Symbol, Date fromDate, Date toDate){

        //report format

        String [] str1 = { "Date",          "Open",     "High",     "Low",      "Close",    "Volume",   "Adj Close" };
        String [] str2 = { "2010-01-25",   "546.59",   "549.88",   "525.61",   "529.94",   "4021800",   "529.94"};
        String [] str3 = { "2010-01-19",   "581.20",   "590.42",   "534.86",   "550.01",   "5168800",   "550.01"};


        List<String[]> stockHistoryList = new ArrayList<String[]>();

        stockHistoryList.add(str1);
        stockHistoryList.add(str2);
        stockHistoryList.add(str3);


        return stockHistoryList;
    }

    public String getCompanyName(String Symbol){
        String companyName="General Electric";
        return companyName;
    }



    /*
     * Gets Symbol and name of Requested Information From DropDown Menu
     *
     * @return  Requested Information
     */


    public String getStockInfoOn(String Symbol, String infoName){
        String info = "28.216B";
        return info;
    }
}