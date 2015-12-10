/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JTabbedPane;
import limmen.id2212.nog.client.model.Client;
import limmen.id2212.nog.server.ChatRoom;

/**
 *
 * @author kim
 */
public class Container extends JTabbedPane{
    private final GuiController contr;
    private MainPanel mainPanel;
    Container(GuiController contr){
        this.contr = contr;
        mainPanel = new MainPanel(contr);
        addTab("Chat center", mainPanel);
    }
    void updateMainFrameClients(ArrayList<Client> clients) throws RemoteException{
        mainPanel.updateMainFrameClients(clients);
    }
    void updateMainFrameChatRooms(ArrayList<ChatRoom> chatRooms) throws RemoteException{
        for(ChatRoom r : chatRooms){
            for(Client c : r.getUsers()){
                if(c.getName().equals(contr.getClient().getName())){
                    System.out.println("Name is equals but references is: " + c.equals(contr.getClient()));
                    System.out.println("The chatroom contains this user");
                    if(!checkIfTabisCreated("ChatRoom " +  r.getID())){
                        System.out.println("The chatRoom tab is not created");
                        addTab("ChatRoom " + r.getID(), new ChatPanel(r, contr));
                    }
                    System.out.println("The chatroom tab is created");
                }
            }
        }
        mainPanel.updateMainFrameChatRooms(chatRooms);
    }
    private boolean checkIfTabisCreated(String title){
        boolean bool = false;
        for(int i = 0; i < getTabCount(); i++){
            if(getTitleAt(i).equals(title))
                bool = true;
        }
        return bool;
    }
}
