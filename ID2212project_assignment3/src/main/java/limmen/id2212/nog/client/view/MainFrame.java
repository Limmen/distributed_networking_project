/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
class MainFrame extends JFrame {
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);    
    private final GuiController contr;    
    
    MainFrame(GuiController contr){        
        this.contr = contr;
        this.setLayout(new MigLayout());
        this.setTitle("Edit Participant");
        this.setContentPane(new JScrollPane(new Container()));
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    private class Container extends JPanel{
        Container(){
            setLayout(new MigLayout("wrap 1, insets 50 50 50 50"));  //insets T, L, B, R
            add(new MainPanel(), "span 1");
            
        }
    }
    private class MainPanel extends JPanel{
        public MainPanel(){
            setLayout(new MigLayout("wrap 2"));
            JLabel lbl = new JLabel("NOG Chat rooms");
            lbl.setFont(Title);
            add(lbl, "span 2");            
        }
    }
    
}
