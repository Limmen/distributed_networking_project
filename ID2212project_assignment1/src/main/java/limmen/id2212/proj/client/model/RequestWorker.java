/*
 * Course project - ID2212 Network Programming with Java
 * Royal Institute of Technology
 * 2015 (c) Kim Hammar 
 */
package limmen.id2212.proj.client.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.SwingWorker;
import limmen.id2212.proj.client.view.GuiController;

/**
 * Request-worker. Thread to handle GET-requests to the server and updates the UI
 * when needed. Neccessary to not freeze the UI in case of network latency.
 * @author kim
 */
public class RequestWorker extends SwingWorker<Boolean, Boolean> {
    private final GuiController contr;
    private PrintWriter outWriter;
    private Socket socket;
    private final String httpServer = "localhost";
    private final int serverPort = 8080;
    private final int timeoutMillis = 10000;
    private final String httpGetRequest = "GET participants.tsv HTTP/1.1";
    private final String hostHeader = "Host: " + httpServer;
    private ArrayList<Participant> participants;
    private final DateFormat format;

    /**
     * Class constructor.
     * @param contr GuiController
     */
    public RequestWorker(GuiController contr){
        this.contr = contr;
        format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
    }

    /**
     * This is where the work is done. Creates connection to the server,
     * sends a GET request for participant data and then collects the data
     * and updates the GUI.
     * @return
     */
    @Override
    protected Boolean doInBackground(){
        try{
            socket = new Socket(httpServer, serverPort);
            socket.setSoTimeout(timeoutMillis);
            outWriter = new PrintWriter(socket.getOutputStream());
            outWriter.println(httpGetRequest);
            outWriter.println(hostHeader);
            outWriter.println();
            outWriter.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str;
            participants = new ArrayList();
            while ((str = reader.readLine()) != null) {
                try{
                    String[] values = str.split("\\t", -1);
                    participants.add(new Participant(Integer.parseInt(values[0]),
                            values[1], values[2].charAt(0), values[3], format.parse(values[4]),
                            Float.parseFloat(values[5]), Float.parseFloat(values[6]),
                            values[7]));
                }
                catch(Exception e){
                }
            }
            socket.close();
        }
        catch(Exception e3){
            e3.printStackTrace();
        }
        updateParticipants(participants);
        return true;
    }    
    private void updateParticipants(ArrayList<Participant> participants){
        contr.updateParticipants(participants);
    }
}
