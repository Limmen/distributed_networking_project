/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.proj.client.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import limmen.id2212.proj.client.view.GuiController;
import limmen.id2212.proj.util.Participant;

/**
 * Implementation of the client-interface.
 * Extends UniCastRemoteObject to automaticly export the remote object.
 * @author kim
 */
public class ClientImpl extends UnicastRemoteObject implements Client {
    private final GuiController contr;
    
    /**
     * Class constructor
     * @param contr GuiController
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public ClientImpl(GuiController contr) throws RemoteException{
        this.contr = contr;
    }
    
    /**
     * Overriden from the client-interface. This is a method that can be
     * invoked from remote. The method updates the cache of participant-data
     * at the client.
     * @param participants list of participants
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void updateParticipants(ArrayList<Participant> participants) throws RemoteException {
        contr.updateParticipants(participants);
    }
    
}
