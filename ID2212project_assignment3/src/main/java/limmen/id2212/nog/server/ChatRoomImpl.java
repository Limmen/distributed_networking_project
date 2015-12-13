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
 * Implementation of the ChatRoom interface.
 * extends UniCastRemoteObject to automaticly export the remote object.
 * Methods are declared synchronized to get some thread-safety (only one
 * thread is allowed to execute the methods at a time, the rest of the threads
 * get queued up until it's their turn.
 * Shared resources like clients and message are declared volatile
 * to avoid inconsistent reads.
 * @author kim
 */
public class ChatRoomImpl extends UnicastRemoteObject implements ChatRoom {
    private final int id;
    private volatile ArrayList<Client> users;
    private volatile String message;
    private final Client creator;
    private final boolean chatRoomIsPublic;
    
    /**
     * Class constructor
     * @param creator creator of the chatroom
     * @param id id of the chatroom (unique for this server)
     * @param chatRoomIsPublic boolean that decided wether the chatroom is public or not
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public ChatRoomImpl(Client creator, int id, boolean chatRoomIsPublic) throws RemoteException{
        this.creator = creator;
        this.id = id;
        this.chatRoomIsPublic = chatRoomIsPublic;
        this.users = new ArrayList();
        users.add(creator);
        message = "Server: welcome to the chatroom. Creator of the chatroom is: "
                + creator.getName() + "\n";
    }
    
    /**
     * getID
     * @return id of the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public int getID() throws RemoteException {
        return id;
    }
    
    /**
     * getMessage
     * @param client that requests the message. Needed to filter out messages if sender is blocked
     * @return the latest posted message at the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    synchronized public String getMessage(Client client) throws RemoteException {
        boolean bool = false;
        if(client.getBlockedList().size() > 0){
            
            for(String username : client.getBlockedList()){
                if(message.startsWith(username))
                    bool = true;
            }
        }
        if(!bool)
            return message;
        else
            return "";
    }
    
    /**
     * getUsers
     * @return list of users in this chatroom.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    synchronized public ArrayList<Client> getUsers() throws RemoteException {
        return users;
    }
    
    /**
     * addMessage
     * @param user client that sends the message
     * @param message message to be broadcasted in the chat
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    synchronized public void addMessage(Client user , String message) throws RemoteException {
        this.message = user.getName() + ": " + message + "\n";
        notifyUsers();
    }
    
    /**
     * getCreator
     * @return creator of the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public Client getCreator() throws RemoteException {
        return creator;
    }
    
    /**
     * addUser
     * @param user to add to the chatroom
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    synchronized public void addUser(Client user) throws RemoteException {
        if(!users.contains(user))
            users.add(user);
        message = "Server: " + user.getName() + " just joined the chatroom \n";
        notifyUsers();
    }
    
    /**
     * removeUser
     * @param user user to be removed
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    synchronized public void removeUser(Client user) throws RemoteException {
        if(users.contains(user)){
            users.remove(user);
        }
        message = "Server: " + "user " + user.getName() + " left the chatroom \n";
        notifyUsers();
        user.updateLeftChatRoom();
    }
    
    /**
     * Method to be called before this instance will be destroyed and
     * garbage-collected.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    synchronized public void destroy() throws RemoteException {
        for(Client c : users){
            c.chatRoomDestroyed(creator.getName(), id);
        }
    }
    
    /**
     * equals
     * @param r chatroom to be compared
     * @return boolean
     * @throws RemoteException thrown when problem with remote method-call occurs.
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
     * chatRoomIsPublic
     * @return boolean
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public boolean chatRoomIsPublic() throws RemoteException {
        return chatRoomIsPublic;
    }
    
}
