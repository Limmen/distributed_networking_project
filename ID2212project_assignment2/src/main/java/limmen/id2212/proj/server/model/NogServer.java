/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.server.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author kim
 */
public interface NogServer extends Remote {
    
    public ArrayList<Participant> getParticipants() throws RemoteException;
    public void putParticipants(ArrayList<Participant> participants) throws RemoteException;
    
}
