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
     *
     * @param client
     * @return
     * @throws RemoteException
     */
    public ArrayList<String> getMessages(Client client) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<Client> getUsers() throws RemoteException;

    /**
     *
     * @param user
     * @param message
     * @throws RemoteException
     */
    public void addMessage(Client user, String message) throws RemoteException;

    /**
     *
     * @param user
     * @throws RemoteException
     */
    public void addUser(Client user) throws RemoteException;

    /**
     *
     * @param user
     * @throws RemoteException
     */
    public void removeUser(Client user) throws RemoteException;

    /**
     *
     * @throws RemoteException
     */
    public void destroy() throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public Client getCreator() throws RemoteException;

    /**
     *
     * @param r
     * @return
     * @throws RemoteException
     */
    public boolean equals(ChatRoom r) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public boolean chatRoomIsPublic() throws RemoteException;
}
