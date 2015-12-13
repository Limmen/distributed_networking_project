/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server.model;

import limmen.id2212.nog.server.integration.QueryManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import limmen.id2212.nog.client.model.Client;
import limmen.id2212.nog.client.model.ParticipantDTO;
import limmen.id2212.nog.server.integration.Participant;

/**
 * Implementation of the NogServer interface.
 * Shared resources like connectedClients are declared volatile
 * to avoid inconsistent reads.
 * Extends UniCastRemoteObject to automaticly export the remote object.
 * @author kim
 */
public class NogServerImpl extends UnicastRemoteObject implements NogServer {
    private volatile ArrayList<Client> connectedClients = new ArrayList();
    private final String serverName;
    private final QueryManager qm;
    
    /**
     * Class constructor. This constructor is used when the database need to
     * be rebuilt. Stores list of participants in the database.
     * @param serverName name of the server that is registered at rmiregistry
     * @param participants list of participants to store in db
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public NogServerImpl(String serverName, ArrayList<Participant> participants) throws RemoteException{
        this.serverName = serverName;
        qm = new QueryManager();
        qm.initialStore(participants);
    }
    
    /**
     * Class constructor. This is the default constructor.
     * @param serverName name of the server that is registered at rmiregistry
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public NogServerImpl(String serverName) throws RemoteException{
        this.serverName = serverName;
        qm = new QueryManager();
        //qm.getParticipants();
    }
    
    /**
     * Method to fetch participants from database
     * @return list of participants
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public ArrayList<Participant> getParticipants() throws RemoteException {
        return qm.getParticipants();
    }
    
    /**
     * Method to store list of participants at database.
     * @param tableParticipants list of participantsDTO
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void putParticipants(ArrayList<ParticipantDTO> tableParticipants) throws RemoteException {
        qm.putParticipants(tableParticipants);
        updateClients();
    }
    
    /**
     * Method to register a client as "connected", meaning that the client
     * should be notified when participant-data changes.
     * @param client reference to remote client
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void registerClient(Client client) throws RemoteException {
        if(!connectedClients.contains(client))
            connectedClients.add(client);
    }
    
    /**
     * Method to deRegister client.
     * @param client reference to remote client.
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void deRegisterClient(Client client) throws RemoteException {
        if(connectedClients.contains(client))
            connectedClients.remove(client);
    }
    //Method to notify clients about new participants data
    private void updateClients() throws RemoteException{
        for(Client client : connectedClients)
            client.updateParticipants(qm.getParticipants());
    }
    
    /**
     * Method to edit participant
     * @param p participant to be edited
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void editParticipant(ParticipantDTO p) throws RemoteException {
        qm.editParticipant(p);
        updateClients();
    }
    
    /**
     * Method to add new participant
     * @param p participant to be added
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void addParticipant(ParticipantDTO p) throws RemoteException {
        qm.editParticipant(p);
        updateClients();
    }
    
    /**
     * Method to delete participant
     * @param p participant to be deleted
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    @Override
    public void deleteParticipant(ParticipantDTO p) throws RemoteException {
        qm.deleteParticipant(p.getID());
        updateClients();
    }
    
}
