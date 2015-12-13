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
 *
 * @author kim
 */
public interface NogChatServer extends Remote {
    
    /**
     *
     * @param c
     * @return
     * @throws RemoteException
     */
    public ArrayList<ChatRoom> getChatRooms(Client c) throws RemoteException;

    /**
     *
     * @return
     * @throws RemoteException
     */
    public ArrayList<Client> getUsers() throws RemoteException;

    /**
     *
     * @param username
     * @return
     * @throws RemoteException
     */
    public Client getClient(String username)  throws RemoteException;

    /**
     *
     * @param creator
     * @throws RemoteException
     */
    public void addChatRoom(Client creator) throws RemoteException;

    /**
     *
     * @param client
     * @throws RemoteException
     * @throws NameAlreadyTakenException
     */
    public void registerClient(Client client) throws RemoteException, NameAlreadyTakenException;

    /**
     *
     * @param client
     * @throws RemoteException
     */
    public void deRegisterClient(Client client) throws RemoteException;

    /**
     *
     * @param client
     * @param id
     * @throws RemoteException
     */
    public void joinChatRoom(Client client, int id) throws RemoteException;

    /**
     *
     * @param id
     * @throws RemoteException
     */
    public void destroyChatRoom(int id) throws RemoteException;

    /**
     *
     * @param id
     * @param client
     * @throws RemoteException
     */
    public void leaveChatRoom(int id, Client client) throws RemoteException;

    /**
     *
     * @param creator
     * @param c
     * @throws RemoteException
     */
    public void addPrivateChatRoom(Client creator, Client c) throws RemoteException;
}
