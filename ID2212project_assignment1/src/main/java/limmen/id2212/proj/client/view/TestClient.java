/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.view;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author kim
 */
public class TestClient {
    static ArrayList<String> participants;
     public static void main(String[] args) {
        String httpServer = "localhost";
        int serverPort = 8080;
        int timeoutMillis = 10000;
        String httpGetRequest = "GET participants.tsv HTTP/1.1";
        String httpPutRequest = "PUT participants.tsv HTTP/1.1";
        String hostHeader = "Host: " + httpServer;

        try (Socket socket = new Socket(httpServer, serverPort)) {
            socket.setSoTimeout(timeoutMillis);
            PrintWriter wr = new PrintWriter(socket.getOutputStream());
            wr.println(httpGetRequest);
            wr.println(hostHeader);
            wr.println();
            wr.flush();
            BufferedReader reader
                    = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str;
            FileWriter writer = new FileWriter("src/main/resources/result.tsv");
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
                writer.write(str);
                //participants.add(str);
            }
            writer.close();
            socket.close();
            Socket socket2 = new Socket(httpServer, serverPort);
            socket2.setSoTimeout(timeoutMillis);
            PrintWriter wr2 = new PrintWriter(socket2.getOutputStream());
            wr2.println(httpPutRequest);
            wr2.println(hostHeader);
            wr2.println();
            wr2.flush();
            File tsvFile = new File("src/main/resources/result.tsv");
            byte [] dataBytes  = new byte [(int)tsvFile.length()];
            FileInputStream fileReader = new FileInputStream(tsvFile);
            BufferedInputStream fileWrapper = new BufferedInputStream(fileReader);
            fileWrapper.read(dataBytes,0,dataBytes.length);           
            socket2.getOutputStream().write(dataBytes,0,dataBytes.length);
            wr2.flush();
            socket2.getOutputStream().flush();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
