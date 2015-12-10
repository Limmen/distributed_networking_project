/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.nog.client.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import limmen.id2212.nog.server.model.NogServer;

/**
 *
 * @author kim
 */
public class LoadTest {  
    private static final String DEFAULT_SERVER_NAME = "ID2212_NOG_INFORMATION_SYSTEM";
    public static void main(String[] args){
        NogServer serverobj = connectToServer();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
        File file = new File("/home/kim/workspace/java/distributed_networking_project/ID2212project_assignment2/doc/benchmarks/data.dat");
        testGetParticipants(serverobj, file);
        testAddParticipant(serverobj, format, file);
        testEditParticipant(serverobj, format, file);
        testDeleteParticipant(serverobj, format, file);
        System.out.println("Testing done");
        
    }
    private static NogServer connectToServer(){
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            return (NogServer) Naming.lookup(DEFAULT_SERVER_NAME);
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to server: " + DEFAULT_SERVER_NAME);
        return null;
    }
    private static void testGetParticipants(NogServer serverobj, File file){
        try{
        PrintWriter writer = new PrintWriter(new FileOutputStream(file, false));        
        long elapsedTime = 0;        
        for(int i = 0; i < 100; i++){
           long start2 = System.nanoTime(); 
           serverobj.getParticipants();         
           long elapsedTime2 = System.nanoTime() - start2;
           elapsedTime = elapsedTime + elapsedTime2;           
        }
        writer.println("getParticipants" + "\t" + elapsedTime);
        writer.close();
        }
        catch(RemoteException | FileNotFoundException e){
            e.printStackTrace();
        }
    }
    private static void testAddParticipant(NogServer serverobj, SimpleDateFormat format, File file){
        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream(file, true)); 
            long elapsedTime = 0;
            for(int i = 0; i < 100; i++){
                long start2 = System.nanoTime();
                serverobj.addParticipant(new ParticipantDTOImpl(99999, "Karlsson, Jan-Erikz",'M',"Sweden",format.parse("1971/01/12"), (float) 173.0, (float) 70.0, "Alpine Skiing"));
                long elapsedTime2 = System.nanoTime() - start2;
                elapsedTime = elapsedTime + elapsedTime2;
                serverobj.deleteParticipant(new ParticipantDTOImpl(99999, "Karlsson, Jan-Erikz",'M',"Sweden",format.parse("1971/01/12"), (float) 173.0, (float) 70.0, "Alpine Skiing"));
            }
            writer.println("addParticipant" + "\t" + elapsedTime);
            writer.close();
        }
        catch(RemoteException | FileNotFoundException | ParseException e){
            e.printStackTrace();
        }
    }
        private static void testDeleteParticipant(NogServer serverobj, SimpleDateFormat format, File file){
        try{
            PrintWriter writer = new PrintWriter(new FileOutputStream(file, true)); 
            serverobj.addParticipant(new ParticipantDTOImpl(99999, "Karlsson, Jan-Erikz",'M',"Sweden",format.parse("1971/01/12"), (float) 173.0, (float) 70.0, "Alpine Skiing"));
            long elapsedTime = 0;
            for(int i = 0; i < 100; i++){
                long start2 = System.nanoTime();                
                serverobj.deleteParticipant(new ParticipantDTOImpl(99999, "Karlsson, Jan-Erikz",'M',"Sweden",format.parse("1971/01/12"), (float) 173.0, (float) 70.0, "Alpine Skiing"));
                long elapsedTime2 = System.nanoTime() - start2;
                elapsedTime = elapsedTime + elapsedTime2;
                serverobj.addParticipant(new ParticipantDTOImpl(99999, "Karlsson, Jan-Erikz",'M',"Sweden",format.parse("1971/01/12"), (float) 173.0, (float) 70.0, "Alpine Skiing"));                
            }
            writer.println("deleteParticipant" + "\t" + elapsedTime);
            writer.close();
        }
        catch(RemoteException | FileNotFoundException | ParseException e){
            e.printStackTrace();
        }
    }
        private static void testEditParticipant(NogServer serverobj, SimpleDateFormat format, File file){
            try{
                PrintWriter writer = new PrintWriter(new FileOutputStream(file, true));               
                long elapsedTime = 0;
                for(int i = 0; i < 100; i++){
                    long start2 = System.nanoTime();
                    serverobj.editParticipant(new ParticipantDTOImpl(99999, "Karlsson, Jan-Erikz",'M',"Sweden",format.parse("1971/01/12"), (float) 173.0, (float) 70.0, "Alpine Skiing"));
                    long elapsedTime2 = System.nanoTime() - start2;
                    elapsedTime = elapsedTime + elapsedTime2;                    
                }
                writer.println("editParticipant" + "\t" + elapsedTime);
                writer.close();
            }
            catch(RemoteException | FileNotFoundException | ParseException e){
                e.printStackTrace();
            }
        }
}
