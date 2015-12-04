/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author kim
 */
public class ParticipantImpl {
    private final int ID;
    private final String name;
    private final String country;
    private final char gender;
    private final Date birthday;
    private final float height;
    private final float weight;
    private final String sport;
    private final DateFormat format;
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
        format = new SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH);
    }
    @Override
    public String toString(){
        return ID + "\t" + name + "\t" + gender + "\t" + country + "\t" 
                + format.format(birthday) + "\t" + height + "\t" 
                + weight + "\t" + sport + "\n";
    }
    
    public int getID() {
        return ID;
    }
    
    public String getName() {
        return name;
    }
    
    public String getCountry() {
        return country;
    }
    
    public char getGender() {
        return gender;
    }
    
    public Date getBirthday() {
        return birthday;
    }
    
    public float getHeight() {
        return height;
    }
    
    public float getWeight() {
        return weight;
    }
    
    public String getSport() {
        return sport;
    }
    
    
}