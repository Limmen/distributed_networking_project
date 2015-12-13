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
    private ArrayList<ChatRoom> chatRooms = new ArrayList();
    private int idCount = 0;

    /**
     *
     * @param serverName
     * @throws RemoteException
     */
    public NogChatServerImpl(String serverName) throws RemoteException{
        this.serverName = serverName;
    }

    /**
     *
     * @param c
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<ChatRoom> getChatRooms(Client c) throws RemoteException {
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
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Client> getUsers() throws RemoteException {
        return clients;
    }
    
    private synchronized int incrementId(){
        return idCount++;
    }

    /**
     *
     * @param creator
     * @throws RemoteException
     */
    @Override
    public void addChatRoom(Client creator) throws RemoteException {
        chatRooms.add(new ChatRoomImpl(creator ,incrementId(), true));
        for(Client c : clients){
            c.updateChatRooms(chatRooms);
        }
    }

    /**
     *
     * @param client
     * @throws RemoteException
     */
    @Override
    public void deRegisterClient(Client client) throws RemoteException {
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
     *
     * @param client
     * @throws RemoteException
     * @throws NameAlreadyTakenException
     */
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

    /**
     *
     * @param client
     * @param id
     * @throws RemoteException
     */
    @Override
    public void joinChatRoom(Client client, int id) throws RemoteException {
        for(ChatRoom r : chatRooms){
            if(r.getID() == id)
                r.addUser(client);
        }
    }

    /**
     *
     * @param id
     * @throws RemoteException
     */
    @Override
    public void destroyChatRoom(int id) throws RemoteException {
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
     *
     * @param id
     * @param client
     * @throws RemoteException
     */
    @Override
    public void leaveChatRoom(int id, Client client) throws RemoteException {
        for(ChatRoom r : chatRooms){
            if(r.getID() == id){
                r.removeUser(client);                
            }
        }        
        notifyClients();
    }

    /**
     *
     * @param username
     * @return
     * @throws RemoteException
     */
    @Override
    public Client getClient(String username) throws RemoteException {
        for(Client c : clients){
            if(c.getName().equals(username))
                return c;
        }
        return null;
    }

    /**
     *
     * @param creator
     * @param c
     * @throws RemoteException
     */
    @Override
    public void addPrivateChatRoom(Client creator, Client c) throws RemoteException {
        ChatRoom r = new ChatRoomImpl(creator, incrementId(),false);
        r.addUser(c);
        chatRooms.add(r);
        creator.updateChatRooms(chatRooms);
        c.updateChatRooms(chatRooms);
    }
    
}
