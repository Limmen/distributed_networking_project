/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.util;

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
 *
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
    
    public ParticipantImpl () throws RemoteException
    {
        this(0,null,(char) 0,null,null,0,0,null);
    }
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
    @Override
    public String toString(){
        DateFormat format = new SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH);
        return ID + "\t" + name + "\t" + gender + "\t" + country + "\t"
                + format.format(birthday) + "\t" + height + "\t"
                + weight + "\t" + sport + "\n";
    }
    @Override
    public int getID() {
        return ID;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getCountry() {
        return country;
    }
    @Override
    public char getGender() {
        return gender;
    }
    @Override
    public Date getBirthday() {
        return birthday;
    }
    @Override
    public float getHeight() {
        return height;
    }
    @Override
    public float getWeight() {
        return weight;
    }
    @Override
    public String getSport() {
        return sport;
    }

    @Override
    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public void setSport(String sport) {
        this.sport = sport;
    }
    
    
    
}