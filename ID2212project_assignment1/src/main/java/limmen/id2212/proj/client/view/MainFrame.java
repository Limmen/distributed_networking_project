/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
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
 *
 * @author kim
 */
public class MainFrame extends JFrame {
    private final GuiController contr;
    private final Container container;
    public MainFrame(GuiController contr){
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
                    contr.updateParticipants();
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
                    contr.updateParticipants();
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
                    container.transitionToNewPartPage();
                    pack();
                }
                catch(Exception e)
                {
                    
                }
                
            }
        });
        return menuBar;
    }
    public void updateParticipants(ArrayList<Participant> participants){
        container.updateParticipants(participants);
        container.updateStatistics(participants);
    }
}
