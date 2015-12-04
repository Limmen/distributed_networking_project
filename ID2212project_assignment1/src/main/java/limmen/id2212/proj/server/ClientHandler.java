/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.server;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

/**
 *
 * @author kim
 */
public class ClientHandler implements Runnable {
    private FileInputStream fileReader = null;
    private BufferedInputStream fileWrapper = null;
    private BufferedReader inReader;
    private PrintWriter outWriter;
    private OutputStream out = null;
    private final Socket clientSocket;
    private File tsvFile;
    private final String SERVER = "Server: ID2212_proj1_subassignment_1";
    private final String OK = "HTTP/1.0 200 OK";
    private final String NOT_FOUND = "HTTP/1.0 404 File Not Found";
    private final String NOT_FOUND_HTML = "<HTML><HEAD><TITLE>File Not Found</TITLE></HEAD><BODY><H1>HTTP Error 404: File Not Found</H1></BODY></HTML>";
    private final String NOT_IMPL = "HTTP/1.0 501 Not Implemented";
    private final String NOT_IMPL_HTML = "<HTML><HEAD><TITLE>Not Implemented</TITLE></HEAD><BODY><H1>HTTP Error 501: Not Implemented</H1></BODY></HTML>";
    private final String FORBIDDEN = "HTTP/1.0 403 Forbidden";
    private final String FORBIDDEN_HTML = "<HTML><HEAD><TITLE>FORBIDDEN</TITLE></HEAD><BODY><H1>HTTP Error 403: Forbidden</H1></BODY></HTML>";
    
    public ClientHandler(Socket clientSocket, File tsvFile){
        this.clientSocket = clientSocket;
        this.tsvFile = tsvFile;
    }
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
            System.out.println("method: " + method);
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
        return;
    }
    private void getReq(String name){
        System.out.println(name);
        if(name.equals("participants.tsv") || name.equals("/participants.tsv"))
            sendFile();
        else
            sendErrorMessage(NOT_FOUND,NOT_FOUND_HTML);
    }
    private void putReq(String name){
        System.out.println(name);
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
        try{
            byte [] dataBytes  = new byte [(int)tsvFile.length()];
            fileReader = new FileInputStream(tsvFile);
            fileWrapper = new BufferedInputStream(fileReader);
            fileWrapper.read(dataBytes,0,dataBytes.length);
            out.write(dataBytes,0,dataBytes.length);
            out.flush();
            System.out.println("Done.");
        }
        catch(FileNotFoundException e){
            
        }
        catch(IOException e2){
            
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
    public void cleanUp(){
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
            return;
        }
    }
    
}
