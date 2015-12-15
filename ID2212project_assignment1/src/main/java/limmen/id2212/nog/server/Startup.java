/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.server;

/**
 * Startup-class. Entry point for the server application.
 * @author kim
 */

public class Startup {
    /**
     * Entry point for the server.
     * Starts a Listener-thread with a given path to tsv-file.
     * @param args
     */
    public static void main(String[] args){
        if(args.length == 1)
            new Thread(new Listener("src/main/resources/participants.tsv", Integer.parseInt(args[0]))).start();
        else
            new Thread(new Listener("src/main/resources/participants.tsv")).start();
    }
}

