/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Class that does the initialization work of the server.
 * @author kim
 */
public class Startup {
    private static final String DEFAULT_SERVER_NAME = "ID2212_NOG_CHAT_ROOMS";
    
    /**
     * Class constructor. Initializes the remote-server-object.
     */
    public Startup(){
        try {
            NogChatServer nogServer = new NogChatServerImpl(DEFAULT_SERVER_NAME);
            register_bind(nogServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Method to register a serverobject at rmiregistry
    private void register_bind(NogChatServer nogServer){
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
    /**
     * Entrypoint of the program
     * @param args
     */
    public static void main(String[] args){
        new Startup();
    }
}
