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
    
    public ArrayList<ChatRoom> getChatRooms() throws RemoteException;
    public ArrayList<Client> getUsers() throws RemoteException;
    public void addChatRoom(Client creator) throws RemoteException;
    public void registerClient(Client client) throws RemoteException, NameAlreadyTakenException;
    public void deRegisterClient(Client client) throws RemoteException;
    public void joinChatRoom(Client client, int id) throws RemoteException;
    public void destroyChatRoom(int id) throws RemoteException;
}
