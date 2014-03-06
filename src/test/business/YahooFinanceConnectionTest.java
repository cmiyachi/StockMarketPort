package test.business;


import business.YahooFinanceConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static junit.framework.Assert.assertEquals;

/** 
* YahooFinanceConnection Tester. 
* 
* @author <Authors name> 
* @since <pre>Feb 13, 2014</pre> 
* @version 1.0 
*/ 
public class YahooFinanceConnectionTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}


/**
 * Test of getInformationList method, of class business.YahooFinanceConnection.
 */
@Test
public void testGetInformationList_Histiry() {
    Date fromDate = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(fromDate);
    c.add(Calendar.MONTH, -2);
    Date toDate = c.getTime();
    YahooFinanceConnection yFC = new YahooFinanceConnection();

    String result = yFC.getInformationList("NKE", toDate, fromDate).get(0);
    String expected = "Date,Open,High,Low,Close,Volume,Adj Close";
    assertEquals("Compare string", expected, result );


}

/**
 * Test of getInformationList method, of class business.YahooFinanceConnection.
 */
@Test
public void testGetInformationList_String_String() {
    YahooFinanceConnection yFC = new YahooFinanceConnection();
    String result = yFC.getInformationList("GOOG", "n").get(0);
    String expected = "\"Google Inc.\"";
    assertEquals("Compare stringd", expected, result );
}
/** 
* 
* Method: getInformationList(String Symbol, Date fromDate, Date toDate) 
* 
*/ 
@Test
public void testGetInformationListForSymbolFromDateToDate() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getInformationList(String Symbol, String infoCode) 
* 
*/ 
@Test
public void testGetInformationListForSymbolInfoCode() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: getInfoFromURL(String urlString) 
* 
*/ 
@Test
public void testGetInfoFromURL() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = YahooFinanceConnection.getClass().getMethod("getInfoFromURL", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 
