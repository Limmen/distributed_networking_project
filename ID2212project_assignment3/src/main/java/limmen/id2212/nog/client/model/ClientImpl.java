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
 * Implementation of the client-interface.
 * Extends UniCastRemoteObject to automaticly export the remote object.
 * @author kim
 */
public class ClientImpl extends UnicastRemoteObject implements Client {
    private final String username;
    private final GuiController contr;
    private final ArrayList<String> blocked = new ArrayList();
    
    /**
     * Class constructor.
     * @param contr Guicontroller
     * @param username username of the client. Unique identifier.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public ClientImpl(GuiController contr, String username) throws RemoteException{
        this.contr = contr;
        this.username = username;
    }
    /**
     * getName
     * @return username of the client
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public String getName() throws RemoteException {
        return username;
    }
    
    /**
     * This method is called by the server when client-state changed and
     * GUI needs to be updated.
     * @param clients list of updated clients.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void updateClients(ArrayList<Client> clients) throws RemoteException {
        contr.updateMainFrameClients(clients);
    }
    
    /**
     * This method is called by the server when chatrooms have been updated.
     * Will result in GUI-updates for the client.
     * @param chatRooms
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void updateChatRooms(ArrayList<ChatRoom> chatRooms) throws RemoteException {
        contr.updateMainFrameChatRooms(chatRooms);
    }
    
    /**
     * Equals method. Username is a unique identifier.
     * @param c client to compare
     * @return boolean
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public boolean equals(Client c) throws RemoteException {
        return c.getName().equals(username);
    }
    
    /**
     * this method is called by the chatroom when something happens,
     * e.g if a client posts a message to the room, a client joins/leaves the room.
     * Will result in GUI updates.
     * @param r chatroom to update.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void updateChat(ChatRoom r) throws RemoteException {
        contr.updateChat(r);
    }
    
    /**
     * This method is called when a chatroom that this client is present in
     * gets destroyed.
     * @param creator name of the user that destroyed the chatroom.
     * @param id id of the chatroom.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void chatRoomDestroyed(String creator, int id) throws RemoteException {
        contr.chatRoomDestroyed(creator, id);
    }
    
    /**
     * Method to block a client.
     * @param c client to block.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void blockClient(String c) throws RemoteException {
        if(!c.equals(username)){
            blocked.add(c);
            contr.updateBlocked(blocked);
        }
    }
    
    /**
     * getBlockedList
     * @return list of usernames of the clients that are blocked.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public ArrayList<String> getBlockedList() throws RemoteException {
        return blocked;
    }
    
    /**
     * Method that is called by the server if the clients request to leave
     * a chatroom is successful.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void updateLeftChatRoom() throws RemoteException {
        contr.getChatRooms();
    }
    
    /**
     * Method to unblock a blocked client.
     * @param c username of client to unblock.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void unBlockClient(String c) throws RemoteException {
        blocked.remove(c);
        contr.updateBlocked(blocked);
    }
    
}
