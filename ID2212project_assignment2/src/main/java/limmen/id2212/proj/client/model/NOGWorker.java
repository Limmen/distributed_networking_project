/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.client.model;

import java.rmi.RemoteException;
import javax.swing.SwingWorker;
import limmen.id2212.proj.client.util.ServerCommand;
import limmen.id2212.proj.client.view.GuiController;
import limmen.id2212.proj.server.model.NogServer;

/**
 * Marketworker class. This class does remote method invocation on the
 * marketplace remote-interface.
 * @author kim
 */
public class NOGWorker extends SwingWorker<Boolean,Boolean> {
    private final NogServer serverobj;
    private final Client client;
    private final ServerCommand command;
    private final GuiController contr;
    public NOGWorker(NogServer serverobj, GuiController contr, ServerCommand command, Client client) {
        this.client = client;
        this.serverobj= serverobj;
        this.command = command;
        this.contr = contr;
    }
    @Override
    protected Boolean doInBackground() throws Exception {
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
