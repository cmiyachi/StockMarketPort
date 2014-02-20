import javax.swing.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by cmiyachi on 2/19/14.
 */
public class EnterStock {
    private JPanel EnterStockPanel;
    private JLabel labelStockSymbol;
    private JTextField textFieldStockSymbol;
    private JComboBox comboBoxStockSymbol;
    private JTextArea textAreaStockData;
    private JButton buttonLookUp;
    private JButton buttonAdd;
    private JButton buttonRemove;


    public EnterStock() {

        // listener for add button

         buttonAdd.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
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


         buttonRemove.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        String currentSymbol = comboBoxStockSymbol.getSelectedItem().toString();
        comboBoxStockSymbol.removeItem(currentSymbol);
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
