/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
/**
 *
 * @author Ivan
 */
public class YahooFinanceConnectionTest {
    
    public YahooFinanceConnectionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getInformationList method, of class YahooFinanceConnection.
     */
    @Test
    public void testGetInformationList_3args() {
        Date from = new Date();
        Date to = new Date();
        YahooFinanceConnection yFC = new YahooFinanceConnection(); 
        
        String result = yFC.getInformationList("GOOG", from, to).get(0);
        String expected = "Date,Open,High,Low,Close,Volume,Adj Close";
        assertEquals("Compare stringd", expected, result );
        
        
    }

    /**
     * Test of getInformationList method, of class YahooFinanceConnection.
     */
    @Test
    public void testGetInformationList_String_String() {
       YahooFinanceConnection yFC = new YahooFinanceConnection(); 
       String result = yFC.getInformationList("GOOG", "n").get(0);
       String expected = "\"Google Inc.\"";
       assertEquals("Compare stringd", expected, result );
    }
}