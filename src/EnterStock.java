import javax.swing.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import business.*;

/**
 * Created by cmiyachi on 2/19/14.
 */

/*
* These are the SWING items on the GUI
 */
public class EnterStock {
    private JPanel EnterStockPanel; // the main panel of the GUI
    private JLabel labelStockSymbol;  // this is the label for the stock symbol text box
    private JTextField textFieldStockSymbol; // this is the text box for entering the stock symbol
    private JComboBox comboBoxStockSymbol; // this is a list of stocks that are saved
    private JTextArea textAreaStockData;  //  this is the result of the look up - all the data of the stock is shown here
    private JButton buttonLookUp;  //  this is a button to look up the data on the stock in the text box
    private JButton buttonAdd;    // this button adds the symbol in the text box to the combo box / list to be saved
    private JButton buttonRemove;// this button removes the symbol in the text box to the combo box and from list to be saved


    public EnterStock() {

        // listener for add button - this is called when the user presses the "Add" button
         buttonAdd.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String stockSymbol = textFieldStockSymbol.getText();
                if (!stockSymbol.equals(""))
                {
                    // make sure the symbol isn't already here
                    comboBoxStockSymbol.setSelectedItem(stockSymbol);
                    int theIndex = comboBoxStockSymbol.getSelectedIndex();
                    if (!(theIndex > -1))
                    {
                        comboBoxStockSymbol.addItem(stockSymbol);
                    }
                }
            }
          });


         buttonRemove.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String currentSymbol = comboBoxStockSymbol.getSelectedItem().toString();
                comboBoxStockSymbol.removeItem(currentSymbol);
            }
        });
        /*
        *  Get the selected item and put it in the text box for the stock symbol
         */
        comboBoxStockSymbol.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String stockSymbol = (String )comboBoxStockSymbol.getSelectedItem();
                textFieldStockSymbol.setText(stockSymbol);
            }
        });
        /*
        * Get data on the stock symbol in the text box when this button is pushed
         */
        buttonLookUp.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // get the stock symbol from the text box
                String stockSymbol = textFieldStockSymbol.getText();
                // create a new ScrapStockData object
                ScrapStockData stockInfo = new ScrapStockData();
                // TODO what happens if the stock Symbol is unknown?
                // Get the info on the stock
                String companyName = stockInfo.getCompanyName(stockSymbol);
                // put the information in the large text window
                textAreaStockData.setText(companyName);


            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("EnterStock");
        frame.setContentPane(new EnterStock().EnterStockPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
