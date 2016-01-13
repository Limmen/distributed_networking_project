/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Listener thread that will listen on a specific port for
 * incomming TCP-connections. Spawns client-handlers with TSV-file to handle
 * client connections.
 * @author kim
 */
public class Listener implements Runnable {
    private final int PORT;
    private final File tsvFile;
    private boolean running;
    private ServerSocket serverSocket;
    
    /**
     * Alternative constructor if port is not specified, default port is 8080.
     * @param path
     */
    public Listener(String path){
        tsvFile = new File(path);
        PORT = 8080;
    }
    
    /**
     * Class constructor.
     * @param path path to tsv-file
     * @param port port to listen for tcp-connections
     */
    public Listener(String path, int port){
        tsvFile = new File(path);
        PORT = port;
    }
    
    /**
     * listen-loop
     */
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
    private void cleanUp(){
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
    private void terminate(){
        running = false;
    }

    /**
     * get port number
     * @return port
     */
    public int getPort(){
        return serverSocket.getLocalPort();
    }

    /**
     * get hostname
     * @return hostname
     */
    public String getHost(){
        return serverSocket.getInetAddress().toString();
    }
}
