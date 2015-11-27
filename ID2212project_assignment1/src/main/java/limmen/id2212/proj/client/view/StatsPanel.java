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
public class StatsPanel extends JPanel {
    private final GuiController contr;
    public StatsPanel(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
    }
}