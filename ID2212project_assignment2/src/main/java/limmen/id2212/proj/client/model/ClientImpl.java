/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import limmen.id2212.proj.client.view.GuiController;
import limmen.id2212.proj.util.Participant;

/**
 *
 * @author kim
 */
public class ClientImpl extends UnicastRemoteObject implements Client {
    private final GuiController contr;
    public ClientImpl(GuiController contr) throws RemoteException{
        this.contr = contr;
    }
    @Override
    public void updateParticipants(ArrayList<Participant> participants) throws RemoteException {
        contr.updateParticipants(participants);
        System.out.println("Updated participants!!!!");
    }
    
}
