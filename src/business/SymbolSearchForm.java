package business;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
/**
 * Created by Ivan on 23/02/14.
 */

/**
 * Helps to find symbol by company's name
 */

public class SymbolSearchForm extends JFrame{
    public SymbolSearchForm() {






        java.util.List<String> lst = new ArrayList<>();

        final SymbolSearch symbolSearch = new SymbolSearch();
        lst = symbolSearch.getNamesList();
        int howMany = lst.size();

        String[] strArray = new String[howMany];
        lst.toArray(strArray);
        final JComboBox jcb = new JComboBox(strArray);
        final JTextField jt = new JTextField("                                                   ");

        jcb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jt.setText(symbolSearch.getSymbol(String.valueOf(jcb.getSelectedItem())));
            }
        });



        JPanel southPanel = new JPanel();

        southPanel.add(jcb);
        southPanel.add(jt);
        add(southPanel, BorderLayout.SOUTH);


        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

    public static void main(String[] args) {


        SymbolSearchForm frame = new SymbolSearchForm();


    }
}
