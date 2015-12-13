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
 * Remote interface of a chatroom.
 * @author kim
 */
public interface ChatRoom extends Remote {
    
    /**
     * getID
     * @return id of the chatroom
     * @throws RemoteException
     */
    public int getID() throws RemoteException;
    
    /**
     * getMessage
     * @param client client that requests the message (needed so we can filter
     * out message in case of block)
     * @return the most recent message in the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public String getMessage(Client client) throws RemoteException;
    
    /**
     * getUsers
     * @return list of clients connected to the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public ArrayList<Client> getUsers() throws RemoteException;
    
    /**
     * addMessage
     * @param user user that sends the message
     * @param message message to broadcast to the other users
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void addMessage(Client user, String message) throws RemoteException;
    
    /**
     * addUser
     * @param user user to be added to the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void addUser(Client user) throws RemoteException;
    
    /**
     * removeUser
     * @param user user to be removed from the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void removeUser(Client user) throws RemoteException;
    
    /**
     * Method that destroys the chatroom, will first do some quit-work.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void destroy() throws RemoteException;
    
    /**
     * getCreator
     * @return creator/host of the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public Client getCreator() throws RemoteException;
    
    /**
     * equals
     * @param r chatroom to compare
     * @return boolean
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public boolean equals(ChatRoom r) throws RemoteException;
    
    /**
     * chatRoomIsPublic
     * @return boolean
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public boolean chatRoomIsPublic() throws RemoteException;
}
