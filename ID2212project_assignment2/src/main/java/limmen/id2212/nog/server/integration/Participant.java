/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server.integration;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;

/**
 * Remote Interface for participant.
 * @author kim
 */
public interface Participant extends Remote {
    
    /**
     * getID
     * @return id of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public int getID() throws RemoteException;
    
    /**
     * getName
     * @return name of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public String getName() throws RemoteException;
    
    /**
     * getCountry
     * @return country of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public String getCountry() throws RemoteException;
    
    /**
     * getGender
     * @return gender of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public char getGender() throws RemoteException;
    
    /**
     * getBirthday
     * @return return birthday date of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public Date getBirthday() throws RemoteException;
    
    /**
     * getHeight
     * @return height of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public float getHeight() throws RemoteException;
    
    /**
     * getWeight
     * @return weight of the participant
     * @throws RemoteException
     */
    public float getWeight() throws RemoteException;
    
    /**
     * getSport
     * @return sport of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public String getSport() throws RemoteException;
    
    /**
     * setId
     * @param id id of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void setID(int id) throws RemoteException;
    
    /**
     * setName
     * @param name name of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void setName(String name) throws RemoteException;
    
    /**
     * setCountry
     * @param country country of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void setCountry(String country) throws RemoteException;
    
    /**
     * setGender
     * @param gender gender of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void setGender(char gender) throws RemoteException;
    
    /**
     * setBirthday
     * @param birthday birthday of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void setBirthday(Date birthday) throws RemoteException;
    
    /**
     * setHeight
     * @param height height of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void setHeight(float height) throws RemoteException;
    
    /**
     * setWeight
     * @param weight weight of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void setWeight(float weight) throws RemoteException;
    
    /**
     * setSport
     * @param sport sport of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public void setSport(String sport) throws RemoteException;
}
