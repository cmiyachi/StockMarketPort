package test.persistence; 

import org.junit.*;
import persistence.PersistSymbol;
import persistence.PersistSymbolXML;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/** 
* PersistSymbolXML Tester. 
* 
* @author zyyang1
* @since <pre>Feb 13, 2014</pre> 
* @version 1.0 
*/ 
public class PersistSymbolXMLTest {

    private PersistSymbol persistence;

    public PersistSymbolXMLTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        persistence = new PersistSymbolXML();
    }

    @After
    public void tearDown() {
        persistence = null;
    }

/** 
* 
* Method: deleteSymbol(String symbol) 
* 
*/
/**
 * Test of deleteSymbol method, of class PersistSymbolXML.
 */
@Test
public void testDeleteSymbol() {

    String symbol = "newsymbol";

    Map<String,String> obj = persistence.createSymbol(symbol);

    assertNotNull(obj);

    persistence.saveSymbol(symbol);

    Set<String> symbols = persistence.getSymbols();


    assertTrue(symbols.contains(symbol));

}

/** 
* 
* Method: saveSymbol(String symbol) 
* 
*/
/**
 * Test of saveSymbol method, of class PersistSymbolXML.
 */
@Test
public void testSaveSymbol() {

    String symbol = "testsymbol";

    Map<String,String> obj = persistence.createSymbol(symbol);
    persistence.saveSymbol(symbol);

    Set<String> symbols = persistence.getSymbols();

    assertTrue(symbols.contains(symbol));
}

/**
 * Test of getSymbols method, of class PersistSymbolXML.
 */
@Test
public void testGetSymbols() {


    String symbol = "testsymbol";

    Map<String,String> obj = persistence.createSymbol(symbol);
    persistence.saveSymbol(symbol);

    String symbol2 = "testsymbol2";
    Map<String,String> obj2 = persistence.createSymbol(symbol2);
    persistence.saveSymbol(symbol2);


    Set<String> symbols = persistence.getSymbols();

    assertTrue(symbols.contains(symbol));
    assertTrue(symbols.contains(symbol2));
}

/**
 * Test of readSymbol method, of class PersistSymbolXML.
 */
@Test
public void testReadSymbol() {

    String symbol = "testsymbol";

    Map<String,String> obj = persistence.createSymbol(symbol);
    persistence.saveSymbol(symbol);

    String symbol2 = "testsymbol2";
    Map<String,String> obj2 = persistence.createSymbol(symbol2);
    persistence.saveSymbol(symbol2);

    Map<String,String> bogus = persistence.readSymbol("bogus");
    assertNull("Read bogus", bogus);
    /*
    Map<String,String> read1 = persistence.readSymbol(symbol);
    assertNotNull("Read symbol1", read1);

    Map<String,String> read2 = persistence.readSymbol(symbol2);
    assertNotNull("Read symbol2", read2);
    */
}

/**
 * Test of createSymbol method, of class PersistSymbolXML.
 */
@Test
public void testCreateBadSymbol() {

    String symbol = "";

    Map<String, String> expResult = null;
    Map<String, String> result = persistence.createSymbol(symbol);
    assertEquals(expResult, result);

}

@Test
public void testCreateBadSymbol2() {

    String symbol = null;

    Map<String, String> expResult = null;
    Map<String, String> result = persistence.createSymbol(symbol);
    assertEquals(expResult, result);

}








} 
