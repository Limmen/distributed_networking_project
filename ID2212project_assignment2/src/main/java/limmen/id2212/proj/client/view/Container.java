/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.proj.client.view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JPanel;
import limmen.id2212.proj.util.Participant;
import net.miginfocom.swing.MigLayout;

/**
 * Container-panel for the Mainframe
 * @author kim
 */
class Container extends JPanel {
    private final GuiController contr;
    private MainPanel mainPanel;
    private StatsPanel statsPanel;
    private NewParticipantPanel newParticipantPanel;
    
    /**
     * Class constructor
     * @param contr GUI-controller
     */
    Container(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 1, insets 50 50 50 50"));
        mainPanel = new MainPanel(contr);
        add(mainPanel, "span 1");
    }
    
    /**
     * This method is called when the user selects the home-page from
     * the top-bar menu.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void transitionToHome() throws RemoteException{
        removeAll();
        mainPanel = new MainPanel(contr);
        add(mainPanel, "span 1");
    }
    
    /**
     * This method is called when the user selects the statistics-page from
     * the top-bar menu.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void transitionToStatsPage() throws RemoteException{
        removeAll();
        statsPanel = new StatsPanel(contr);
        add(statsPanel, "span 1");
    }
    
    /**
     * This method is called when the user selects the new-participant-page
     * from the top-bar menu.
     */
    public void transitionToNewParticipantPage(){
        removeAll();
        newParticipantPanel = new NewParticipantPanel(contr);
        add(newParticipantPanel, "span 1");
    }
    
    /**
     * Method to update the participant-data in the mainPanel.
     * @param participants list of participants
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void updateParticipants(ArrayList<Participant> participants) throws RemoteException{
        if(mainPanel != null)
            mainPanel.updateParticipants(participants);
    }
    
    /**
     * Method to update the participant-data in the statisticsPanel.
     * @param participants list of participants
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void updateStatistics(ArrayList<Participant> participants) throws RemoteException{
        if(statsPanel != null)
            statsPanel.updateStatistics(participants);
    }
}
