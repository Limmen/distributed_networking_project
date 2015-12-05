/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.util;

import java.io.Serializable;
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
public class ParticipantImpl implements Serializable,Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final int ID;
    private final String name;
    private final String country;
    private final char gender;
    @Temporal(javax.persistence.TemporalType.DATE)
    private final Date birthday;
    private final float height;
    private final float weight;
    private final String sport;
    
    public ParticipantImpl()
    {
        this(0,null,(char) 0,null,null,0,0,null);
    }
    public ParticipantImpl(int ID, String name,char gender,String country,
            Date birthday, float height, float weight, String sport){
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
    
    
}