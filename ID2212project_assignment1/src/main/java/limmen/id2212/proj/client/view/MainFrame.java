/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.proj.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import limmen.id2212.proj.client.model.Participant;
import net.miginfocom.swing.MigLayout;

/**
 * MainFrame class. Represents the GUI for the client when he has specified
 * a server to interact with.
 * @author kim
 */
class MainFrame extends JFrame {
    private final GuiController contr;
    private final Container container;
    
    /**
     * Class constructor.
     * @param contr GuiController
     */
    MainFrame(GuiController contr){
        this.contr = contr;
        this.setLayout(new MigLayout());
        this.setTitle("NOG Informationsystem");
        container = new Container(contr);
        this.setContentPane(new JScrollPane(container));
        this.setJMenuBar(createMenu());
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                closeMainFrame();
            }
        });
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    private void closeMainFrame(){
        contr.closeMainFrame();
    }
    //Creates JMenuBar for the MainFrame
    private JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);
        JMenuItem item = new JMenuItem("Home");
        menu.add(item);
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                container.transitionToHome();
                contr.getParticipants();
                pack();
            }
        });
        item = new JMenuItem("Statistics");
        menu.add(item);
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                container.transitionToStatsPage();
                contr.getParticipants();
                pack();
            }
        });
        item = new JMenuItem("New participant");
        menu.add(item);
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                container.transitionToNewPartPage();
                pack();
            }
        });
        return menuBar;
    }
    /**
     * Method to update participantdata at mainPanel or statisticsPanel,
     * depending on which panel is active.
     * @param participants list of all participants.
     */
    void updateParticipants(ArrayList<Participant> participants){
        container.updateParticipants(participants);
        container.updateStatistics(participants);
    }
}
