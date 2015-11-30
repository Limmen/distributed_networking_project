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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
    private DefaultTableModel model;
    private DefaultTableModel filterModel;
    private final String[][] rowData;
    private final String[] columnNames;
    private final SimpleDateFormat format;
    private final JTextField filter;
    private final TableRowSorter<DefaultTableModel> sorter;
    private final int rowsDisplayed = 15;
    private final JTable table;
    private final JScrollPane scrollPane;
    private ArrayList<Participant> participants;
    private final JCheckBox editBox;
    public MainPanel(GuiController contr){
        format = new SimpleDateFormat("yyyy/mm/dd");
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("Participants in NOG");
        lbl.setFont(Title);
        add(lbl, "span 2, align center");
        columnNames = new String[8];
        columnNames[0] = "Id";
        columnNames[1] = "Name";
        columnNames[2] = "Country";
        columnNames[3] = "Gender";
        columnNames[4] = "birthday";
        columnNames[5] = "height";
        columnNames[6] = "weight";
        columnNames[7] = "sport";
        rowData = new String[0][0];
        model = new DefaultTableModel(rowData,columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
        table.setFont(Plain);
        table.getTableHeader().setFont(PBold);
        sorter = new TableRowSorter(table.getModel());
        table.setRowSorter(sorter);
        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        Dimension dim = new Dimension(800,400);
        scrollPane.setPreferredSize(new Dimension(dim.width, table.getRowHeight()*rowsDisplayed));
        JPanel toggleEdit = new JPanel(new MigLayout("wrap 4"));
        lbl = new JLabel("Toggle edit-mode: ");
        lbl.setFont(PBold);
        toggleEdit.add(lbl, "span 1");
        editBox = new JCheckBox();
        editBox.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                if(editBox.isSelected()){
                    model = new DefaultTableModel(rowData,columnNames) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return true;
                        }
                    };
                    table.setModel(model);
                    updateParticipants(participants);
                }
                else{
                    model = new DefaultTableModel(rowData,columnNames) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    table.setModel(model);
                    updateParticipants(participants);
                }
                
            }
        });
        toggleEdit.add(editBox, "span 1");
        JButton save = new JButton("Save changes");
        save.setFont(Plain);
        save.addActionListener(contr. new SaveListener(this));
        toggleEdit.add(save, "span 1");
        JButton delete = new JButton("Delete selected row");
        delete.setFont(Plain);
        delete.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                ArrayList<Participant> updatedParticipants = new ArrayList();
                int row = table.getSelectedRow();
                if(row != -1){
                    for(Participant p : participants){
                        if(!(p.getID() == Integer.parseInt((String) table.getModel().getValueAt(row, 0)) &&
                                p.getName().equals(table.getModel().getValueAt(row, 1)) &&
                                p.getCountry().equals(table.getModel().getValueAt(row, 2)) &&
                                Character.toString(p.getGender()).equals(table.getModel().getValueAt(row, 3)) &&
                                format.format(p.getBirthday()).equals(table.getModel().getValueAt(row, 4)) &&
                                p.getHeight() == Float.parseFloat((String) table.getModel().getValueAt(row, 5)) &&
                                p.getWeight() == Float.parseFloat((String) table.getModel().getValueAt(row, 6)) &&
                                p.getSport().equals(table.getModel().getValueAt(row, 7)))){
                            updatedParticipants.add(p);
                        }
                    }
                    updateParticipants(updatedParticipants);
                }
            }
        } );
        toggleEdit.add(delete, "span 1");
        add(toggleEdit, "span 2, gaptop 30");
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
        add(scrollPane, "span 2, gaptop 10");
        JPanel navigator = new JPanel(new MigLayout("wrap 2"));
        JButton prev = new JButton("<");
        prev.setFont(PBold);
        prev.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(bar.getValue()- table.getRowHeight()*(rowsDisplayed-1));
            }
        } );
        navigator.add(prev, "span 1");
        JButton next = new JButton(">");
        next.setFont(PBold);
        next.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(bar.getValue() + table.getRowHeight()*(rowsDisplayed-1));
            }
        } );
        navigator.add(next, "span 1");
        JButton first = new JButton("first");
        first.setFont(PBold);
        first.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                int height = table.getRowHeight()*(rowsDisplayed-1);
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(0);
            }
        } );
        navigator.add(first, "span 1");
        JButton last = new JButton("last");
        last.setFont(PBold);
        last.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                JScrollBar bar = scrollPane.getVerticalScrollBar();
                bar.setValue(table.getRowCount()*table.getRowHeight());
            }
        } );
        navigator.add(last, "span 1");
        add(navigator, "span 2, align center");
        JPanel filterPanel = new JPanel(new MigLayout("wrap 2"));
        lbl = new JLabel("Filtering form: ");
        lbl.setFont(PBold);
        filterPanel.add(lbl, "span 2, gaptop 15");
        lbl = new JLabel("ID");
        lbl.setFont(Plain);
        filterPanel.add(lbl, "span 1");
        final JTextField idField = new JTextField(25);
        idField.setFont(Plain);
        filterPanel.add(idField, "span 1");
        lbl = new JLabel("Name");
        lbl.setFont(Plain);
        filterPanel.add(lbl, "span 1");
        final JTextField nameField = new JTextField(25);
        nameField.setFont(Plain);
        filterPanel.add(nameField, "span 1");
        lbl = new JLabel("Country");
        lbl.setFont(Plain);
        filterPanel.add(lbl, "span 1");
        final JTextField countryField = new JTextField(25);
        countryField.setFont(Plain);
        filterPanel.add(countryField, "span 1");
        lbl = new JLabel("Gender");
        lbl.setFont(Plain);
        filterPanel.add(lbl, "span 1");
        final JTextField genderField = new JTextField(25);
        genderField.setFont(Plain);
        filterPanel.add(genderField, "span 1");
        lbl = new JLabel("Birthday");
        lbl.setFont(Plain);
        filterPanel.add(lbl, "span 1");
        final JTextField birthdayField = new JTextField(25);
        birthdayField.setFont(Plain);
        filterPanel.add(birthdayField, "span 1");
        lbl = new JLabel("Height");
        lbl.setFont(Plain);
        filterPanel.add(lbl, "span 1");
        final JTextField heightField = new JTextField(25);
        heightField.setFont(Plain);
        filterPanel.add(heightField, "span 1");
        lbl = new JLabel("Weight");
        lbl.setFont(Plain);
        filterPanel.add(lbl, "span 1");
        final JTextField weightField = new JTextField(25);
        weightField.setFont(Plain);
        filterPanel.add(weightField, "span 1");
        lbl = new JLabel("Sport");
        lbl.setFont(Plain);
        filterPanel.add(lbl, "span 1");
        final JTextField sportField = new JTextField(25);
        sportField.setFont(Plain);
        filterPanel.add(sportField, "span 1");
        JPanel buttonsPanel = new JPanel(new MigLayout("wrap 2"));
        JButton apply = new JButton("apply filter");
        apply.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                filter(idField, nameField, countryField,
                        genderField, birthdayField, heightField,
                        weightField, sportField);
            }
        } );
        buttonsPanel.add(apply, "span 1");
        JButton clear = new JButton("clear");
        clear.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                clear(idField, nameField, countryField,
                        genderField, birthdayField, heightField,
                        weightField, sportField);
                filter(idField, nameField, countryField,
                        genderField, birthdayField, heightField,
                        weightField, sportField);
            }
        } );
        buttonsPanel.add(clear, "span 1");
        filterPanel.add(buttonsPanel, "span 1, align center");
        add(filterPanel, "span 2, align center");
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
    public void filterParticipants(ArrayList<Participant> filtered){
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
        ArrayList<Participant> filtered = new ArrayList();
        for(Participant p : participants){
            Boolean id = true;
            Boolean name = true;
            Boolean country = true;
            Boolean gender = true;
            Boolean birthday = true;
            Boolean height = true;
            Boolean weight = true;
            Boolean sport = true;
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
                    !(Float.toString(p.getHeight()).trim().equalsIgnoreCase(heightField.getText())))
                height = false;
            if(weightField.getText().length() > 0 &&
                    !(Float.toString(p.getWeight()).trim().equalsIgnoreCase(weightField.getText())))
                weight = false;
            if(sportField.getText().length() > 0 &&
                    !p.getSport().trim().equalsIgnoreCase(sportField.getText()))
                sport = false;
            if(id && name && country && gender && birthday && height && weight && sport)
                filtered.add(p);
        }
        filterParticipants(filtered);
    }
    
    private void clear(JTextField idField, JTextField nameField, JTextField countryField,
            JTextField genderField, JTextField birthdayField, JTextField heightField,
            JTextField weightField, JTextField sportField){
        idField.setText("");
        nameField.setText("");
        countryField.setText("");
        genderField.setText("");
        birthdayField.setText("");
        heightField.setText("");
        weightField.setText("");
        sportField.setText("");
    }
    public ArrayList<Participant> getTableData () {
        int rows = model.getRowCount(), cols = model.getColumnCount();
        ArrayList<Participant> tableParticipants = new ArrayList();
        // Object[][] tableData = new Object[rows][nCol];
        for (int i = 0 ; i < rows; i++){
            try{
                tableParticipants.add(new Participant(
                        Integer.parseInt((String) table.getValueAt(i, 0)),
                        (String) table.getValueAt(i, 1),
                        (Character)(((String) table.getValueAt(i, 2)).charAt(0)),
                        (String) table.getValueAt(i, 3),
                        format.parse((String) table.getValueAt(i, 4)),
                        Float.parseFloat((String) table.getValueAt(i, 5)),
                        Float.parseFloat((String) table.getValueAt(i,6)),
                        (String) table.getValueAt(i, 7)));
            }
            catch(ParseException e){
                e.printStackTrace();
            }
        }
        return tableParticipants;
    }
    
}


