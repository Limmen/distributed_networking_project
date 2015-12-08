/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.client.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author kim
 */
public class Participant {
    private int ID;
    private String name;
    private String country;
    private char gender;
    private Date birthday;
    private float height;
    private float weight;
    private String sport;
    private final DateFormat format;
    public Participant(int ID, String name,char gender,String country,
            Date birthday, float height, float weight, String sport){
        this.ID=ID;
        this.name=name;
        this.country=country;
        this.gender=gender;
        this.birthday=birthday;
        this.height=height;
        this.weight=weight;
        this.sport=sport;
        format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
    
    
}