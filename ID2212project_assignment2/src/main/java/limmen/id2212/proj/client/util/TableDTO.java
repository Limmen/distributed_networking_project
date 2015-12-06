/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.util;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 *
 * @author kim
 */
public interface TableDTO extends Remote {
    public int getID() throws RemoteException;    
    public String getName() throws RemoteException;    
    public String getCountry() throws RemoteException;    
    public char getGender() throws RemoteException;    
    public Date getBirthday() throws RemoteException;    
    public float getHeight() throws RemoteException;    
    public float getWeight() throws RemoteException;    
    public String getSport() throws RemoteException;
    public void setID(int id) throws RemoteException;
    public void setName(String name) throws RemoteException;
    public void setCountry(String country) throws RemoteException;
    public void setGender(char gender) throws RemoteException;
    public void setBirthday(Date birthday) throws RemoteException;
    public void setHeight(float height) throws RemoteException;
    public void setWeight(float weight) throws RemoteException;
    public void setSport(String sport) throws RemoteException;
}
