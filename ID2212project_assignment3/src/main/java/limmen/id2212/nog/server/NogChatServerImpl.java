/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import limmen.id2212.nog.client.model.Client;

/**
 *
 * @author kim
 */
public class NogChatServerImpl extends UnicastRemoteObject implements NogChatServer {
    private final String serverName;
    private ArrayList<Client> clients = new ArrayList();
    private ArrayList<ChatRoom> chatrooms = new ArrayList();
    private int idCount = -1;
    public NogChatServerImpl(String serverName) throws RemoteException{
        this.serverName = serverName;
    }
    @Override
    public ArrayList<ChatRoom> getChatRooms() throws RemoteException {
        return chatrooms;
    }

    @Override
    public ArrayList<Client> getUsers() throws RemoteException {
        return clients;
    }
    
    private synchronized int incrementId(){
        return idCount++;
    }

    @Override
    public void addChatRoom(Client creator) throws RemoteException {
        chatrooms.add(new ChatRoomImpl(creator ,incrementId()));
    }
    
}
