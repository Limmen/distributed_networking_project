/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.model;

import java.util.Date;

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
    }
    @Override
    public String toString(){
        return ID + "\t" + name + "\t" + gender + "\t" + country + "\t" + birthday
                + "\t" + height + "\t" + weight + "\t" + sport + "\n";
    }
}