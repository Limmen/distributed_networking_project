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
 * Implementation of the NogChatServer interface.
 * extends UniCastRemoteObject to automaticly export the remote object.
 * Methods are declared synchronized to get some thread-safety (only one
 * thread is allowed to execute the methods at a time, the rest of the threads
 * get queued up until it's their turn.
 * Shared resources like clients and chatrooms are declared volatile
 * to avoid inconsistent reads.
 * @author kim
 */
public class NogChatServerImpl extends UnicastRemoteObject implements NogChatServer {
    private final String serverName;
    private volatile ArrayList<Client> clients;
    private volatile ArrayList<ChatRoom> chatRooms;
    private int idCount = 0;
    
    /**
     * Class constructor.
     * @param serverName name of the server that is registered and rmiregistry
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public NogChatServerImpl(String serverName) throws RemoteException{
        this.serverName = serverName;
        this.clients = new ArrayList();
        this.chatRooms = new ArrayList();
    }
    
    /**
     * thrown when problem with remote method-call occurs.
     * @param c client that requests the chatrooms. Neccessary to filter out private chatrooms
     * @return List of chatrooms
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public synchronized ArrayList<ChatRoom> getChatRooms(Client c) throws RemoteException {
        ArrayList<ChatRoom> rooms = new ArrayList();
        for(ChatRoom r : chatRooms){
            if(r.chatRoomIsPublic())
                rooms.add(r);
            else{
                if(r.getUsers().contains(c))
                    rooms.add(r);
            }
        }
        return rooms;
    }
    
    /**
     * getUsers
     * @return list of users connected to the server
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public synchronized ArrayList<Client> getUsers() throws RemoteException {
        return clients;
    }
    
    private synchronized int incrementId(){
        return idCount++;
    }
    
    /**
     * addChatRoom
     * @param creator client that wants to create the chatroom.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public synchronized void addChatRoom(Client creator) throws RemoteException {
        chatRooms.add(new ChatRoomImpl(creator ,incrementId(), true));
        for(Client c : clients){
            c.updateChatRooms(chatRooms);
        }
    }
    
    /**
     * deRegisterClient
     * @param client client to deRegister
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public synchronized void deRegisterClient(Client client) throws RemoteException {
        clients.remove(client);
        ArrayList<ChatRoom> updRooms = new ArrayList();
        for(ChatRoom r : chatRooms){
            if (r.getUsers().contains(client)){
                r.removeUser(client);
                if(!r.getCreator().equals(client))
                    updRooms.add(r);
            }
        }
        for(Client c : clients){
            c.updateClients(clients);
        }
        this.chatRooms = updRooms;
    }
    
    /**
     * registerClient
     * @param client client to register at the server
     * @throws RemoteException thrown when problem with remote method-call occurs.
     * @throws NameAlreadyTakenException thrown when the username is already taken by another client.
     */
    @Override
    public synchronized void registerClient(Client client) throws RemoteException, NameAlreadyTakenException {
        for(Client i : clients){
            if(i.getName().equals(client.getName()))
                throw new NameAlreadyTakenException(client.getName() + " is used by another user");
        }
        clients.add(client);
        for(Client c : clients){
            c.updateClients(clients);
        }
    }
    
    /**
     * joinChatRoom
     * @param client client that wants to join the chatroom
     * @param id id of the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public synchronized void joinChatRoom(Client client, int id) throws RemoteException {
        for(ChatRoom r : chatRooms){
            if(r.getID() == id)
                r.addUser(client);
        }
    }
    
    /**
     * destroyChatRoom
     * @param id id of the chatroom to be destroyed
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public synchronized void destroyChatRoom(int id) throws RemoteException {
        ChatRoom room = null;
        for(ChatRoom r : chatRooms){
            if(r.getID() == id){
                room = r;
            }
        }
        chatRooms.remove(room);
        notifyClients();
        room.destroy();
    }
    
    private void notifyClients() throws RemoteException{
        for(Client c : clients){
            c.updateChatRooms(chatRooms);
        }
    }
    
    /**
     * leaveChatRoom
     * @param id id of the chatroom to leave
     * @param client client that wants to leave the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public synchronized void leaveChatRoom(int id, Client client) throws RemoteException {
        for(ChatRoom r : chatRooms){
            if(r.getID() == id){
                r.removeUser(client);
            }
        }
        notifyClients();
    }
    
    /**
     * getClient
     * @param username of the client to lookup
     * @return reference to the client
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public synchronized Client getClient(String username) throws RemoteException {
        for(Client c : clients){
            if(c.getName().equals(username))
                return c;
        }
        return null;
    }
    
    /**
     * addPrivateChatRoom
     * @param creator client that initiates the private chatroom.
     * @param c the other client
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public synchronized void addPrivateChatRoom(Client creator, Client c) throws RemoteException {
        ChatRoom r = new ChatRoomImpl(creator, incrementId(),false);
        r.addUser(c);
        chatRooms.add(r);
        creator.updateChatRooms(chatRooms);
        c.updateChatRooms(chatRooms);
    }
    
}
