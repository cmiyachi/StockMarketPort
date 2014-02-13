package persistence;

import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * Created by cmiyachi on 2/5/14.
 */
public interface PersistSymbol {

    /**
     * Save the symbol
     * @param symbol
     */
    public void saveSymbol(String symbol);

    /**
     * Get list of symbols;
     * @return
     */
    public Set<String>  getSymbols();

    /**
     * Read from the database;
     * @param dborfile
     */
    public void readFromDB(String dborfile);

    /**
     * Read a symbol.
     * @param symbol
     * @return
     */
    public Map<String,String> readSymbol(String symbol);

    /**
     * Create a Symbol;
     * @param symbol
     * @return
     */
    public Map<String,String> createSymbol(String symbol);

    /**
     * Delete a symbol.
     * @param symbol
     */
    public void deleteSymbol(String symbol);

    /**
     * Re-Write to database that you read from;
     */
    public void writeDB();

    /**
     * Write to database 
     * @param dborfile
     */
    public void writeDB(String dborfile);

}
