/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import limmen.id2212.nog.server.integration.Participant;
import net.miginfocom.swing.MigLayout;

/**
 * Statistics-panel. Contains statistics about participants.
 * @author kim
 */
class StatsPanel extends JPanel {
    private final GuiController contr;
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final DefaultTableModel countryModel;
    private final String[] countryColumnNames;
    private final DefaultTableModel sportsModel;
    private final String[] sportsColumnNames;
    private ArrayList<Participant> participants;
    private final JLabel totalParts;
    private final JLabel totalCountries;
    private final JLabel totalSports;
    private final JLabel averageWeight;
    private final JLabel averageHeight;
    
    /**
     * Class constructor. Builds the UI and creates all swing-components.
     * @param contr GuiController
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    StatsPanel(GuiController contr) throws RemoteException{
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
        JScrollPane scroll = new JScrollPane(countryTable);
        scroll.setPreferredSize(new Dimension(425,200));
        add(scroll, "span 2");
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
        scroll = new JScrollPane(sportsTable);
        scroll.setPreferredSize(new Dimension(425,200));
        add(scroll, "span 2");
        
    }
    
    /**
     * Update participant-data in the UI
     * @param participants list of participants
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    void updateStatistics(ArrayList<Participant> participants) throws RemoteException{
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
    private int totalCountries(ArrayList<Participant> participants) throws RemoteException{
        ArrayList<String> countries = new ArrayList();
        for(Participant p : participants){
            if(!countries.contains(p.getCountry()))
                countries.add(p.getCountry());
        }
        return countries.size();
    }
    private int totalSports(ArrayList<Participant> participants) throws RemoteException{
        ArrayList<String> sports = new ArrayList();
        for(Participant p : participants){
            if(!sports.contains(p.getSport()))
                sports.add(p.getSport());
        }
        return sports.size();
    }
    private float averageWeight(ArrayList<Participant> participants) throws RemoteException{
        float totalWeight = 0;
        for(Participant p : participants){
            totalWeight = totalWeight + p.getWeight();
        }
        return totalWeight/participants.size();
    }
    private float averageHeight(ArrayList<Participant> participants) throws RemoteException{
        float totalHeight = 0;
        for(Participant p : participants){
            totalHeight = totalHeight + p.getHeight();
        }
        return totalHeight/participants.size();
    }
    //method to create table of number of participants per country
    private void updateParticipantsPerCountry(ArrayList<Participant> participants) throws RemoteException{
        HashMap<String, Integer> table = new HashMap();
        for(Participant p : participants){
            if(!table.containsKey(p.getCountry())){
                int n = 0;
                for(Participant p2 : participants){
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
    //method to create table of number of participants per sport
    public void updateParticipantsPerSport(ArrayList<Participant> participants) throws RemoteException{
        HashMap<String, Integer> table = new HashMap();
        for(Participant p : participants){
            if(!table.containsKey(p.getSport())){
                int n = 0;
                for(Participant p2 : participants){
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