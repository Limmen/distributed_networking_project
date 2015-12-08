/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.proj.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import limmen.id2212.proj.util.Participant;
import net.miginfocom.swing.MigLayout;

/**
 * The main GUI-frame for the application.
 * @author kim
 */
class MainFrame extends JFrame {
    private final GuiController contr;
    private final Container container;
    
    /**
     * Class constructor
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
                quit();
            }
        });
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    //Clean-up a few things before exiting
    private void quit(){
        contr.quit();
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
                try {
                    container.transitionToHome();
                    contr.getParticipants();
                    pack();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        item = new JMenuItem("Statistics");
        menu.add(item);
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    container.transitionToStatsPage();
                    contr.getParticipants();
                    pack();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        item = new JMenuItem("New participant");
        menu.add(item);
        item.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
                try {
                    container.transitionToNewParticipantPage();
                    pack();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        return menuBar;
    }
    
    /**
     * Method to update participantdata at mainPanel or statisticsPanel,
     * depending on which panel is active.
     * @param participants list of participants.
     */
    void updateParticipants(ArrayList<Participant> participants){
        try{
            container.updateParticipants(participants);
            container.updateStatistics(participants);
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
}
