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
public class ChatRoomImpl extends UnicastRemoteObject implements ChatRoom {
    private final int id;
    private final ArrayList<Client> users = new ArrayList();
    private final ArrayList<String> messages = new ArrayList();
    private final Client creator;
    private final boolean chatRoomIsPublic;

    /**
     *
     * @param creator
     * @param id
     * @param chatRoomIsPublic
     * @throws RemoteException
     */
    public ChatRoomImpl(Client creator, int id, boolean chatRoomIsPublic) throws RemoteException{
        this.creator = creator;
        this.id = id;
        this.chatRoomIsPublic = chatRoomIsPublic;
        users.add(creator);
        messages.add("Server: welcome to the chatroom. Creator of the chatroom is: "
                + creator.getName() + "\n");
    }
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public int getID() throws RemoteException {
        return id;
    }
    
    /**
     *
     * @param client
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<String> getMessages(Client client) throws RemoteException {
        if(client.getBlockedList().size() > 0)
            return filterMessages(client);
        else
            return messages;
    }
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public ArrayList<Client> getUsers() throws RemoteException {
        return users;
    }
    
    /**
     *
     * @param user
     * @param message
     * @throws RemoteException
     */
    @Override
    public void addMessage(Client user , String message) throws RemoteException {
        messages.add(user.getName() + ": " + message + "\n");
        notifyUsers();
    }
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public Client getCreator() throws RemoteException {
        return creator;
    }
    
    /**
     *
     * @param user
     * @throws RemoteException
     */
    @Override
    public void addUser(Client user) throws RemoteException {
        if(!users.contains(user))
            users.add(user);
        messages.add("Server: " + user.getName() + " just joined the chatroom \n");
        notifyUsers();
    }
    
    /**
     *
     * @param user
     * @throws RemoteException
     */
    @Override
    public void removeUser(Client user) throws RemoteException {
        if(users.contains(user)){
            users.remove(user);
        }
        messages.add("Server: " + "user " + user.getName() + " left the chatroom \n");
        notifyUsers();
        user.updateLeftChatRoom();
    }

    /**
     *
     * @throws RemoteException
     */
    @Override
    public void destroy() throws RemoteException {
        for(Client c : users){
            c.chatRoomDestroyed(creator.getName(), id);
        }
    }
    
    /**
     *
     * @param r
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean equals(ChatRoom r) throws RemoteException {
        return r.getID() == id;
    }
    private void notifyUsers() throws RemoteException{
        for(Client c : users){
            c.updateChat(this);
        }
    }
    
    /**
     *
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean chatRoomIsPublic() throws RemoteException {
        return chatRoomIsPublic;
    }
    ArrayList<String> filterMessages(Client client) throws RemoteException{
        ArrayList<String> filtered = new ArrayList();
        for(String s : messages){
            boolean bool = false;
            for(String username : client.getBlockedList()){
                if(s.startsWith(username))
                    bool = true;
            }
            if(!bool)
                filtered.add(s);
        }
        return filtered;
    }
    
}
