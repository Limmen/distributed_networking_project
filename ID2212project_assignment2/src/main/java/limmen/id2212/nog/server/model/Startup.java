/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server.model;

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
import limmen.id2212.nog.server.integration.Participant;
import limmen.id2212.nog.server.integration.ParticipantImpl;

/**
 *
 * @author kim
 */
public class Startup {
    private static final String DEFAULT_SERVER_NAME = "ID2212_NOG_INFORMATION_SYSTEM";

    /**
     * Class constructor. Initializes the remote-server-object.
     */
    public Startup(){
        try {            
            NogServer nogServer = new NogServerImpl(DEFAULT_SERVER_NAME);
            register_bind(nogServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Alternative contstructor that's used if database need to be built from
     * scratch
     * @param init 
     */
    public Startup(String init){
        try {            
            NogServer nogServer = new NogServerImpl(DEFAULT_SERVER_NAME, readFile());
            register_bind(nogServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Method to register a serverobject at rmiregistry
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
    //parses content of tsv-file into list. Only called when program is started
    //with "init" flag.
    private ArrayList<Participant> readFile(){
        ArrayList<Participant> participants = new ArrayList();
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
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
    
    /**
     * Entrypoint of the program
     * @param args
     */
    public static void main(String[] args){
        if(args.length > 0){
            if(args[0].equals("init"))
                new Startup(args[0]);
        }
        else
            new Startup();
    }
}
