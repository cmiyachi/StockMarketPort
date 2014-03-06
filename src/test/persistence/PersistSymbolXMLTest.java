package test.persistence; 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

    //Use an interface PersistSymbol to set up tests.
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

        //Persistence is implemented as an XML for iteration 1.
        persistence = new PersistSymbolXML();
    }

    @After
    public void tearDown() {
        persistence = null;
    }

/** 
* 
*   
 * Test to create a Stock Symbol.
 * Save the symbol.
 * Make sure that the symbol exits when users queries for it using getSymbols.
 */
@Test
public void testCreateSymbol() {

    //create symbol 
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


    //create two stock symbols
    String symbol = "testsymbol";

    Map<String,String> obj = persistence.createSymbol(symbol);
    persistence.saveSymbol(symbol);

    String symbol2 = "testsymbol2";
    Map<String,String> obj2 = persistence.createSymbol(symbol2);
    persistence.saveSymbol(symbol2);


    Set<String> symbols = persistence.getSymbols();

    //Ensure that both stock symbols exist and can be queried.
    assertTrue(symbols.contains(symbol));
    assertTrue(symbols.contains(symbol2));
}

/**
 * Test of readSymbol method, of class PersistSymbolXML.
 */
@Test
public void testReadSymbol() {

    
    //Create two real stock symbols
    String symbol = "testsymbol";

    Map<String,String> obj = persistence.createSymbol(symbol);
    persistence.saveSymbol(symbol);

    String symbol2 = "testsymbol2";
    Map<String,String> obj2 = persistence.createSymbol(symbol2);
    persistence.saveSymbol(symbol2);

    
    //Create a bogus stock symbol that should not exist
    
    //Querying bogus should result in a null, since it does not exist
    Map<String,String> bogus = persistence.readSymbol("bogus");
    assertNull("Read bogus", bogus);
    
    
    //Querying symbol1 and symbol2 should return data.
    Map<String,String> read1 = persistence.readSymbol(symbol);
    assertNotNull("Read symbol1", read1);

    Map<String,String> read2 = persistence.readSymbol(symbol2);
    assertNotNull("Read symbol2", read2);
    
}

/**
 * Test of createSymbol method, of class PersistSymbolXML.
 */
@Test
public void testCreateBadSymbol() {

    //Thus symbol is not alphanumeric and is not a suitable argument.
    String symbol = "";

    Map<String, String> expResult = null;
    Map<String, String> result = persistence.createSymbol(symbol);

    //No Stock symbol data is returned, as the stock symbol name is bogus.    
    assertEquals(expResult, result);

}


/**
 * Test of createSymbol method, of class PersistSymbolXML.
 */
@Test
public void testCreateBadSymbol2() {

    //Another bad symbol name
    String symbol = null;

    Map<String, String> expResult = null;
    Map<String, String> result = persistence.createSymbol(symbol);
    
    //No data structure is created, since the stock symbol name is not alphanumeric.
    assertEquals(expResult, result);

}


/*
    Test that data can be inserted and retrieved.
*/

@Test
public void testInsertData() {
    
    //create a valid stock symbol and insert test data. 
    String symbol = "testsymbol";
    Map<String,String> obj = persistence.createSymbol(symbol);
    obj.put("somefield", "somedata");
    persistence.saveSymbol(symbol);
    
    //Now query it and see check that data
    Map<String,String> anotherobj = persistence.readSymbol(symbol);
    assertNotNull(anotherobj);
    assertTrue(anotherobj.containsKey("somefield"));
    assertEquals("somedata", anotherobj.get("somefield"));
    
    
}


//These tests are simple barf tests.
//They should not cause a crash.
@Test
public void testDeleteBadSymbol() {
    //should not crash the test
    persistence.deleteSymbol(null);
    persistence.deleteSymbol("doesnotexist");
}

@Test
public void testSaveBadSymbol() {
    //should not crash the test
    persistence.saveSymbol(null);
}


/*
    Test the FileIO of the PersistSymbol implementation.
    Assuming that all systems have the Public Documents directory available for read and write.
    This tests the that XML file can be generated.
*/
@Test
public void testWriteXML() {
    
    String xmlfile = "build\\testxml\\testWrite.xml";
    
    //create a valid stock symbol and insert test data. 
    String symbol = "testsymbol";
    Map<String,String> obj = persistence.createSymbol(symbol);
    obj.put("somefield", "somedata");
    persistence.saveSymbol(symbol);
    
    persistence.writeDB(xmlfile);
    
    File readfile = new File(xmlfile);
    assertNotNull(readfile);
    
    
}

/*
    Test the FileIO of the PersistSymbol implementation.
    Assuming that all systems have the Public Documents directory available for read and write.
    This tests the that XML file can be created and data can be read back in.
*/

@Test
public void testWriteReadXML() {
    String xmlfile = "build\\testxml\\testWriteRead.xml";
    
    //create a valid stock symbol and insert test data. 
    String symbol = "testsymbol";
    Map<String,String> obj = persistence.createSymbol(symbol);
    obj.put("somefield", "somedata");
    persistence.saveSymbol(symbol);
    
    String symbol2 = "testsymbol2";
    Map<String,String> obj2 = persistence.createSymbol(symbol2);
    obj2.put("somefield", "somedata2");
    persistence.saveSymbol(symbol2);
    
    persistence.writeDB(xmlfile);
    
    //Now read the data back in
    
    PersistSymbolXML newdb = new PersistSymbolXML();
    newdb.readFromDB(xmlfile);
    
    Set<String> allsymbols = newdb.getSymbols();
    assertTrue(allsymbols.contains(symbol));
    assertTrue(allsymbols.contains(symbol2));
    
    
}


/*
    Test the FileIO of the PersistSymbol implementation.
    Assuming that all systems have the Public Documents directory available for read and write.
    This tests the that XML file can be created and data can be read back in.
    Then it adds more stock data and persists back to the XML.
*/

@Test
public void testWriteReadXML2() {
    String xmlfile = "build\\testxml\\testWriteRead2.xml";
    
    //create a valid stock symbol and insert test data. 
    String symbol = "testsymbol";
    Map<String,String> obj = persistence.createSymbol(symbol);
    obj.put("somefield", "somedata");
    persistence.saveSymbol(symbol);
    
    String symbol2 = "testsymbol2";
    Map<String,String> obj2 = persistence.createSymbol(symbol2);
    obj2.put("somefield", "somedata2");
    persistence.saveSymbol(symbol2);
    

    persistence.writeDB(xmlfile);
    
    //Now read the data back in
    
    PersistSymbolXML newdb = new PersistSymbolXML();
    newdb.readFromDB(xmlfile);
    
    Set<String> allsymbols = newdb.getSymbols();
    assertTrue(allsymbols.contains(symbol));
    assertTrue(allsymbols.contains(symbol2));
    assertTrue(allsymbols.size() == 2);
    
    //add a new Stock Symbol
    String symbol3 = "testsymbol3";
    Map<String,String> obj3 = newdb.createSymbol(symbol3);
    obj3.put("somefiled","somedata3");
    newdb.saveSymbol(symbol3);
    newdb.writeDB(); //Test the overloaded method which writes to the same XML file it read from
    assertTrue(newdb.getSymbols().size()==3);// Now we should have one more stock symbol
    
    
    
}

//These remaining tests check bad file inputs and outputs.


@Test
public void testWriteBadFile() {
    // should not crash the test
    persistence.writeDB(null);
    persistence.writeDB("C:\\baddirname\\somewhereovertherainbow");
    
}

@Test
public void testWriteEmptyFile() {
     //makes bad file
     persistence.writeDB("build\\testxml\\emptyXmlTest");
}

@Test
public void testReadBadFile() {
    persistence.readFromDB(null);
}


@Test
public void testWriteReadEmptyFileTest() {
  
    String badfile = "build\\testxml\\testBadXmlFile";
    writeBadDB(badfile);
    persistence.readFromDB(badfile);
    
    
    
}



//helper function to write out garbage xml
public void writeBadDB(String file) {
        
        if (file == null) {
            return ;
        }
        File outfile = new File(file);
        
        
        
        try {
            if (! outfile.exists()) {
                outfile.createNewFile();
            }
            
            FileWriter fwt = new FileWriter(outfile.getAbsoluteFile());
            BufferedWriter bwt = new BufferedWriter(fwt);
            
            //write contents of dataCOntainer out to the XML file
            bwt.write("<?xml version=\"1.0\"?>");
            bwt.newLine();
            
            bwt.write("<portfolio>");
            bwt.newLine();
            
            
                bwt.write("\t\t<stock id=\"yuck\" />");
                bwt.newLine();
              
                //bwt.write("\t\t</badnodename>");
                //bwt.newLine();
            
            
            bwt.write("</portfolio>");
            bwt.newLine();
            bwt.close();
            
        }
        catch(Exception doh) {
            System.out.println(doh.getMessage());
        }
        
    }
    




} 
