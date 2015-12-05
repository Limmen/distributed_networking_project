/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.server.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import limmen.id2212.proj.util.Participant;
import limmen.id2212.proj.util.ParticipantImpl;

/**
 *
 * @author kim
 */
public class Server {
    private static final String DEFAULT_SERVER_NAME = "ID2212_NOG_INFORMATION_SYSTEM";
    public Server(){
        try {            
            NogServer nogServer = new NogServerImpl(DEFAULT_SERVER_NAME);
            register_bind(nogServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Server(String init){
        try {            
            NogServer nogServer = new NogServerImpl(DEFAULT_SERVER_NAME, readFile());
            register_bind(nogServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void register_bind(NogServer nogServer){
        try{
        // Register the newly created object at rmiregistry.
        try {
            LocateRegistry.getRegistry(1099).list();
        } catch (RemoteException e) {
            LocateRegistry.createRegistry(1099);
        }
        Naming.rebind(DEFAULT_SERVER_NAME, nogServer);
        System.out.println(nogServer + " is ready.");
        }
        catch(Exception e){
            
        }
    }
    private ArrayList<Participant> readFile(){
        ArrayList<Participant> participants = new ArrayList();
        DateFormat format = new SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH);
        try{
            BufferedReader buffReader = new BufferedReader(new FileReader("/home/kim/participants.tsv"));
            String str = buffReader.readLine();
            while (str != null) {
                String[] values = str.split("\\t", -1);
                participants.add((Participant) new ParticipantImpl(Integer.parseInt(values[0]),
                        values[1], values[2].charAt(0), values[3], format.parse(values[4]),
                        Float.parseFloat(values[5]), Float.parseFloat(values[6]),
                        values[7]));
                str = buffReader.readLine();
            }
        }
        catch(IOException | ParseException e){
            e.printStackTrace();
        }
        return participants;
    }
    
    public static void main(String[] args){
        if(args.length > 0){
            if(args[0].equals("init"))
                new Server(args[0]);
        }
        else
            new Server();
    }
}
