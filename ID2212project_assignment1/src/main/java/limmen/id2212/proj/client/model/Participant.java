/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.proj.client.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class represents a participant in the NOG 2015
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

    /**
     * Class constructor
     * @param ID identifier
     * @param name name of the participant
     * @param gender gender of the participant
     * @param country country of the participant
     * @param birthday birthday-date of the participant
     * @param height height of the participant
     * @param weight weight of the participant
     * @param sport sport that the participant performed during NOG
     */
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

    /**
     * Returns a tab-separated line that is compatible to store in a tsv-file.
     * @return tab-separated line (string)
     */
    @Override
    public String toString(){
        return ID + "\t" + name + "\t" + gender + "\t" + country + "\t" 
                + format.format(birthday) + "\t" + height + "\t" 
                + weight + "\t" + sport + "\n";
    }
    
    /**
     * Get ID
     * @return id
     */
    public int getID() {
        return ID;
    }    
    /**
     * Get Participant name
     * @return name
     */
    public String getName() {
        return name;
    }    
    /**
     * Get Participant  country
     * @return country
     */
    public String getCountry() {
        return country;
    }   
    /**
     * Get Participant gender
     * @return g ender
     */
    public char getGender() {
        return gender;
    }    
    /**
     * Get Participant birthday
     * @return
     */
    public Date getBirthday() {
        return birthday;
    }    
    /**
     * Get Participant height
     * @return height
     */
    public float getHeight() {
        return height;
    }    
    /**
     * Get Participant weight
     * @return weight
     */
    public float getWeight() {
        return weight;
    }    
    /**
     * Participant sport
     * @return sport
     */
    public String getSport() {
        return sport;
    }
    /**
     * set ID
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    /**
     * set Paticipant name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * set Participant country
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
    /**
     * set Participant gender
     * @param gender
     */
    public void setGender(char gender) {
        this.gender = gender;
    }
    /**
     * set Participant birthday
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    /**
     * set Participant height
     * @param height
     */
    public void setHeight(float height) {
        this.height = height;
    }
    /**
     * set Participant weight
     * @param weight
     */
    public void setWeight(float weight) {
        this.weight = weight;
    }
    /**
     * set Participant sport
     * @param sport
     */
    public void setSport(String sport) {
        this.sport = sport;
    }        
}