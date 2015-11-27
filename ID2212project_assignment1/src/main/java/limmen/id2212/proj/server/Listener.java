/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author kim
 */
public class Listener implements Runnable {
    private int PORT;
    private File tsvFile;
    private boolean running;
    private ServerSocket serverSocket;
    
    public Listener(String path){
        tsvFile = new File(path);
        PORT = 8080;
    }
    public Listener(String path, int port){
        tsvFile = new File(path);
        PORT = port;
    }
    @Override
    public void run() {
        running = true;
        try
        {
            serverSocket = new ServerSocket(PORT);
            while (running)
            {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket, tsvFile)).start();
            }
        } catch (IOException e)
        {
            System.err.println("Could not listen on port: " + PORT);
            cleanUp();
            terminate();
        }
    }
    
    /**
     * Clean up the serversocket.
     */
    public void cleanUp(){
        try{
            serverSocket.close();
        }
        catch(Exception e){
            running = false;
        }
    }
    
    /**
     * Terminates this thread
     */
    public void terminate(){
        running = false;
    }
    
    private ArrayList<String> parseTSV(File file){
        BufferedReader tsvReader = null;
        ArrayList<String> participants = new ArrayList() ;
        try{
            // TSVFile = new BufferedReader(new FileReader("src/main/resources/participants.tsv"));
            tsvReader= new BufferedReader(new FileReader(file));
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
            System.exit(0);
        }
        try{
            String dataRow = tsvReader.readLine();
            while (dataRow != null){
                participants.add(dataRow);
                dataRow = tsvReader.readLine();
            }
            tsvReader.close();
        }
        catch(IOException e2){
            e2.printStackTrace();
            System.exit(0);
        }
        return participants;
    }
    
    
}
