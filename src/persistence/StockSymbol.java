/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author zyyang1
 */
public class StockSymbol {
    
    private Map<String, String> hashTable;
    private String name;
    
    public StockSymbol() {
        hashTable = new HashMap<>();
        name = "undefined";
        
        
    }
    public StockSymbol(String name) {
        
        this();
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName( String name) {
        this.name = name;
    }
    
    public void setPair(String key, String value) {
        hashTable.put(key, value);
    }
    
    public boolean containsColumn(String field) {
        return hashTable.containsKey(field);
    }
    public String getColumnValue(String field) {
        return hashTable.get(field);
    }
   
    public Set<String> getAllColumnNames() {
        return hashTable.keySet();
    }
    
    public Map<String,String> getHashTable() {
        return hashTable;
    }
    
    @Override
    public boolean equals(Object obj) {
    
    boolean checkhash = true;
    
    if (obj == null) {
        return false;
    }
    if (obj == this) {
        return true;
    }
     if (! (obj instanceof StockSymbol)) {
        return false;
    }
     
     
    StockSymbol testobj = (StockSymbol) obj;

    //First check keys of this against testobj
    for (String key : hashTable.keySet()) {
      
        if (! testobj.containsColumn(key)) {
            return false;
        }
        if (! getColumnValue(key).equals(testobj.getColumnValue(key))) {
            return false;
        }
    }
    
    //Now check keys of testobj against this
    for (String key : testobj.getAllColumnNames()) {
        if (! containsColumn(key)) {
            return false;
        }
        if (! testobj.getColumnValue(key).equals( getColumnValue(key))){
            return false;
        }
    }
    
    
    if(hashCode() != testobj.hashCode()) {
        return false;
    }
     
    
     
     //if it made it this far, they're equal
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = 0;
        
        StringBuilder sb = new StringBuilder();
        
        for (String key : this.hashTable.keySet()) {
            String value = hashTable.get(key);
            sb.append(key+":"+value+":");
        }
        result = sb.toString().hashCode();
        return result;
    }
}
