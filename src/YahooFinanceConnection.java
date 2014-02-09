/**
 * Created by Ivan on 09/02/14.
 */
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class YahooFinanceConnection {

    private List<String> listInfo = new ArrayList<String>();

/**
 * gets generated URL
 * @return List<String> of requested information;
 */

    private List<String> getInfoFromURL(String urlString){
         List<String> listStr = new ArrayList<String>();
        return listStr;
    }

    /**
     * gets params from ScrapStockData
     * @param Symbol
     * @param fromDate
     * @param toDate
     * generates URL
     * @return History report for given data
     */
    public List<String> getInformationList(String Symbol, Date fromDate, Date toDate){

        listInfo = getInfoFromURL("http://.....Symbol Dates.....history");

        listInfo.clear();
        listInfo.add("Date,Open,High,Low,Close,Volume,Adj Close");
        listInfo.add("2012-01-30,101.45,104.94,100.87,102.82,7002000,49.98");
        listInfo.add("2012-01-23,101.92,103.29,101.02,102.11,4074100,49.63");
        listInfo.add("2012-01-17,98.70,102.00,98.05,101.76,4828600,49.46");
        listInfo.add("2012-01-09,98.10,99.14,97.68,98.30,4090600,47.78");
        return listInfo;
    }

    /**
     * gets params from ScrapStockData
     * @param Symbol
     * @param infoCode
     * generates URL
     * @return List<> with requested info
     */

    public List<String> getInformationList(String Symbol, String infoCode){

        listInfo = getInfoFromURL("http://....Symbol infoCode......information");

        listInfo.clear();
        listInfo.add("General Electric");
        return listInfo;
    }
}
