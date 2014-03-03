package test.business;
/**
 * SymbolSearch Tester.
 *
 * @author Ivan
 * @since <pre>Feb 13, 2014</pre>
 * @version 1.0
 */
import business.SymbolSearch;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
/**
 *
 * @author Ivan
 */
public class SymbolSearchTest {

    public SymbolSearchTest() {
        SymbolSearch ss = new SymbolSearch();
        List<String> listNames = new ArrayList<String>();
        listNames = ss.getNamesList();
        String result = listNames.get(0);
        String expected = "";
        assertEquals(result, expected);

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getNamesList method, of class business.SymbolSearch.
     */
    @Test
    public void testGetNamesList() {

    }

    /**
     * Test of getSymbol method, of class business.SymbolSearch.
     */
    @Test
    public void testGetSymbol() {

        SymbolSearch ss = new SymbolSearch();
        String result = ss.getSymbol("Response Genetics");
        String expected = "RGDX";
        assertEquals(result, expected);
    }

    /**
     * Test of getMatchingNames method, of class business.SymbolSearch.
     */
    @Test
    public void testGetMatchingNames() {

        SymbolSearch ss = new SymbolSearch();
        List<String> listNames = ss.getMatchingNames("As");

        for(String name : listNames){
            assertTrue(name.toLowerCase().startsWith("as"));
        }

    }

}
