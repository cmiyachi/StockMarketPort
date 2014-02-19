/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package test.persistence;

import java.util.Map;
import java.util.Random;
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

/**
 *
 * @author zyyang1
 */
public class PersistSymbolMysqlTest {
    
        //Use an interface PersistSymbol to set up tests.
    private PersistSymbol persistence;
    private static boolean checkconn;

    public PersistSymbolMysqlTest() {
    }

    @BeforeClass
    public static void setUpClass() {
       checkconn =  PersistSymbolFactory.checkSqlConnection();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

        
    }

    @After
    public void tearDown() {
        persistence = null;
    }
    
    
    
    @Test
    public void createSymbol() {
        
        if (checkconn) {
            PersistSymbol mysql= new PersistSymbolMysql();
            Random randomGenerator = new Random();
            int randomInt = randomGenerator.nextInt(1000);
            String newname = "newstock" + randomInt;
            Map<String,String> newsymbol = mysql.createSymbol(newname);
           
            assertNotNull(newsymbol);
        }
    }
    
    @Test
    public void duplicateSymbol() {
        
            if (checkconn) {
                PersistSymbol mysql= new PersistSymbolMysql();
                
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(1000);
                String newname = "newstock" + randomInt;
                
                Map<String,String> newsymbol = mysql.createSymbol(newname);
                newsymbol.put("somdomfield", "scoobydoo");
                newsymbol.put("anotherfield", "shaggy");
                
                mysql.saveSymbol(newname);
                
                //try to create again
                Map<String,String> duplicate = mysql.createSymbol(newname);
                
           
                assertNotNull(newsymbol);
                assertNull(duplicate);
        }
    }
    
    @Test
    public void updateSymbol() {
        
        if (checkconn){
            PersistSymbol mysql= new PersistSymbolMysql();
            Map<String,String> newsymbol = mysql.createSymbol("newstock2");
            if(newsymbol == null) {//could not create it b.c. it already exists
                newsymbol= mysql.readSymbol("newstock2"); //just read it instead
            }
            
            newsymbol.put("somdomfield", "scoobydoo");
            newsymbol.put("anotherfield", "shaggy");
            mysql.saveSymbol("newstock2");
        }
    }
    
    @Test
    public void readNonexistentSymbol() {
        
        if (checkconn) {
            PersistSymbol mysql= new PersistSymbolMysql();
            Map<String,String> newsymbol = mysql.readSymbol("idontexist");
            assertNull(newsymbol);
        }
    }
    
    
 @Test   
 public void deleteSymbol() {
         if (checkconn) {
                PersistSymbol mysql= new PersistSymbolMysql();
                
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(1000);
                String newname = "newstock" + randomInt;
                
                Map<String,String> newsymbol = mysql.createSymbol(newname);
                assertNotNull(newsymbol);
                
                newsymbol.put("somdomfield", "scoobydoo");
                newsymbol.put("anotherfield", "shaggy");
                
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
            PersistSymbol mysql= new PersistSymbolMysql();
            Map<String,String> bad = mysql.readSymbol(null);
            assertNull(bad);
        }
    }
    
    @Test
    public void badNameTest2() {
        if (checkconn) {
            PersistSymbol mysql= new PersistSymbolMysql();
            Map<String,String> bad = mysql.readSymbol(".");
            assertNull(bad); 
        }
    }
    
 
}
