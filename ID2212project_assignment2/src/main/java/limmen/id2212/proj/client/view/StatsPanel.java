/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import limmen.id2212.proj.util.ParticipantImpl;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class StatsPanel extends JPanel {
    private final GuiController contr;
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final DefaultTableModel countryModel;
    private final String[] countryColumnNames;
    private final DefaultTableModel sportsModel;
    private final String[] sportsColumnNames;
    private ArrayList<ParticipantImpl> participants;
    private final JLabel totalParts;
    private final JLabel totalCountries;
    private final JLabel totalSports;
    private final JLabel averageWeight;
    private final JLabel averageHeight;
    public StatsPanel(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("NOG statistics");
        lbl.setFont(Title);
        add(lbl, "span 2, align center, gapbottom 30");
        lbl = new JLabel("Number of participants: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        totalParts = new JLabel("");
        totalParts.setFont(Plain);
        add(totalParts, "span 1");
        lbl = new JLabel("Number of countries: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        totalCountries = new JLabel("");
        totalCountries.setFont(Plain);
        add(totalCountries, "span 1");
        lbl = new JLabel("Number of sports: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        totalSports = new JLabel("");
        totalSports.setFont(Plain);
        add(totalSports, "span 1");
        lbl = new JLabel("Average weight: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        averageWeight = new JLabel("");
        averageWeight.setFont(Plain);
        add(averageWeight, "span 1");
        lbl = new JLabel("Average height: ");
        lbl.setFont(PBold);
        add(lbl, "span 1");
        averageHeight = new JLabel("");
        averageHeight.setFont(Plain);
        add(averageHeight, "span 1");
        countryColumnNames = new String[2];
        countryColumnNames[0] = "Country";
        countryColumnNames[1] = "Number of participants";
        String[][] RowData = new String[0][0];
        lbl = new JLabel("Participants/Country");
        lbl.setFont(Plain);
        add(lbl, "span 2, gaptop 20");
        countryModel = new DefaultTableModel(RowData,countryColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable countryTable = new JTable(countryModel);
        countryTable.setFont(Plain);
        countryTable.getTableHeader().setFont(PBold);
        add(new JScrollPane(countryTable), "span 2");       
        sportsColumnNames = new String[2];
        sportsColumnNames[0] = "Country";
        sportsColumnNames[1] = "Number of participants";
        RowData = new String[0][0];
        lbl = new JLabel("Participants/Sport");
        lbl.setFont(Plain);
        add(lbl, "span 2, gaptop 20");
        sportsModel = new DefaultTableModel(RowData,sportsColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable sportsTable = new JTable(sportsModel);
        sportsTable.setFont(Plain);
        sportsTable.getTableHeader().setFont(PBold);
        add(new JScrollPane(sportsTable), "span 2");
    }
    public void updateStatistics(ArrayList<ParticipantImpl> participants){
        this.participants = participants;
        totalParts.setText(Integer.toString(participants.size()));
        totalCountries.setText(Integer.toString(totalCountries(participants)));
        totalSports.setText(Integer.toString(totalSports(participants)));
        averageWeight.setText(Float.toString(averageWeight(participants)));
        averageHeight.setText(Float.toString(averageHeight(participants)));
        updateParticipantsPerCountry(participants);
        updateParticipantsPerSport(participants);
        repaint();
        revalidate();
        
    }
    private int totalCountries(ArrayList<ParticipantImpl> participants){
        ArrayList<String> countries = new ArrayList();
        for(ParticipantImpl p : participants){
            if(!countries.contains(p.getCountry()))
                countries.add(p.getCountry());
        }
        return countries.size();
    }
    private int totalSports(ArrayList<ParticipantImpl> participants){
        ArrayList<String> sports = new ArrayList();
        for(ParticipantImpl p : participants){
            if(!sports.contains(p.getSport()))
                sports.add(p.getSport());
        }
        return sports.size();
    }
    private float averageWeight(ArrayList<ParticipantImpl> participants){
        float totalWeight = 0;
        for(ParticipantImpl p : participants){
            totalWeight = totalWeight + p.getWeight();
        }
        return totalWeight/participants.size();
    }
    private float averageHeight(ArrayList<ParticipantImpl> participants){
        float totalHeight = 0;
        for(ParticipantImpl p : participants){
            totalHeight = totalHeight + p.getHeight();
        }
        return totalHeight/participants.size();
    }
    public void updateParticipantsPerCountry(ArrayList<ParticipantImpl> participants){
        HashMap<String, Integer> table = new HashMap();
        for(ParticipantImpl p : participants){
            if(!table.containsKey(p.getCountry())){
                int n = 0;
                for(ParticipantImpl p2 : participants){
                    if(p2.getCountry().equals(p.getCountry()))
                        n = n +1;
                }
                table.put(p.getCountry(), n);
            }
        }
        String[][] rowData = new String[table.size()][2];
        int i = 0;
        for (Map.Entry<String, Integer> entry : table.entrySet()) {
            rowData[i][0] = entry.getKey();
            rowData[i][1] = Integer.toString(entry.getValue());
            i++;
        }
        countryModel.setDataVector(rowData, countryColumnNames);
    }
    public void updateParticipantsPerSport(ArrayList<ParticipantImpl> participants){
        HashMap<String, Integer> table = new HashMap();
        for(ParticipantImpl p : participants){
            if(!table.containsKey(p.getSport())){
                int n = 0;
                for(ParticipantImpl p2 : participants){
                    if(p2.getSport().equals(p.getSport()))
                        n = n +1;
                }
                table.put(p.getSport(), n);
            }
        }
        String[][] rowData = new String[table.size()][2];
        int i = 0;
        for (Map.Entry<String, Integer> entry : table.entrySet()) {
            rowData[i][0] = entry.getKey();
            rowData[i][1] = Integer.toString(entry.getValue());
            i++;
        }
        sportsModel.setDataVector(rowData, sportsColumnNames);
    }
    
}