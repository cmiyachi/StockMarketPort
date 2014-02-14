package business;

   
import java.util.Date;
import java.util.List;

/**
 * Created by Ivan on 12/02/14.
 */
public interface ScrapStockDataInterface {


    /**
     *
     * @param Symbol - stock symbol
     * @param fromDate
     * @param toDate
     * @return Stock History for the given dates
     */

    public List<String> stockHistory(String Symbol, Date fromDate, Date toDate);

    /**
     *
     * @param Symbol - stock symbol
     * @return Company's name on given symbol
     */

    public String getCompanyName(String Symbol);

    /**
     *
     * @param Symbol - - stock symbol
     * @param infoName - QuoteProperties
     * @return stock information on given stock QuoteProperties
     */

    public String getStockInfoOn(String Symbol, String infoName);



}
