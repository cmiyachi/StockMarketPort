package business;


/**
 * Created by Ivan on 09/02/14.
 */
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.List;
import java.net.*;
import java.io.*;

public class YahooFinanceConnection {

    private List<String> listInfo = new ArrayList<String>();

/**
 * gets generated URL
 * @return List<String> of requested information;
 */

    private List<String> getInfoFromURL(String urlString){
         
        List<String> listStr = new ArrayList<String>();
        
         listStr.clear();

      try{   
         URL url = new URL(urlString);
         URLConnection urlConnection = url.openConnection();
         BufferedReader in = new BufferedReader(new InputStreamReader( urlConnection.getInputStream()));
         String inputLine;
        while ((inputLine = in.readLine()) != null) listStr.add(inputLine);
        in.close();
      }
      catch(Exception e){}
         
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
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        int fromMonth = cal.get(Calendar.MONTH);
        int fromDay = cal.get(Calendar.DAY_OF_MONTH);
        int fromYear = cal.get(Calendar.YEAR);
        
        cal.setTime(toDate);
        int toMonth = cal.get(Calendar.MONTH);
        int toDay = cal.get(Calendar.DAY_OF_MONTH);
        int toYear = cal.get(Calendar.YEAR);
        

        listInfo = getInfoFromURL("http://ichart.yahoo.com/table.csv?s="+
                
                Symbol + 
                "&a="+ fromMonth +
                "&b="+ fromDay +
                "&c="+ fromYear + 
                "&d="+ toMonth +
                "&e="+ toDay +
                "&f=" + toYear + "&g=d&ignore=.csv");
       
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

        listInfo = getInfoFromURL("http://download.finance.yahoo.com/d/quotes.csv?s="+
                Symbol+
                "&f="+infoCode+
                "&e=.csv");

       
        return listInfo;
    }



    
}
