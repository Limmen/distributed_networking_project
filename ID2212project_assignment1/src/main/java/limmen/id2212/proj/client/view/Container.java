/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.view;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class Container extends JPanel {
     private final GuiController contr;
     private MainPanel mainPanel;
     private StatsPanel statsPanel;
    public Container(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 1, insets 50 50 50 50"));
        mainPanel = new MainPanel(contr);
        add(mainPanel, "span 1");
    }
    public void transitionToHome(){
        removeAll();
        mainPanel = new MainPanel(contr);
        add(statsPanel, "span 1");
    }
    public void transitionToStatsPage(){
        removeAll();
        statsPanel = new StatsPanel(contr);
        add(statsPanel, "span 1");
    }
}
