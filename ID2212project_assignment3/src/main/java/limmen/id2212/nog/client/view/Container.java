/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.nog.client.view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JPanel;
import limmen.id2212.nog.client.model.Client;
import limmen.id2212.nog.server.ChatRoom;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class Container extends JPanel{
        public MainPanel mainPanel;
        Container(){
            setLayout(new MigLayout("wrap 1, insets 50 50 50 50"));  //insets T, L, B, R
            mainPanel = new MainPanel();
            add(mainPanel, "span 1");            
        }
        void updateMainFrameClients(ArrayList<Client> clients) throws RemoteException{
            mainPanel.updateMainFrameClients(clients);
        }
        void updateMainFrameChatRooms(ArrayList<ChatRoom> chatRooms) throws RemoteException{
            mainPanel.updateMainFrameChatRooms(chatRooms);
        }
    }
