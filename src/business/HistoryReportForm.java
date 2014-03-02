package business;

/**
 * Created by Ivan on 23/02/14.
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Shows 30 day Financial History Report for the given Symbol
 */

public class HistoryReportForm extends JFrame{

    private String[] columnNames = {"Date", "Open", "High", "Low", "Close", "Volume", "Adj Close"};
    private String[][] data = new String[30][7];
    public JTable table;
    public DefaultTableModel model;
    Date fromDate = new Date();
    Calendar c = Calendar.getInstance();
    List<String> historyList = new ArrayList<String>();
    ScrapStockData ssd = new ScrapStockData();

    public HistoryReportForm(String Symbol) {

        c.setTime(fromDate);
        c.add(Calendar.MONTH, -2);
        final Date toDate = c.getTime();

        java.util.List<String> lst = new ArrayList<>();


        this.setTitle("30-Day History Report for "+ssd.getCompanyName(Symbol)+" ("+Symbol+")");

        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);


        historyList = ssd.stockHistory(Symbol, toDate, fromDate);
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 7; j++) {
                model.setValueAt(historyList.get(i).split(",")[j], i, j);
            }
        }


        JPanel southPanel = new JPanel();

        southPanel.add(table);
        add(southPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }


}
