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
    private ArrayList<Participant> participants;
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
        lbl = new JLabel("Search: ");
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
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(bar.getValue()- table.getRowHeight()*(rowsDisplayed-1));
            }
        } );
        add(prev, "span 1");
        JButton next = new JButton("next");
        next.setFont(PBold);
        next.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(bar.getValue() + table.getRowHeight()*(rowsDisplayed-1));
            }
        } );
        add(next, "span 1");
        JButton last = new JButton("last");
        last.setFont(PBold);
        last.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(table.getRowCount()*table.getRowHeight());
            }
        } );
        add(last, "span 1");
        JButton first = new JButton("first");
        first.setFont(PBold);
        first.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                int height = table.getRowHeight()*(rowsDisplayed-1);
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(0);
            }
        } );
        add(first, "span 1");
        lbl = new JLabel("Filtering form: ");
        lbl.setFont(PBold);
        add(lbl, "span 2, gaptop 15");
        lbl = new JLabel("ID");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField idField = new JTextField(25);
        idField.setFont(Plain);
        add(idField, "span 1");
        lbl = new JLabel("Name");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField nameField = new JTextField(25);
        nameField.setFont(Plain);
        add(nameField, "span 1");
        lbl = new JLabel("Country");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField countryField = new JTextField(25);
        countryField.setFont(Plain);
        add(countryField, "span 1");
        lbl = new JLabel("Gender");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField genderField = new JTextField(25);
        genderField.setFont(Plain);
        add(genderField, "span 1");
        lbl = new JLabel("Birthday");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField birthdayField = new JTextField(25);
        birthdayField.setFont(Plain);
        add(birthdayField, "span 1");
        lbl = new JLabel("Height");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField heightField = new JTextField(25);
        heightField.setFont(Plain);
        add(heightField, "span 1");
        lbl = new JLabel("Weight");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField weightField = new JTextField(25);
        weightField.setFont(Plain);
        add(weightField, "span 1");
        lbl = new JLabel("Sport");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField sportField = new JTextField(25);
        sportField.setFont(Plain);
        add(sportField, "span 1");
        JButton apply = new JButton("apply");
        apply.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                filter(idField, nameField, countryField,
                        genderField, birthdayField, heightField,
                        weightField, sportField);
            }
        } );      add(apply, "span 2");
    }    
    public void updateParticipants(ArrayList<Participant> participants){
        this.participants = participants;
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
    public void filterParticipants(ArrayList<Participant> filtered){
        System.out.println("filtering??");
        String[][] rowData = new String[filtered.size()][8];
        for(int i = 0; i <  filtered.size(); i++)
        {
            Participant p = filtered.get(i);
            rowData[i][0] = Integer.toString(p.getID());
            rowData[i][1] = p.getName();
            rowData[i][2] = p.getCountry();
            rowData[i][3] = Character.toString(p.getGender());
            rowData[i][4] = format.format(p.getBirthday());
            rowData[i][5] = Float.toString(p.getHeight());
            rowData[i][6] = Float.toString(p.getWeight());
            rowData[i][7] = p.getSport();
        }
        model.setDataVector(rowData, columnNames);
        repaint();
        revalidate();
    }    
    public void filter(JTextField idField, JTextField nameField, JTextField countryField,
            JTextField genderField, JTextField birthdayField, JTextField heightField,
            JTextField weightField, JTextField sportField){
        Boolean id = true;
        Boolean name = true;
        Boolean country = true;
        Boolean gender = true;
        Boolean birthday = true;
        Boolean height = true;
        Boolean weight = true;
        Boolean sport = true;
        ArrayList<Participant> filtered = new ArrayList();
        for(Participant p : participants){
            if(idField.getText().length() > 0 &&
                    !Integer.toString(p.getID()).trim().equalsIgnoreCase(idField.getText()))
                id = false;
            if(nameField.getText().length() > 0 &&
                    !p.getName().trim().equalsIgnoreCase(nameField.getText()))
                name = false;
            if(countryField.getText().length() > 0 &&
                    !p.getCountry().trim().equalsIgnoreCase(countryField.getText()))
                country = false;
            if(genderField.getText().length() > 0  &&
                    !Character.toString(p.getGender()).trim().equalsIgnoreCase(genderField.getText()))
                gender = false;
            if(birthdayField.getText().length() > 0 &&
                    !format.format(p.getBirthday()).trim().equalsIgnoreCase(birthdayField.getText()))
                birthday = false;
            if(heightField.getText().length() > 0 &&
                    !Float.toString(p.getHeight()).trim().equalsIgnoreCase(heightField.getText()))
                height = false;
            if(weightField.getText().length() > 0 &&
                    !Float.toString(p.getWeight()).trim().equalsIgnoreCase(weightField.getText()))
                weight = false;
            if(sportField.getText().length() > 0 &&
                    !p.getSport().trim().equalsIgnoreCase(sportField.getText()))
                sport = false;
            if(id && name && country && gender && birthday && height && weight && sport)
                filtered.add(p);
        }
        filterParticipants(filtered);
    }   
}


