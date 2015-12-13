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
 * Startframe of the application. Contains form for entering username before
 * connecting to the chatserver.
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
        this.setTitle("NOG Chat Rooms");
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
            add(new RegisterPanel(), "span 1");
        }
    }
    //Register panel for the start-frame
    private class RegisterPanel extends JPanel{
        JTextField nameField;
        public RegisterPanel(){
            setLayout(new MigLayout("wrap 2"));
            JLabel lbl = new JLabel("Join the chat-rooms");
            lbl.setFont(Title);
            add(lbl, "span 2");
            lbl = new JLabel("Username: ");
            lbl.setFont(PBold);
            add(lbl, "span 1");
            nameField = new JTextField(25);
            nameField.setFont(Plain);
            add(nameField, "span 1");
            JButton joinButton = new JButton("Join");
            joinButton.setFont(PBold);
            joinButton.addActionListener(contr.new RegisterListener(nameField));
            add(joinButton, "span 2");
        }
    }
}
