/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.proj.client.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import limmen.id2212.proj.client.view.GuiController;

/**
 * Put-worker. Thread to handle PUT-requests to the server. 
 * Neccessary to not freeze the UI in case of network latency.
 * @author kim
 */
public class PutWorker extends SwingWorker<Boolean, Boolean> {
    private final GuiController contr;
    private PrintWriter outWriter;
    private Socket socket;
    private final String httpServer = "localhost";
    private final int serverPort = 8080;
    private final int timeoutMillis = 10000;
    private final String httpPutRequest = "PUT participants.tsv HTTP/1.1";
    private final String hostHeader = "Host: " + httpServer;
    private final ArrayList<Participant> participants;

    /**
     * Class constructor
     * @param contr GuiController
     * @param participants list of participants
     */
    public PutWorker(GuiController contr, ArrayList<Participant> participants){
        this.contr = contr;
        this.participants = participants;
    }

    /**
     * This is where the work is done. Create connection to the server, send
     * put-request and then send the participant data. When we're done we 
     * spawn a request-worker to fetch the new data from the server and update
     * the GUI.
     * @return
     */
    @Override
    protected Boolean doInBackground(){
        try{
            socket = new Socket(httpServer, serverPort);
            socket.setSoTimeout(timeoutMillis);
            outWriter = new PrintWriter(socket.getOutputStream());
            outWriter.println(httpPutRequest);
            outWriter.println(hostHeader);
            //outWriter.println();
            for(Participant p : participants){
                outWriter.write(p.toString());
            }
            outWriter.flush();
            outWriter.close();
            socket.close();
        }
        catch(UnknownHostException e){
            e.printStackTrace();
            return false;
        }
        catch(IOException e2){
            e2.printStackTrace();
            return false;
        }
        catch(Exception e3){
            e3.printStackTrace();
        }
        try {
            Thread.sleep(1000);                 
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        new RequestWorker(contr).execute();
        return true;
    }
}

