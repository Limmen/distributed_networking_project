/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.proj.client.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import limmen.id2212.proj.util.Participant;

/**
 * Remote interface for a Client
 * @author kim
 */
public interface Client extends Remote {
    
    /**
     * Update the clients "cache" of participant-data.
     * @param participants list of participants
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void updateParticipants(ArrayList<Participant> participants) throws RemoteException;
}
