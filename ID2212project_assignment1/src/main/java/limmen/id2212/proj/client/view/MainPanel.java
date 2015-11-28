/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import limmen.id2212.proj.client.model.Participant;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class MainPanel extends JPanel {
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final GuiController contr;
    private final DefaultTableModel model;
    private final String[] columnNames;
    private final SimpleDateFormat format;
    private final JTextField filter;
    private final TableRowSorter<DefaultTableModel> sorter;
    private final int rowsDisplayed = 15;
    private final JTable table;
    private final JScrollPane scrollPane;
    public MainPanel(GuiController contr){
        format = new SimpleDateFormat("yyyy/mm/dd");
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("Participants in NOG");
        lbl.setFont(Title);
        add(lbl, "span 2");
        columnNames = new String[8];
        columnNames[0] = "Id";
        columnNames[1] = "Name";
        columnNames[2] = "Country";
        columnNames[3] = "Gender";
        columnNames[4] = "birthday";
        columnNames[5] = "height";
        columnNames[6] = "weight";
        columnNames[7] = "sport";
        String[][] rowData = new String[0][0];
        model = new DefaultTableModel(rowData,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(20);
        table.setFont(Plain);
        table.getTableHeader().setFont(PBold);
        sorter = new TableRowSorter(table.getModel());
        table.setRowSorter(sorter);
        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Dimension dim = new Dimension(800,400);       
        scrollPane.setPreferredSize(new Dimension(dim.width, table.getRowHeight()*rowsDisplayed));        
        add(scrollPane, "span 2, gaptop 10");
        lbl = new JLabel("Filter: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        filter = new JTextField(25);
        filter.setFont(Plain);
        filter.getDocument().addDocumentListener(new DocumentListener(){            
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = filter.getText();
                
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }            
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = filter.getText();
                
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }            
            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        add(filter, "span 1");
        JButton prev = new JButton("previous");
        prev.setFont(PBold);
        prev.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                int height = table.getRowHeight()*(rowsDisplayed-1);
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue( bar.getValue()-height );
            }
        } );
        add(prev, "span 1");
        JButton next = new JButton("next");
        next.setFont(PBold);
        next.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                int height = table.getRowHeight()*(rowsDisplayed-1);
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue( bar.getValue()+height );
            }
        } );
        add(next, "span 1");
    }
    
    public void updateParticipants(ArrayList<Participant> participants){
        String[][] rowData = new String[participants.size()][8];
        for(int i = 0; i <  participants.size(); i++)
        {
            Participant p = participants.get(i);
            rowData[i][0] = Integer.toString(p.getID());
            rowData[i][1] = p.getName();
            rowData[i][2] = p.getCountry();
            rowData[i][3] = String.valueOf(p.getGender());
            rowData[i][4] = format.format(p.getBirthday());
            rowData[i][5] = Float.toString(p.getHeight());
            rowData[i][6] = Float.toString(p.getWeight());
            rowData[i][7] = p.getSport();                                    
        }
        model.setDataVector(rowData, columnNames);
        repaint();
        revalidate();
    }
}
