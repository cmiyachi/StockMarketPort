package business;

/**
 * Created by Ivan on 23/02/14.
 */

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
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class HistoryReportForm extends JFrame{


    private String[] columnNames = {"Date", "Open", "High", "Low", "Close", "Volume", "Adj Close"};
    private String[][] data = new String[30][7];
    private JTable table;
    private DefaultTableModel model;
    Date fromDate = new Date();
    Calendar c = Calendar.getInstance();
    List<String> historyList = new ArrayList<String>();
    ScrapStockData ssd = new ScrapStockData();

    public HistoryReportForm() {

        c.setTime(fromDate);
        c.add(Calendar.MONTH, -2);
        final Date toDate = c.getTime();

        java.util.List<String> lst = new ArrayList<>();
        final SymbolSearch ss = new SymbolSearch();
        lst = ss.getNamesList();
        int howMany = lst.size();
        String[] zhopa = new String[howMany];
        lst.toArray(zhopa);
        final JComboBox jcb = new JComboBox(zhopa);
        final JTextField jt = new JTextField("                                                   ");

        this.setTitle("30-Day History Report");

        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);




        jcb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                jt.setText(ss.getSymbol(String.valueOf(jcb.getSelectedItem())));
            }
        });



        JButton jb = new JButton("Show History");

        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                historyList = ssd.stockHistory(jt.getText(), toDate, fromDate);
                for (int i = 0; i < 30; i++) {
                    for (int j = 0; j < 7; j++) {
                        model.setValueAt(historyList.get(i).split(",")[j], i, j);
                    }
                }
            }
        });



        JPanel northPanel = new JPanel();


        northPanel.add(jcb);
        northPanel.add(jt);
        northPanel.add(jb);
        add(northPanel, BorderLayout.NORTH);


        JPanel southPanel = new JPanel();



        // table.add(scrollPane);

        southPanel.add(table);
        add(southPanel, BorderLayout.SOUTH);



        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {


        HistoryReportForm frame = new HistoryReportForm();


    }
}

