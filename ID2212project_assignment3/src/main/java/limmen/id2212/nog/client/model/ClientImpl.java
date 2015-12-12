/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import limmen.id2212.nog.client.view.GuiController;
import limmen.id2212.nog.server.ChatRoom;

/**
 *
 * @author kim
 */
public class ClientImpl extends UnicastRemoteObject implements Client {
    private final String username;
    private final GuiController contr;
    private final ArrayList<String> blocked = new ArrayList();
    public ClientImpl(GuiController contr, String username) throws RemoteException{
        this.contr = contr;
        this.username = username;
    }
    @Override
    public void notifyChatRoomDestroyed(ChatRoom room) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() throws RemoteException {
        return username;
    }

    @Override
    public void updateClients(ArrayList<Client> clients) throws RemoteException {
        contr.updateMainFrameClients(clients);
    }

    @Override
    public void updateChatRooms(ArrayList<ChatRoom> chatRooms) throws RemoteException {
        contr.updateMainFrameChatRooms(chatRooms);
    }
    
    @Override
    public boolean equals(Client c) throws RemoteException {
        return c.getName().equals(username);
    }

    @Override
    public void updateChat(ChatRoom r) throws RemoteException {
        contr.updateChat(r);
    }

    @Override
    public void chatRoomDestroyed(String creator, int id) throws RemoteException {
        contr.chatRoomDestroyed(creator, id);
    }

    @Override
    public void blockClient(String c) throws RemoteException {
        if(!c.equals(username)){
            blocked.add(c);
            contr.updateBlocked(blocked);
        }
    }

    @Override
    public ArrayList<String> getBlockedList() throws RemoteException {
        return blocked;
    }

    @Override
    public void updateLeftChatRoom() throws RemoteException {
        contr.getChatRooms();
    }
    
}
