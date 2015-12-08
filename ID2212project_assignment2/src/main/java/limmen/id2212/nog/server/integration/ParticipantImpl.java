/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server.integration;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 * Entity-class and participant implementation.
 * @author kim
 */
@Entity
public class ParticipantImpl extends UnicastRemoteObject implements Serializable,Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    private String name;
    private String country;
    private char gender;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthday;
    private float height;
    private float weight;
    private String sport;
    
    /**
     *
     * @throws RemoteException
     */
    public ParticipantImpl () throws RemoteException
    {
        this(0,null,(char) 0,null,null,0,0,null);
    }
    
    /**
     * Class constructor.
     * @param ID id of the participant
     * @param name name of the participant
     * @param gender gender of the participant
     * @param country country of the participant
     * @param birthday birthday of the participant
     * @param height height of the participant
     * @param weight weight of the participant
     * @param sport sport of the participant
     * @throws RemoteException thrown when problem with remote method-call occurs.
     */
    public ParticipantImpl(int ID, String name,char gender,String country,
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
     * Returns a tab-separated line of the data.
     * @return tab-separated line
     */
    @Override
    public String toString(){
        DateFormat format = new SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH);
        return ID + "\t" + name + "\t" + gender + "\t" + country + "\t"
                + format.format(birthday) + "\t" + height + "\t"
                + weight + "\t" + sport + "\n";
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