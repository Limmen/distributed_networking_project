/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 * Start-frame of the application. This frame contains a form for connecting
 * to a specified server.
 * @author kim
 */
class StartFrame extends JFrame {
    private final GuiController contr;
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    
    /**
     * Class constructor. Builds the UI with all Swing-components.
     * @param contr GuiController
     */
    StartFrame(GuiController contr){
        this.contr = contr;
        this.setLayout(new MigLayout());
        this.setTitle("NOG Informationsystem");
        this.setContentPane(new JScrollPane(new Container()));
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    //Container panel for the start-frame
    private class Container extends JPanel{
        Container(){
            setLayout(new MigLayout("wrap 1, insets 50 50 50 50"));  //insets T, L, B, R
            add(new ConnectPanel(), "span 1");
        }
    }
    //Connect panel for the start-frame
    private class ConnectPanel extends JPanel{
        JTextField hostField;
        JTextField portField;
        public ConnectPanel(){
            setLayout(new MigLayout("wrap 2"));
            JLabel lbl = new JLabel("Connect to a server");
            lbl.setFont(Title);
            add(lbl, "span 2");
            lbl = new JLabel("Host: ");
            lbl.setFont(PBold);
            add(lbl, "span 1");
            hostField = new JTextField(25);
            hostField.setFont(Plain);
            add(hostField, "span 1");
            lbl = new JLabel("port");
            lbl.setFont(PBold);
            add(lbl, "span 1");
            portField = new JTextField(25);
            portField.setFont(Plain);
            add(portField, "span 1");
            JButton connectButton = new JButton("Connect");
            connectButton.setFont(PBold);
            connectButton.addActionListener(contr.new ConnectListener(hostField, portField));
            add(connectButton, "span 2");
        }
    }
}
