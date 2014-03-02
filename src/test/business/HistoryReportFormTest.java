package test.business;

/**
 * Created by Ivan on 02/03/14.
 */
import business.HistoryReportForm;
import business.ScrapStockData;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author Ivan
 * @since <pre>Feb 13, 2014</pre>
 * @version 1.0
 */
/**
 *
 * @author Ivan
 */
public class HistoryReportFormTest {

    public HistoryReportFormTest() {


    }


    /**
     * Tests Jtable for the values. Compares JTable values with values from Yahoo Finance
     */
    @Test
    public void testHistoryFrameJTable() {
        Date fromDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(fromDate);
        c.add(Calendar.MONTH, -2);
        Date toDate = c.getTime();

        HistoryReportForm historyReportForm = new HistoryReportForm("GOOG");
        ScrapStockData scrapStockData = new ScrapStockData();
        List<String>  historyList;
        historyList = scrapStockData.stockHistory("GOOG",toDate, fromDate);
        int i =0;
        for(String str : historyList){
            for(int j= 0 ; j< 7; j++){
               if (i<30) Assert.assertEquals("Compares JTable values with values from Yahoo Finance",historyReportForm.table.getModel().getValueAt(i,j),str.split(",")[j]);
            }
            i++;
        }



    }

    /**
     * Tests JFrame tile formation;
     */
    @Test
    public void testFrameTitle(){
        HistoryReportForm historyReportForm = new HistoryReportForm("GOOG");
        String expected = "30-Day History Report for \"Google Inc.\" (GOOG)";
        String result = historyReportForm.getTitle();
        Assert.assertEquals("Compare JFrame Title", expected, result);
    }
}

