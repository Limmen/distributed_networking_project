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
public interface ChatRoom extends Remote {
    
    public int getID() throws RemoteException;
    public ArrayList<String> getMessages() throws RemoteException;
    public ArrayList<Client> getUsers() throws RemoteException;
    public void addMessage(Client user, String message) throws RemoteException;   
}
