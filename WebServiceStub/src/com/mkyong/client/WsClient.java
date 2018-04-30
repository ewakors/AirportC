/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mkyong.client;

import com.mkyong.ws.ServerInfo;
import com.mkyong.ws.ServerInfoService;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class WsClient extends JFrame {

     private String[] columnNames
            = {"Country", "Capital", "Population in Millions", "Democracy"};

    private Object[][] data = {
        {"USA", "Washington DC", 280, true},
        {"Canada", "Ottawa", 32, true},
        {"United Kingdom", "London", 60, true},
        {"Germany", "Berlin", 83, false},
        {"France", "Paris", 60, true},
        {"Norway", "Oslo", 4.5, true},
        {"India", "New Delhi", 1046, false}
    };

    private DefaultTableModel model = new DefaultTableModel(data, columnNames);
    private JTable jTable = new JTable(model);

    private TableRowSorter<TableModel> rowSorter
            = new TableRowSorter<>(jTable.getModel());

    private JTextField jtfFilter = new JTextField();
    private JButton jbtFilter = new JButton("Filter");
    
    private JList list = new JList();
    private JButton button;
    private JPanel panel;
    private JLabel searchLabel;
    private JTextField searchField;

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
               JFrame frame = new JFrame("Row Filter");
               frame.add(new WsClient());
               frame.pack();
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               frame.setLocationRelativeTo(null);
               frame.setVisible(true);
            }

        });
        ServerInfoService sis = new ServerInfoService();
        ServerInfo si = sis.getServerInfoPort();

        System.out.println(si.getServerName());
//                System.out.println(si.getAirport());
    }

    public WsClient() {
        super("Rezerwacja biletów lotniczych");
        setSize(900, 700);
        setVisible(true);
        
        jTable.setRowSorter(rowSorter);

        JPanel searchPanel = new JPanel(new BorderLayout());

        searchPanel.add(new JLabel("Wyszukiwanie lotów:"),
                BorderLayout.NORTH);
        searchPanel.add(jtfFilter, BorderLayout.NORTH);

        setLayout(new BorderLayout());
        add(searchPanel, BorderLayout.NORTH);
        add(new JScrollPane(jTable), BorderLayout.CENTER);

                JPanel buttonPanel = new JPanel(new BorderLayout());
        button = new JButton("Kup bilet");
        button.setBounds(10, 15, 120, 55);
        buttonPanel.add(button, BorderLayout.NORTH);
        add(button, BorderLayout.SOUTH);
        jtfFilter.getDocument().addDocumentListener(new DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jtfFilter.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });
//        String[] items = {"cloumn 1\t column  2\t Column 3",
//            "cloumn 1\t column  2\t Column 3"};
//
//        panel = new JPanel();
//        panel.setLayout(null);
//        add(panel);
//
//        JPanel searchPanel = new JPanel(new BorderLayout());
//
//        searchLabel = new JLabel("wyszukiwanie");
//        searchField = new JTextField(50);
//        searchPanel.add(searchLabel, BorderLayout.NORTH);
//        searchPanel.add(searchField, BorderLayout.SOUTH);
//        add(searchPanel, BorderLayout.NORTH);
//
//        
//        list = new JList(items);
//
//        TabListCellRenderer renderer = new TabListCellRenderer();
//        list.setCellRenderer(renderer);
//
//        JScrollPane scrollPane = new JScrollPane();
//        scrollPane.setBounds(10, 15, 900, 300);
//        scrollPane.setBounds(10, 10, 30, 30);
//        scrollPane.getViewport().add(list);
//        getContentPane().add(scrollPane, null);
////
//        WindowListener exitEvent = new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        };
//        addWindowListener(exitEvent);
//

     

    }

}

class TabListCellRenderer extends JLabel implements ListCellRenderer {

    protected static Border m_noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    protected FontMetrics m_fm = null;

    public TabListCellRenderer() {
        super();
        setOpaque(true);
        setBorder(m_noFocusBorder);
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());

        setBackground(isSelected ? list.getSelectionBackground() : list
                .getBackground());
        setForeground(isSelected ? list.getSelectionForeground() : list
                .getForeground());

        setFont(list.getFont());
        setBorder((cellHasFocus) ? UIManager
                .getBorder("List.focusCellHighlightBorder") : m_noFocusBorder);

        return this;
    }

    public void paint(Graphics g) {
        m_fm = g.getFontMetrics();

        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        getBorder().paintBorder(this, g, 0, 0, getWidth(), getHeight());

        g.setColor(getForeground());
        g.setFont(getFont());
        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top + m_fm.getAscent();

        StringTokenizer st = new StringTokenizer(getText(), "\t");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            g.drawString(str, x, y);
            //insert distance for each tab
            x += m_fm.stringWidth(str) + 50;

            if (!st.hasMoreTokens()) {
                break;
            }
        }
    }
}
