package test.business; 

import business.ScrapStockData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
* ScrapStockData Tester.
 * This test program unit tests the class ScrapStockData -
* 
* @author cmiyachi
* @since <pre>Feb 13, 2014</pre> 
* @version 1.0 
*/ 
public class ScrapStockDataTest {

    private ScrapStockData scrapStockData;

    /*
    * This method creates the object ScrapStockData to be used in the tests
     */
@Before
public void before() throws Exception {
    scrapStockData = new ScrapStockData();
} 

    /*
    * Once we are done with the test:
    *  This sets the object to null so it will be garbage collected
     */
@After
public void after() throws Exception {
    scrapStockData = null;
} 

/** 
* 
* Method: stockHistory(String Symbol, Date fromDate, Date toDate)
 * This tests the obtaining the stock history on a symbol from one date to another
* 
*/ 
@Test
public void testStockHistory() throws Exception {
    Date begin = new Date(2014,1,1);
    Date end = new Date(2014,2,1);
    List<String> listInfoNames = scrapStockData.stockHistory("XRX",begin, end);

    for (String infoNames : listInfoNames)
    {
        System.out.println(infoNames);
    }

    assertNotNull("list of Info Names returned", listInfoNames);
} 

/** 
* 
* Method: getCompanyName(String Symbol) 
* 
*/ 
@Test
public void testGetCompanyName() throws Exception { 

    String companyName = scrapStockData.getCompanyName("XRX");
    assertEquals("XRX equals Xerox", "\"Xerox Corporation\"",companyName);

} 

/** 
* 
* Method: getStockInfoOn(String Symbol, String infoName)
 *  This tests that a symbol gives the correct company name
* 
*/ 
@Test
public void testGetStockInfoOn() throws Exception { 

    String info = scrapStockData.getStockInfoOn("XRX","s0p0c1p2");
    System.out.println("info for Xerox: " + info);
    assertNotNull("Info on Xerox ", info);
} 


} 
