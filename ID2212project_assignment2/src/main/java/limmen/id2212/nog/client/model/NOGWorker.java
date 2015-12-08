/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.model;

import java.rmi.RemoteException;
import javax.swing.SwingWorker;
import limmen.id2212.nog.client.view.GuiController;
import limmen.id2212.nog.server.model.NogServer;

/**
 * NOGWorker class. This class does remote method invocations on the
 * NogServer remote-interface. The method depends on the servercommand
 * that's given upon initialization.
 * @author kim
 */
public class NOGWorker extends SwingWorker<Boolean,Boolean> {
    private final NogServer serverobj;
    private final Client client;
    private final ServerCommand command;
    private final GuiController contr;
    
    /**
     * Class constructor
     * @param serverobj Reference to remote NogServer object
     * @param contr GuiController
     * @param command command that specifies which remote-method to invoke
     * @param client Client that requests the remote-method invocation
     */
    public NOGWorker(NogServer serverobj, GuiController contr, ServerCommand command, Client client) {
        this.client = client;
        this.serverobj= serverobj;
        this.command = command;
        this.contr = contr;
    }
    
    /**
     * This is where the work is done. This method will call approperiate method
     * depending on what command that was given upon intialization.
     * @return boolean whether the invocation went ok or not.
     */
    @Override
    protected Boolean doInBackground() {
        switch(command.getCommandName()){
            case getParticipants:
                getParticipants();
                break;
            case putParticipants:
                putParticipants();
                break;
            case addParticipant:
                addParticipant();
                break;
            case editParticipant:
                editParticipant();
                break;
            case deleteParticipant:
                deleteParticipant();
                break;
        }
        return true;
    }
    private void getParticipants(){
        try{
            contr.updateParticipants(serverobj.getParticipants());
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void putParticipants(){
        try{
            serverobj.putParticipants(command.getParticipants());
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void addParticipant(){
        try{
            serverobj.addParticipant(command.getParticipant());
            contr.getParticipants();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void editParticipant(){
        try{
            serverobj.editParticipant(command.getParticipant());
            contr.getParticipants();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    private void deleteParticipant(){
        try{
            serverobj.deleteParticipant(command.getParticipant());
            contr.getParticipants();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
}
