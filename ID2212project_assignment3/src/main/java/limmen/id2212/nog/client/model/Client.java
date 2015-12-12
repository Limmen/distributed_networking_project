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
    public void notifyChatRoomDestroyed(ChatRoom room) throws RemoteException;
    public String getName() throws RemoteException;
    public void updateClients(ArrayList<Client> clients) throws RemoteException;
    public void updateChatRooms(ArrayList<ChatRoom> chatRooms) throws RemoteException;
    public boolean equals(Client c) throws RemoteException;
    public void updateChat(ChatRoom r) throws RemoteException;
    public void chatRoomDestroyed(String creator, int id) throws RemoteException;
    public void blockClient(String c) throws RemoteException;
    public ArrayList<String> getBlockedList() throws RemoteException;
    public void updateLeftChatRoom() throws RemoteException;
}
