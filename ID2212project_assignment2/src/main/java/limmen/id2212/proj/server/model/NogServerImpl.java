/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.server.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import limmen.id2212.proj.client.model.Client;
import limmen.id2212.proj.client.util.TableDTO;
import limmen.id2212.proj.util.Participant;

/**
 *
 * @author kim
 */
public class NogServerImpl extends UnicastRemoteObject implements NogServer {
    private volatile ArrayList<Client> connectedClients = new ArrayList();
    private final String serverName;
    private final QueryManager qm;
    public NogServerImpl(String serverName, ArrayList<Participant> participants) throws RemoteException{
        this.serverName = serverName;
        qm = new QueryManager();
        System.out.println("init!");
        qm.initialStore(participants);        
    }
    public NogServerImpl(String serverName) throws RemoteException{
        this.serverName = serverName;
        qm = new QueryManager();
        qm.getParticipants();
    }
    @Override
    public ArrayList<Participant> getParticipants() throws RemoteException {
        return qm.getParticipants();
    }
    @Override
    public void putParticipants(ArrayList<TableDTO> tableParticipants) throws RemoteException {
        qm.putParticipants(tableParticipants);
        updateClients();
    }    

    @Override
    public void registerClient(Client client) throws RemoteException {
        if(!connectedClients.contains(client))
            connectedClients.add(client);
    }

    @Override
    public void deRegisterClient(Client client) throws RemoteException {
        if(connectedClients.contains(client))
            connectedClients.remove(client);
    }
    private void updateClients() throws RemoteException{
        for(Client client : connectedClients)
            client.updateParticipants(qm.getParticipants());
    }

    @Override
    public void editParticipant(TableDTO p) throws RemoteException {
        qm.editParticipant(p);
        updateClients();
    }

    @Override
    public void addParticipant(TableDTO p) throws RemoteException {
        qm.editParticipant(p);
        updateClients();
    }

    @Override
    public void deleteParticipant(TableDTO p) throws RemoteException {
        qm.deleteParticipant(p.getID());
        updateClients();
    }
    
}
