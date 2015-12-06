/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.server.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import limmen.id2212.proj.client.model.Client;
import limmen.id2212.proj.client.util.TableDTO;
import limmen.id2212.proj.util.Participant;

/**
 *
 * @author kim
 */
public interface NogServer extends Remote {
    
    public ArrayList<Participant> getParticipants() throws RemoteException;
    public void putParticipants(ArrayList<TableDTO> tableParticipants) throws RemoteException;
    public void registerClient(Client client) throws RemoteException;
    public void deRegisterClient(Client client) throws RemoteException;
    public void editParticipant(TableDTO p) throws RemoteException;
    public void addParticipant(TableDTO p) throws RemoteException;
    public void deleteParticipant(TableDTO p) throws RemoteException;
    
}
