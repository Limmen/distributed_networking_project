/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
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
    public PutWorker(GuiController contr, ArrayList<Participant> participants){
        this.contr = contr;
        this.participants = participants;
    }
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
        new RequestWorker(contr).execute();
        return true;
    }
    private void errorHandling(){
        
    }
    
}
