/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import limmen.id2212.nog.server.ChatRoom;

/**
 *
 * @author kim
 */
public interface Client extends Remote {
    public void notifyNewMessage(ArrayList<String> messages) throws RemoteException;
    public void notifyChatRoomDestroyed(ChatRoom room) throws RemoteException;
    public String getName() throws RemoteException;
}
