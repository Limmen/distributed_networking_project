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
        messages.add(user.getName() + " " + message + "\n");
    }
    
}
