package test.business; 

import business.ScrapStockData;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

/**
* ScrapStockData Tester. 
* 
* @author cmiyachi
* @since <pre>Feb 13, 2014</pre> 
* @version 1.0 
*/ 
public class ScrapStockDataTest {

    private ScrapStockData scrapStockData;

@Before
public void before() throws Exception {
    scrapStockData = new ScrapStockData();
} 

@After
public void after() throws Exception {
    scrapStockData = null;
} 

/** 
* 
* Method: stockHistory(String Symbol, Date fromDate, Date toDate) 
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
* 
*/ 
@Test
public void testGetStockInfoOn() throws Exception { 

    String info = scrapStockData.getStockInfoOn("XRX","abp5");
    System.out.println("info for Xerox: " + info);
    assertNotNull("Info on Xerox ", info);
} 


/** 
* 
* Method: getListInfoNames() 
* 
*/ 
@Test
public void testGetListInfoNames() throws Exception {

    // TODO:  Not sure this needs to be tested as method is private


} 

} 
