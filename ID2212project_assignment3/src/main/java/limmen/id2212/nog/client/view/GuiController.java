/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.nog.client.view;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import limmen.id2212.nog.client.model.Client;
import limmen.id2212.nog.server.NogChatServer;

/**
 *
 * @author kim
 */
public class GuiController {
  private static final String DEFAULT_SERVER_NAME = "ID2212_NOG_CHAT_ROOMS";
    private final GuiController contr = this;
    private final MainFrame mainFrame;
    private NogChatServer serverobj;
    private Client client;

    public GuiController(){
        connectToServer();
        mainFrame = new MainFrame(contr);
    }
    
    /**
     * Main-method, entry-point of the program.
     * @param args
     */
    public static void main(String[] args){
        new GuiController();
    }
    
    //Uses rmiregistry to get initial reference to remoteObject
    private void connectToServer(){
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            serverobj = (NogChatServer) Naming.lookup(DEFAULT_SERVER_NAME);
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to server: " + DEFAULT_SERVER_NAME);
    }
}
