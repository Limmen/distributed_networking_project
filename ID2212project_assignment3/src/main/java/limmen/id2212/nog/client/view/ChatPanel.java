/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.rmi.RemoteException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import limmen.id2212.nog.server.ChatRoom;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class ChatPanel extends JPanel{
    private final ChatRoom chatRoom;
    private final GuiController contr;
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    public ChatPanel(ChatRoom chatRoom, GuiController contr) throws RemoteException{
        this.chatRoom = chatRoom;
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("ChatRoom " + chatRoom.getID());
        lbl.setFont(Title);
        add(lbl, "span 2");
        JTextArea chatarea = new JTextArea("");
        chatarea.setLineWrap(true);
        chatarea.setEditable(false);
        chatarea.setFont(Plain);
        JScrollPane scroll = new JScrollPane(chatarea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(250, 250));
        add(scroll, "span 2");
        JTextArea messageArea = new JTextArea("");
        messageArea.setLineWrap(true);
        messageArea.setFont(Plain);
        scroll = new JScrollPane(messageArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(250, 50));
        add(scroll, "span 1");
        JButton sendButton = new JButton("Send");
        sendButton.setFont(Title); 
        sendButton.addActionListener(contr. new SendListener(chatRoom, messageArea));
        
    }
    
    
}
