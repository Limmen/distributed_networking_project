/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.nog.client.model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

/**
 * Implementation of the ParticipantDTO interface.
 * Extends UniCastRemoteObject to automaticly export the remote object.
 * @author kim
 */
public class ParticipantDTOImpl extends UnicastRemoteObject implements Serializable,ParticipantDTO {
    private int ID;
    private String name;
    private String country;
    private char gender;
    private Date birthday;
    private float height;
    private float weight;
    private String sport;
    
    /**
     * Class constructor
     * @param ID
     * @param name
     * @param gender
     * @param country
     * @param birthday
     * @param height
     * @param weight
     * @param sport
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public ParticipantDTOImpl(int ID, String name,char gender,String country,
            Date birthday, float height, float weight, String sport) throws RemoteException{
        this.ID=ID;
        this.name=name;
        this.country=country;
        this.gender=gender;
        this.birthday=birthday;
        this.height=height;
        this.weight=weight;
        this.sport=sport;
    }

    /**
     * getID
     * @return id of the participant
     */
    @Override
    public int getID() {
        return ID;
    }

    /**
     * getName
     * @return name of the participant
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * getCountry
     * @return country for the participant
     */
    @Override
    public String getCountry() {
        return country;
    }

    /**
     * getGender
     * @return gender of the participant
     */
    @Override
    public char getGender() {
        return gender;
    }

    /**
     * getBirthday
     * @return birthday of the participant
     */
    @Override
    public Date getBirthday() {
        return birthday;
    }

    /**
     * getHeight
     * @return height of the participant
     */
    @Override
    public float getHeight() {
        return height;
    }

    /**
     * getWeight
     * @return weight of the participant
     */
    @Override
    public float getWeight() {
        return weight;
    }

    /**
     * getSport
     * @return sport of the participant
     */
    @Override
    public String getSport() {
        return sport;
    }

    /**
     * setID
     * @param ID of the participant
     */
    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * setName
     * @param name name of the participant
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * setCountry
     * @param country country of the participant
     */
    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * setGender
     * @param gender gender of the participant
     */
    @Override
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * setBirthday
     * @param birthday birthday of the participant
     */
    @Override
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * setHeight
     * @param height height of the participant
     */
    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * setWeigth
     * @param weight weight of the participant
     */
    @Override
    public void setWeight(float weight) {
        this.weight = weight;
    }

    /**
     * setSport
     * @param sport sport of the participant
     */
    @Override
    public void setSport(String sport) {
        this.sport = sport;
    }
    
    
}
