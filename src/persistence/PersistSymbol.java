package persistence;

import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * This is an interface for the class project.

 * The methods that are to be implemented follow CRUD (create, read, update, delete).
 * 
 * Based on team discussions, Stock data is represented in the form of Strings.
 * 
 *
 */
public interface PersistSymbol {

    /**
     * Save and persist the Stock data based on symbol name.
     * @param symbol
     */
    public void saveSymbol(String symbol);

    
    
    /**
     * Get Set of Symbol names.
     * @return
     */
    public Set<String>  getSymbols();

    
    /**
     * Read from dborfile (the database or file) to retrieve the Stock symbols and stock data.
     * @param dborfile
     */
    public void readFromDB(String dborfile);

    
    
    
    /**
     * Read a Stock symbol from the database. 
     * Note that a Stock symbol data is represented as hash table.
     * @param symbol
     * @return
     */
    public Map<String,String> readSymbol(String symbol);

    /**
     * Create a Symbol.
     * Note that a Stock symbol data is represented as hash table.
     * 
     * @param symbol
     * @return
     */
    public Map<String,String> createSymbol(String symbol);

    
    
    /**
     * Delete a symbol from the database.
     * @param symbol
     */
    public void deleteSymbol(String symbol);

    
    
    /**
     * Overloaded method
     * Flush or write to database or file that you previously read from;
     * That's why no arguement is needed.
     */
    public void writeDB();

    /**
     * Overloaded method
     * Flush or write out data to a database name or a file name.
     * @param dborfile
     */
    public void writeDB(String dborfile);

    
 
    
}

