/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.server.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author kim
 */
public class NogServerImpl extends UnicastRemoteObject implements NogServer {
    private final String serverName;
    private final QueryManager qm;
    private ArrayList<Participant> participants;
    public NogServerImpl(String serverName, ArrayList<Participant> participants) throws RemoteException{
        this.serverName = serverName;
        this.participants = participants;
        qm = new QueryManager();
        System.out.println(participants.size());
    }
    @Override
    public ArrayList<Participant> getParticipants() throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void putParticipants(ArrayList<Participant> participants) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
