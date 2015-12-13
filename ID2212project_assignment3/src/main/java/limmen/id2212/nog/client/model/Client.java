/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import limmen.id2212.nog.server.ChatRoom;

/**
 * Client remote interface.
 * @author kim
 */
public interface Client extends Remote {
    
    /**
     * getName
     * @return name of the client
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public String getName() throws RemoteException;
    
    /**
     * updateClients, this method is called by the server when a client
     * leaves/joins the register center or a chatroom.
     * @param clients the updated list of clients.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void updateClients(ArrayList<Client> clients) throws RemoteException;
    
    /**
     * updateChatRooms, this method is called by the server when a chatroom
     * is created/destroyed.
     * @param chatRooms the updated list of chatrooms.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void updateChatRooms(ArrayList<ChatRoom> chatRooms) throws RemoteException;
    
    /**
     * equals method.
     * @param c Client to compare with
     * @return boolean
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public boolean equals(Client c) throws RemoteException;
    
    /**
     * updateChat, this method is called by the chatroom when something happens,
     * e.g if a client posts a message to the room, a client joins/leaves the room
     * etc.
     * @param r Chatroom that is to be updated.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void updateChat(ChatRoom r) throws RemoteException;
    
    /**
     * Method that is called when a chatroom that this client is present in
     * gets destroyed.
     * @param creator name of the user that destroyed the chatroom
     * @param id id of the room that was destroyed
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void chatRoomDestroyed(String creator, int id) throws RemoteException;
    /**
     * Method that adds a username to the list of blocked clients
     * @param c
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void blockClient(String c) throws RemoteException;
    
    /**
     * Method to unblock a user by removing the username from the list of
     * blocked clients.
     * @param c username to unblock
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void unBlockClient(String c) throws RemoteException;
    
    /**
     * getBlockedList
     * @return list of blocked users.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public ArrayList<String> getBlockedList() throws RemoteException;
    
    /**
     * Method that is called by the server if the clients request to leave
     * a chatroom is successful.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void updateLeftChatRoom() throws RemoteException;
}
