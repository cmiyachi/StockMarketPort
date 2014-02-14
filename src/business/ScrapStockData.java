package business;



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
public class ScrapStockData implements ScrapStockDataInterface{


    private Map<String, String> mapStockInfoCodes = new HashMap<String, String>();



    private YahooFinanceConnection yahooFinanceConnection = new YahooFinanceConnection();

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


    /**
     *
     * @param Symbol - stock symbol
     * @param fromDate
     * @param toDate
     * @return Stock History for the given dates
     */

    public List<String> stockHistory(String Symbol, Date fromDate, Date toDate){

      List<String> stockHistoryList = new ArrayList<String>();
      stockHistoryList = yahooFinanceConnection.getInformationList(Symbol, fromDate, toDate);
      return stockHistoryList;
    }


    /**
     *
     * @param Symbol - stock symbol
     * @return Company's name on given symbol
     */

    public String getCompanyName(String Symbol){
        String companyName=yahooFinanceConnection.getInformationList(Symbol,"n").get(0);
        return companyName;
    }


    /**
     *
     * @param Symbol - - stock symbol
     * @param infoName - QuoteProperties
     * @return stock information on given stock QuoteProperties
     */

    public String getStockInfoOn(String Symbol, String infoName){
        String info = yahooFinanceConnection.getInformationList(Symbol, infoName).get(0);
        return info;
    }



}
