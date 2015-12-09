/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.model;

import javax.swing.SwingWorker;
import limmen.id2212.nog.client.view.GuiController;
import limmen.id2212.nog.server.NogChatServer;

/**
 *
 * @author kim
 */
public class NOGWorker extends SwingWorker<Boolean,Boolean> {
    private final NogChatServer serverobj;
    private final Client client;
    private final ServerCommand command;
    private final GuiController contr;
    
    public NOGWorker(NogChatServer serverobj, GuiController contr, ServerCommand command, Client client) {
        this.client = client;
        this.serverobj= serverobj;
        this.command = command;
        this.contr = contr;
    }
    
    @Override
    protected Boolean doInBackground() {
        switch(command.getCommandName()){
            case getClients:
                getClients();
                break;
        }
        return true;
    }
    private void getClients(){
        /*
        try{
            contr.updateParticipants(serverobj.getParticipants());
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }*/
    }
  
}
