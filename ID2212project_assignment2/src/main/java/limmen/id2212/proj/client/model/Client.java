/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.model;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author kim
 */
public interface Client extends Remote {
    public void updateParticipants() throws RemoteException;
}
