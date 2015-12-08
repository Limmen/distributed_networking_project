/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.proj.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Clienthandler class. Handles a client-connection. Will read 1
 * request from client and respond.
 * @author kim
 */
public class ClientHandler implements Runnable {
    private final FileInputStream fileReader = null;
    private final BufferedInputStream fileWrapper = null;
    private BufferedReader inReader;
    private PrintWriter outWriter;
    private OutputStream out = null;
    private final Socket clientSocket;
    private final File tsvFile;
    private final String SERVER = "Server: ID2212_proj1_subassignment_1";
    private final String OK = "HTTP/1.0 200 OK";
    private final String NOT_FOUND = "HTTP/1.0 404 File Not Found";
    private final String NOT_FOUND_HTML = "<HTML><HEAD><TITLE>File Not Found</TITLE></HEAD><BODY><H1>HTTP Error 404: File Not Found</H1></BODY></HTML>";
    private final String NOT_IMPL = "HTTP/1.0 501 Not Implemented";
    private final String NOT_IMPL_HTML = "<HTML><HEAD><TITLE>Not Implemented</TITLE></HEAD><BODY><H1>HTTP Error 501: Not Implemented</H1></BODY></HTML>";
    private final String FORBIDDEN = "HTTP/1.0 403 Forbidden";
    private final String FORBIDDEN_HTML = "<HTML><HEAD><TITLE>FORBIDDEN</TITLE></HEAD><BODY><H1>HTTP Error 403: Forbidden</H1></BODY></HTML>";
    
    /**
     * Class constructor.
     * @param clientSocket end-point for connection with client.
     * @param tsvFile tsvfile containing participants data.
     */
    public ClientHandler(Socket clientSocket, File tsvFile){
        this.clientSocket = clientSocket;
        this.tsvFile = tsvFile;
    }
    
    /**
     * Read http-request and respond, then terminate.
     */
    @Override
    public void run() {
        setup();
        String req;
        try{
            req = inReader.readLine();
            if(req == null){
                cleanUp();
                return;
            }
            StringTokenizer st = new StringTokenizer(req);
            String method = st.nextToken();
            String name = st.nextToken();
            String version = st.nextToken();
            String host = inReader.readLine();
            System.out.println(method + " request");
            if(method.equals("GET"))
                getReq(name);
            if(method.equals("PUT")){
                putReq(name);
            }
            if(!method.equals("GET") && !method.equals("PUT"))
                sendErrorMessage(NOT_IMPL,NOT_IMPL_HTML);
        }
        catch(IOException e){
            cleanUp();
            return;
        }
        cleanUp();
    }
    private void getReq(String name){
        if(name.equals("participants.tsv") || name.equals("/participants.tsv"))
            sendFile();
        else
            sendErrorMessage(NOT_FOUND,NOT_FOUND_HTML);
    }
    private void putReq(String name){
        if(name.equals("participants.tsv"))
            saveToFile();
        else
            sendErrorMessage(FORBIDDEN, FORBIDDEN_HTML);
    }
    private void setup(){
        try{
            inReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = clientSocket.getOutputStream();
            outWriter = new PrintWriter(out);
        }
        catch(IOException e){
            
        }
    }
    private void sendFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(tsvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                outWriter.println(line);
            }
            outWriter.flush();
        }
        catch(IOException e2){
            e2.printStackTrace();
        }
    }
    private void sendErrorMessage(String code,String html){
        outWriter.println(code);
        outWriter.println("Date:" + (new Date()));
        outWriter.println(SERVER);
        outWriter.println("Content-type: text/html");
        outWriter.println();
        outWriter.println(html);
        outWriter.flush();
    }
    private void saveToFile(){
        try{
            String line;
            FileWriter fileWriter = new FileWriter("src/main/resources/participants.tsv");
            while ((line = inReader.readLine()) != null) {
                fileWriter.write(line);
                fileWriter.write("\n");
            }
            fileWriter.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Closes the client-connection.
     */
    private void cleanUp(){
        try {
            if(out != null)
                out.close();
            if(inReader != null)
                inReader.close();
            if(outWriter != null)
                outWriter.close();
            if(fileReader != null)
                fileReader.close();
            if(fileWrapper != null)
                fileWrapper.close();
            clientSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
