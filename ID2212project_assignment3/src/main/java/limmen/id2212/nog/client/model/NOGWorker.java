/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.model;

import java.rmi.RemoteException;
import javax.swing.SwingWorker;
import limmen.id2212.nog.client.view.GuiController;
import limmen.id2212.nog.server.NameAlreadyTakenException;
import limmen.id2212.nog.server.NogChatServer;

/**
 *
 * @author kim
 */
public class NOGWorker extends SwingWorker<Boolean,Boolean> {
    private final NogChatServer serverobj;
    private Client client;
    private final ServerCommand command;
    private final GuiController contr;
    private String username;
    
    public NOGWorker(NogChatServer serverobj, GuiController contr, ServerCommand command, Client client) {
        this.client = client;
        this.serverobj= serverobj;
        this.command = command;
        this.contr = contr;
    }
    public NOGWorker(NogChatServer serverobj, GuiController contr, ServerCommand command, String username) {
        this.serverobj= serverobj;
        this.command = command;
        this.contr = contr;
        this.username = username;
    }
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
            contr.updateMainFrameChatRooms(serverobj.getChatRooms());
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
    
}
