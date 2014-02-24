StockMarketPort
===============

Our team decided to do a Stock Market Portfolio Program.  Here's our tier assignments:
● GUI  (Chris)
	○ Swing 
		Enter a symbol to search
		Present current price along with history
		Save symbol (for list of symbols to grab data for)
		Button to save symbol and if so, that symbol be added to a persistence interface
		ListBox - dynamically added to the list box - ListBox itself could be persisted. 
		Get data from Persistence and load it into the SWING list box 
		Java Class would read the file from disk and then map it and pass it to the GUI
● Business Logic (Ivan)
	○ Figure out how to “scrap” stock data from an online
		○ Decided to use the Yahoo Finance API
		○ Input - symbol - string with some restrictions
		○ Output - CSV File
	○ Present current price along with history
	○ Search for symbols in the GUI - from company name
● Persistence (Steve)
	○ Save users set of stocks to watch
	○ Start with Serialized Java objects, later move to XML files
		With SWING, you can create a menu and read a file in and out
		SaveSymbol(String symbol);  
		GetSymbols(String symbols); //delimited string 
		
		
		
In our first meeting, we created to interface definitions for the tiers.   We all use different IDE's but it doesn't appear to be a problem.   

In our second meeting we worked out issues with our build, unit tests, and IDE issues. 

Our project is: https://github.com/cmiyachi/StockMarketPort

Our Junit tests are in place.  We have a build.xml file that builds and runs the ant tests.  



2-19-14  Iteration 2 Goals

	1. Document all tests and code
		a. Need to assign java files to each of us
	2. Swing  (Chris)
		a. Determine layout of data for UI
		b. Should it update dynamically (maybe next iteration)
		c. Other features?
		d. Other team members need to review
	3. Code coverage
		a. Eclipse
		b. IntelliJ
	4. Persistence (Steve)
		a.  MySQL implementation using JDBC
		
		
Iteration 2
    First Swing iteration complete
    Code coverage added
    Persistence in both XML and MySQL