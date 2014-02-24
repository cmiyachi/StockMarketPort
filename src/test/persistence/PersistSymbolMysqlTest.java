/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.persistence;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import persistence.PersistSymbol;
import persistence.PersistSymbolFactory;
import persistence.PersistSymbolMysql;
import persistence.PersistSymbolXML;
import persistence.PropertiesParserForMysql;

/**
 *
 * @author zyyang1
 */
public class PersistSymbolMysqlTest {
    
        //Use an interface PersistSymbol to set up tests.
    private static PersistSymbol persistence;
    private static boolean checkconn;

    public PersistSymbolMysqlTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        
       
       checkconn =  PersistSymbolFactory.checkSqlConnection();
      
       //Set in test mode
       persistence = PersistSymbolFactory.createMySql(PersistSymbolFactory.modes.TEST);
       
    }

    @AfterClass
    public static void tearDownClass() {
        persistence = null;
    }

    @Before
    public void setUp() {

        
    }

    @After
    public void tearDown() {
        
    }
    
    
    
    @Test
    public void createSymbol() { // but don't persist yet
        
        
        if (checkconn) {
            
            
            PersistSymbol mysql= persistence;
           
            
            
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(10000);
            String newname = "newstock" + randomInt;
            Map<String,String> newsymbol = mysql.createSymbol(newname);
           
            assertNotNull(newsymbol);
             
           
        }
    }
    
    @Test
    public void duplicateSymbol() {
        
            if (checkconn) {
                PersistSymbol mysql= persistence;
                
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(1000);
                String newname = "newstock" + randomInt ;
               
                
                //delete it if it exists so we can start from scratch
                mysql.deleteSymbol(newname);
                
                Map<String,String> newsymbol = mysql.createSymbol(newname);
                newsymbol.put("somdomfield", "44.33");
                newsymbol.put("anotherfield", "33.44");
                
                mysql.saveSymbol(newname);
                
                //try to create again
                Map<String,String> duplicate = mysql.createSymbol(newname);
                
           
                assertNotNull("New Symbol", newsymbol);
                assertNull("Duplicate", duplicate);
                
                
        }
    }
    
    @Test
    public void updateSymbol() {
        
        if (checkconn){
            PersistSymbol mysql= persistence;
            Map<String,String> newsymbol = mysql.createSymbol("newstock2");
            if(newsymbol == null) {//could not create it b.c. it already exists
                newsymbol= mysql.readSymbol("newstock2"); //just read it instead
            }
            
            newsymbol.put("somdomfield", "99.89");
            newsymbol.put("anotherfield", "54.34");
            mysql.saveSymbol("newstock2");
        }
         
    }
    
    @Test
    public void readNonexistentSymbol() {
        
        if (checkconn) {
            PersistSymbol mysql= persistence;
            Map<String,String> newsymbol = mysql.readSymbol("idontexist");
            assertNull(newsymbol);
        }
         
    }
    
    
 @Test   
 public void deleteSymbol() {
         if (checkconn) {
                PersistSymbol mysql= persistence;
                
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(10000);
                String newname = "newstock" + randomInt ;
                
                Map<String,String> newsymbol = mysql.createSymbol(newname);
                assertNotNull(newsymbol);
                
                newsymbol.put("somdomfield", "123.45");
                newsymbol.put("anotherfield", "456.78");
                
                mysql.saveSymbol(newname);
                
                Map<String,String> readsymbol = mysql.readSymbol(newname);
                assertNotNull(readsymbol);
                
                mysql.deleteSymbol(newname);
                Map<String,String> cantreadsymbol = mysql.readSymbol(newname);
                assertNull(cantreadsymbol);
               
                
            
        }
         
    }
    
    @Test
    public void badNameTest() {
        if(checkconn) {
            PersistSymbol mysql= persistence;
            Map<String,String> bad = mysql.readSymbol(null);
            assertNull(bad);
        }
         
    }
    
    @Test
    public void badNameTest2() {
        if (checkconn) {
            PersistSymbol mysql= persistence;
            Map<String,String> bad = mysql.readSymbol(".");
            assertNull(bad); 
        }
         
    }
    
    
    @Test
    public void getSymbolsTest() {
        PersistSymbol mysql= persistence;
        Set<String> result = mysql.getSymbols();
        
        assertNotNull(result);
         
    }
   
 
}
