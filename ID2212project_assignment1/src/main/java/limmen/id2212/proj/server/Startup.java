/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.server;

/**
 *
 * @author kim
 */

public class Startup {
        
    /**
     *
     */
    public Startup(){
        
    }    
    public static void main(String[] args){
        new Thread(new Listener("src/main/resources/participants.tsv")).start();
    }
    }
    
