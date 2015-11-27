/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.client.model;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.swing.SwingWorker;
import limmen.id2212.proj.client.view.GuiController;

/**
 *
 * @author kim
 */
public class RequestWorker extends SwingWorker<Boolean, Boolean> {
    private final GuiController contr;
    private PrintWriter outWriter;
    private Socket socket;
    String httpServer = "localhost";
    int serverPort = 8080;
    int timeoutMillis = 10000;
    String httpGetRequest = "GET participants.tsv HTTP/1.1";
    String hostHeader = "Host: " + httpServer;
    ArrayList<Participant> participants;
    public RequestWorker(GuiController contr){
        this.contr = contr;
    }
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
            FileWriter writer = new FileWriter("src/main/resources/result.tsv");
            System.out.println("entering loooooop");
            ArrayList<Participant> participants = new ArrayList();
            while ((str = reader.readLine()) != null) {
                DateFormat format = new SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH);
                writer.write(str);
                String[] values = str.split("\\t", -1); // don't truncate empty fields
                participants.add(new Participant(Integer.parseInt(values[0]),
                values[1], str.charAt(0), values[3], format.parse(values[4]),
                Float.parseFloat(values[5]), Float.parseFloat(values[6]),
                values[7]));
                }
            System.out.println("Added all????? size : " + participants.size());
            writer.close();
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
        updateParticipants(participants);
        return true;        
    }
    
    private void updateParticipants(ArrayList<Participant> participants){
        contr.updateParticipants(participants);
    }
}
