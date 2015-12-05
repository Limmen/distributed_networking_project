/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.server.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import limmen.id2212.proj.util.Participant;

/**
 *
 * @author kim
 */
public class NogServerImpl extends UnicastRemoteObject implements NogServer {
    private final String serverName;
    private final QueryManager qm;
    public NogServerImpl(String serverName, ArrayList<Participant> participants) throws RemoteException{
        this.serverName = serverName;
        qm = new QueryManager();
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
    public void putParticipants(ArrayList<Participant> participants) throws RemoteException {
        qm.putParticipants(participants);
    }    
    
}
