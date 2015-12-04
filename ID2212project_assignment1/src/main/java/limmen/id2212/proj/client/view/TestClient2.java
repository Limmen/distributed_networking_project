/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.client.view;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author kim
 */
public class TestClient2 {
    static ArrayList<String> participants;
    public static void main(String[] args) {
        String httpServer = "localhost";
        int serverPort = 8080;
        int timeoutMillis = 10000;
        String httpGetRequest = "GET participants.tsv HTTP/1.1";
        String httpPutRequest = "PUT participants.tsv HTTP/1.1";
        String hostHeader = "Host: " + httpServer;
        try{
        URL url = new URL("http://localhost:8080/participants.tsv");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();        
        connection.setDoOutput(true);
        connection.setRequestMethod("GET");
       /* 
        OutputStreamWriter out = new OutputStreamWriter(
                connection.getOutputStream());
        out.write("participants.tsv");
        out.close();
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));
        String decodedString;
        while ((decodedString = in.readLine()) != null) {
            System.out.println(decodedString);
        }
        in.close(); */
        System.out.println((String) connection.getContent());
        }
        catch(Exception e2){
            e2.printStackTrace(); 
    }
}
}
