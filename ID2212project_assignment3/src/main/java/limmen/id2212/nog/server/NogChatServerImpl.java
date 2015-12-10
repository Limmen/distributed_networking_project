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
    private int idCount = 0;
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
        for(Client c : clients){
            c.updateChatRooms(chatrooms);
        }
    }

    @Override
    public void deRegisterClient(Client client) throws RemoteException {
        clients.remove(client);
        ArrayList<ChatRoom> updRooms = new ArrayList();
        for(ChatRoom r : chatrooms){
            if (r.getUsers().contains(client)){
                r.removeUser(client);
                if(!r.getCreator().equals(client))
                    updRooms.add(r);
            }
        }
        for(Client c : clients){
            c.updateClients(clients);
        }
        this.chatrooms = updRooms;
        System.out.println("Client deRegistered, size : " + clients.size());
    }

    @Override
    public void registerClient(Client client) throws RemoteException, NameAlreadyTakenException {
        for(Client i : clients){
            if(i.getName().equals(client.getName()))
                throw new NameAlreadyTakenException(client.getName() + " is used by another user");            
        }
        clients.add(client);
        for(Client c : clients){
            c.updateClients(clients);
        }
    }
    
}
