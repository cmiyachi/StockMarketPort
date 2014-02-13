/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import java.util.ArrayList;
import java.util.List;
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
        String expected = "Asia Pacific Fund Inc.";
        assertEquals(result, expected);
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of getNamesList method, of class SymbolSearch.
     */
    @Test
    public void testGetNamesList() {
        
    }

    /**
     * Test of getSymbol method, of class SymbolSearch.
     */
    @Test
    public void testGetSymbol() {
         
        SymbolSearch ss = new SymbolSearch();
        String result = ss.getSymbol("Response Genetics, Inc");
        String expected = "RGDX";    
        assertEquals(result, expected);
    }

    /**
     * Test of getMatchingNames method, of class SymbolSearch.
     */
    @Test
    public void testGetMatchingNames() {
        
        SymbolSearch ss = new SymbolSearch();
        List<String> listNames = new ArrayList<String>();
        String str = ss.getMatchingNames("As").get(0);
        char expected1 = 'A';
        char expected2 = 's';
        for(int i =0; i < str.length(); i++){
             char result1 = str.charAt(0);
             char result2 = str.charAt(1);
             assertEquals(expected1, result1);
             assertEquals(expected2, result2);
        }
        //String expected = "Asia Pacific Fund Inc.";
    }
}