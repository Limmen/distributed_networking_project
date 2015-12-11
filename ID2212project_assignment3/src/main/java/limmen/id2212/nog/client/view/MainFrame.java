/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import limmen.id2212.nog.client.model.Client;
import limmen.id2212.nog.server.ChatRoom;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
class MainFrame extends JFrame {
    private final GuiController contr;
    private final Container container;
    MainFrame(GuiController contr){
        this.contr = contr;
        this.setLayout(new MigLayout());
        this.setTitle("NOG Chat rooms");
        this.container = new Container(contr);
        this.setContentPane(new JScrollPane(container));
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
    private void quit(){
        contr.quit();
    }    
    void updateMainFrameClients(ArrayList<Client> clients) throws RemoteException{
        container.updateMainFrameClients(clients);
    }
    void updateMainFrameChatRooms(ArrayList<ChatRoom> chatRooms) throws RemoteException{
        container.updateMainFrameChatRooms(chatRooms);
    }
    void updateChat(ChatRoom r, ArrayList<String> messages){
        container.updateChat(r, messages);
    }
}
