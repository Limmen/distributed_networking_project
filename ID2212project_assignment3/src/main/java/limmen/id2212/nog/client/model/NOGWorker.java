/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.model;

import java.rmi.RemoteException;
import javax.swing.SwingWorker;
import limmen.id2212.nog.client.view.GuiController;
import limmen.id2212.nog.server.ChatRoom;
import limmen.id2212.nog.server.NameAlreadyTakenException;
import limmen.id2212.nog.server.NogChatServer;

/**
 * NOGWorker class. This class does remote method invocations on the
 * NogServer remote-interface. The method depends on the servercommand
 * that's given upon initialization.
 * @author kim
 */
public class NOGWorker extends SwingWorker<Boolean,Boolean> {
    private NogChatServer serverobj;
    private Client client;
    private final ServerCommand command;
    private final GuiController contr;
    private String username;
    private ChatRoom chatRoom;
    private String message;
    private int id;
    
    /**
     * Class constructor.
     * @param serverobj Reference to remote NogChatServer object.
     * @param contr GuiController
     * @param command command that specifies which remote-method to invoke
     * @param client client that requests the remote method invocation
     */
    public NOGWorker(NogChatServer serverobj, GuiController contr, ServerCommand command, Client client) {
        this.client = client;
        this.serverobj= serverobj;
        this.command = command;
        this.contr = contr;
    }
    
    /**
     * Class constructor.
     * @param serverobj Reference to remote NogChatServer object.
     * @param contr GuiController
     * @param command command that specifies which remote-method to invoke
     * @param username username that the user wants to register at the server
     */
    public NOGWorker(NogChatServer serverobj, GuiController contr, ServerCommand command, String username) {
        this.serverobj= serverobj;
        this.command = command;
        this.contr = contr;
        this.username = username;
    }
    
    /**
     * Class constructor.
     * @param chatRoom Reference to remote ChatRoom object.
     * @param contr GuiController
     * @param command command that specifies which remote-method to invoke
     * @param message message to send to the chatroom.
     * @param client client that requests the remote method invocation.
     */
    public NOGWorker(ChatRoom chatRoom, GuiController contr, ServerCommand command, String message, Client client) {
        this.chatRoom= chatRoom;
        this.client = client;
        this.command = command;
        this.contr = contr;
        this.message = message;
    }
    
    /**
     * Class constructor.
     * @param serverobj Reference to remote ChatRoom object.
     * @param contr GuiController
     * @param command command that specifies which remote-method to invoke
     * @param id id of a chatroom.
     * @param client client that requests the remote method invocation.
     */
    public NOGWorker(NogChatServer serverobj, GuiController contr, ServerCommand command, int id, Client client) {
        this.serverobj = serverobj;
        this.client = client;
        this.command = command;
        this.contr = contr;
        this.id = id;
    }
    
    /**
     * Class constructor.
     * @param serverobj Reference to remote ChatRoom object.
     * @param contr GuiController
     * @param command command that specifies which remote-method to invoke
     * @param username username of a user.
     * @param client client that requests the remote method invocation.
     */
    public NOGWorker(NogChatServer serverobj, GuiController contr, ServerCommand command, String username, Client client) {
        this.serverobj = serverobj;
        this.client = client;
        this.username = username;
        this.command = command;
        this.contr = contr;
        this.id = id;
    }
    
    /**
     * This is where the work is done. This method will call approperiate method
     * depending on what command that was given upon intialization.
     * @return boolean whether the invocation went ok or not.
     */
    @Override
    protected Boolean doInBackground() {
        switch(command.getCommandName()){
            case getClients:
                getClients();
                break;
            case getChatRooms:
                getChatRooms();
                break;
            case registerClient:
                registerClient();
                break;
            case addChatRoom:
                addChatRoom();
                break;
            case sendMessage:
                sendMessage();
                break;
            case joinChat:
                joinChat();
                break;
            case destroyChatRoom:
                destroyChatRoom();
                break;
            case leaveChatRoom:
                leaveChatRoom();
                break;
            case privateChatRoom:
                privateChatRoom();
                break;
        }
        return true;
    }
    private void getClients(){
        try{
            contr.updateMainFrameClients(serverobj.getUsers());
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void getChatRooms(){
        try{
            contr.updateMainFrameChatRooms(serverobj.getChatRooms(client));
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void addChatRoom(){
        try{
            serverobj.addChatRoom(client);
            contr.updateMainFrameChatRooms(serverobj.getChatRooms(client));
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void sendMessage(){
        try{
            chatRoom.addMessage(client, message);
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void registerClient(){
        try{
            client = new ClientImpl(contr, username);
            serverobj.registerClient(client);
            contr.successfulRegistration(client);
            
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
        catch(NameAlreadyTakenException e2){
            contr.failedRegistration(client);
        }
    }
    private void joinChat(){
        try{
            serverobj.joinChatRoom(client, id);
            contr.updateMainFrameChatRooms(serverobj.getChatRooms(client));
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void destroyChatRoom(){
        try{
            serverobj.destroyChatRoom(id);
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void leaveChatRoom(){
        try{
            serverobj.leaveChatRoom(id, client);
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void privateChatRoom(){
        try{
            Client c = serverobj.getClient(username);
            serverobj.addPrivateChatRoom(client, c);
            
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
}
