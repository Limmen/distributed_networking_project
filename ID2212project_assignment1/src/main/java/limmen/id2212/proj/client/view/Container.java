/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.proj.client.view;

import java.util.ArrayList;
import javax.swing.JPanel;
import limmen.id2212.proj.client.model.Participant;
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
     * This method is called when the user selects the home-page from the
     * top-bar menu.
     */
    void transitionToHome(){
        removeAll();
        mainPanel = new MainPanel(contr);
        add(mainPanel, "span 1");
    }
    
    /**
     * This method is called when the user selects the statistics-page from the
     * top-bar menu.
     */
    void transitionToStatsPage(){
        removeAll();
        statsPanel = new StatsPanel(contr);
        add(statsPanel, "span 1");
    }
    
    /**
     * This method is called when the user selects the new-participant-page
     * from the top-bar menu.
     */
    void transitionToNewPartPage(){
        removeAll();
        newParticipantPanel = new NewParticipantPanel(contr);
        add(newParticipantPanel, "span 1");
    }
    
    /**
     * Updates participant-data in the mainPanel if the panel is active.
     * @param participants list of all participants.
     */
    void updateParticipants(ArrayList<Participant> participants){
        if(mainPanel != null)
            mainPanel.updateParticipants(participants);
    }
    
    /**
     * Updates participant-data in the statistics-panel if the panel is active.
     * @param participants list of all participants
     */
    void updateStatistics(ArrayList<Participant> participants){
        if(statsPanel != null)
            statsPanel.updateStatistics(participants);
    }
}
