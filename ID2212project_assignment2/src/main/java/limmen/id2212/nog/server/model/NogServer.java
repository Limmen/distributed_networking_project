/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import limmen.id2212.nog.client.model.Client;
import limmen.id2212.nog.client.model.ParticipantDTO;
import limmen.id2212.nog.server.integration.Participant;

/**
 * RemoteInterface for the NOGServer.
 * @author kim
 */
public interface NogServer extends Remote {
    
    /**
     * getParticipants
     * @return list of participants
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public ArrayList<Participant> getParticipants() throws RemoteException;
    
    /**
     * putParticipants
     * @param tableParticipants list of participantDTO
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void putParticipants(ArrayList<ParticipantDTO> tableParticipants) throws RemoteException;
    
    /**
     * register a client
     * @param client remote client
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void registerClient(Client client) throws RemoteException;
    
    /**
     * deRegister client
     * @param client remote client
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void deRegisterClient(Client client) throws RemoteException;
    
    /**
     * Edit participant
     * @param p edited participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void editParticipant(ParticipantDTO p) throws RemoteException;
    
    /**
     * Add new participant
     * @param p participant to add
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void addParticipant(ParticipantDTO p) throws RemoteException;
    
    /**
     * Delete participant
     * @param p participant to delete
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void deleteParticipant(ParticipantDTO p) throws RemoteException;
    
}
