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
    public ChatRoomImpl(Client creator, int id) throws RemoteException{
        this.creator = creator;
        this.id = id;
        users.add(creator);
        messages.add("Server: welcome to the chatroom. Creator of the chatroom is: "
        + creator.getName() + "\n");
    }

    @Override
    public int getID() throws RemoteException {
        return id;
    }

    @Override
    public ArrayList<String> getMessages() throws RemoteException {
        return messages;
    }

    @Override
    public ArrayList<Client> getUsers() throws RemoteException {
        return users;
    }

    @Override
    public void addMessage(Client user , String message) throws RemoteException {
        messages.add(user.getName() + ": " + message + "\n");
        notifyUsers();
    }

    @Override
    public Client getCreator() throws RemoteException {
        return creator;
    }

    @Override
    public void addUser(Client user) throws RemoteException {
        if(!users.contains(user))
        users.add(user);
    }

    @Override
    public void removeUser(Client user) throws RemoteException {
        System.out.println("trying to remove user/cr");
        if(users.contains(user)){
            users.remove(user);
            System.out.println("User removed!");
        }
        if(creator.equals(user)){
            System.out.println("creator is user");            
        }
    }
    @Override
    public void destroy() throws RemoteException {
        for(Client c : users){
            c.chatRoomDestroyed(creator.getName(), id);
        }
    }

    @Override
    public boolean equals(ChatRoom r) throws RemoteException {
        return r.getID() == id;
    }
    private void notifyUsers() throws RemoteException{
        for(Client c : users){
            c.updateChat(this, messages);
        }
    }
    
}
