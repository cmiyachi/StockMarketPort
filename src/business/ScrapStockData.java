package business;



import java.util.*;


/**
 *
 * @author Ivan
 */
public class ScrapStockData implements ScrapStockDataInterface{


    private Map<String, String> mapStockInfoCodes = new HashMap<String, String>();



    private YahooFinanceConnection yahooFinanceConnection = new YahooFinanceConnection();



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
