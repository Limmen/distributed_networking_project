/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import limmen.id2212.nog.client.model.Client;

/**
 * Remote interface of the NogChatServer
 * @author kim
 */
public interface NogChatServer extends Remote {
    
    /**
     * getChatRooms
     * @param c client that requests the list of chatrooms. (Needed to filter out
     * private chatrooms)
     * @return list of chatrooms
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public ArrayList<ChatRoom> getChatRooms(Client c) throws RemoteException;
    
    /**
     * getUsers
     * @return list of users connected to the server
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public ArrayList<Client> getUsers() throws RemoteException;
    
    /**
     * getClient
     * @param username username of the client reference to be retrieved
     * @return Reference to remote client object.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public Client getClient(String username)  throws RemoteException;
    
    /**
     * addChatRoom
     * @param creator reference to the client that creates the chatroom.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void addChatRoom(Client creator) throws RemoteException;
    
    /**
     * registerClient
     * @param client client to be registered
     * @throws RemoteException thrown when problem with remote method-call occurs.
     * @throws NameAlreadyTakenException Thrown when the username is already taken by another client.
     */
    public void registerClient(Client client) throws RemoteException, NameAlreadyTakenException;
    
    /**
     * deRegisterClient
     * @param client client to deRegister from the server
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void deRegisterClient(Client client) throws RemoteException;
    
    /**
     * joinChatRoom
     * @param client client that wants to join
     * @param id id of the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void joinChatRoom(Client client, int id) throws RemoteException;
    
    /**
     * destroyChatRoom
     * @param id id of the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void destroyChatRoom(int id) throws RemoteException;
    
    /**
     * leaveChatRoom
     * @param id id of the chatroom
     * @param client client that wants to leave
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void leaveChatRoom(int id, Client client) throws RemoteException;
    
    /**
     * addPrivateChatRoom
     * @param creator client that initiates the private chatroom
     * @param c the other client
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void addPrivateChatRoom(Client creator, Client c) throws RemoteException;
}
