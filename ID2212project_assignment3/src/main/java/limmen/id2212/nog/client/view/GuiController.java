/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import limmen.id2212.nog.client.model.Client;
import limmen.id2212.nog.client.model.NOGWorker;
import limmen.id2212.nog.client.model.ServerCommand;
import limmen.id2212.nog.client.model.ServerCommandName;
import limmen.id2212.nog.server.ChatRoom;
import limmen.id2212.nog.server.NogChatServer;

/**
 *
 * @author kim
 */
public class GuiController {
    private static final String DEFAULT_SERVER_NAME = "ID2212_NOG_CHAT_ROOMS";
    private final GuiController contr = this;
    private MainFrame mainFrame;
    private final StartFrame startFrame;
    private NogChatServer serverobj;
    private Client client;
    
    public GuiController(){
        connectToServer();
        startFrame = new StartFrame(contr);
    }
    /**
     * Main-method, entry-point of the program.
     * @param args
     */
    public static void main(String[] args){
        new GuiController();
    }
    void invalidInput(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "That is not valid input",
                        "Invalid input", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    void getClients(){
        new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.getClients), client).execute();        
    }
    void getChatRooms(){
        new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.getChatRooms), client).execute();
    }
    public void updateMainFrameClients(final ArrayList<Client> clients){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    if(mainFrame != null)
                        mainFrame.updateMainFrameClients(clients);
                }catch(RemoteException e){
                    remoteExceptionHandler(e);
                }
            }
        });
    }
    public void updateMainFrameChatRooms(final ArrayList<ChatRoom> chatRooms){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try{
                    mainFrame.updateMainFrameChatRooms(chatRooms);
                } catch(RemoteException e){
                    remoteExceptionHandler(e);
                }
            }
        });
    }
    public void successfulRegistration(Client client){
        this.client = client;
        mainFrame = new MainFrame(contr);
        startFrame.setVisible(false);
        getClients();
        getChatRooms();
    }
    public void failedRegistration(Client client){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "That username is already taken",
                        "Invalid username", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    //ActionListener for connect-button at startframe.
    class RegisterListener implements ActionListener {
        private final JTextField nameField;
        
        RegisterListener(JTextField nameField){
            this.nameField = nameField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(nameField.getText().length() > 0){
                new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.registerClient), nameField.getText()).execute();
            }
            else{
                invalidInput();
            }
            nameField.setText("");
        }
    }
    //ActionListener for connect-button at startframe.
    class SendListener implements ActionListener {
        private final JTextArea messageArea;
        private final ChatRoom chatRoom;
        
        SendListener(ChatRoom chatRoom, JTextArea messageArea){
            this.chatRoom = chatRoom;
            this.messageArea = messageArea;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(messageArea.getText().length() > 0){
                new NOGWorker(chatRoom, contr, new ServerCommand(ServerCommandName.sendMessage), messageArea.getText(), client).execute();
            }
            else{
                invalidInput();
            }
            messageArea.setText("");
        }
    }
    Client getClient(){
        return client;
    }
        //ActionListener for connect-button at startframe.
    class NewChatRoomListener implements ActionListener {                
        NewChatRoomListener(){                             
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.addChatRoom), client).execute();
        }
    }
    void quit(){
        try{
            serverobj.deRegisterClient(client);
            client = null;
            mainFrame.dispose();
            startFrame.setVisible(true);
        }
        catch(RemoteException e){
            remoteExceptionHandler(e);
        }
        
    }
    public void remoteExceptionHandler(RemoteException e){
        e.printStackTrace();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "There was an error"
                        + " with the connection to the server",
                        "ConnectionError", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    //Uses rmiregistry to get initial reference to remoteObject
    private void connectToServer(){
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            serverobj = (NogChatServer) Naming.lookup(DEFAULT_SERVER_NAME);
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to server: " + DEFAULT_SERVER_NAME);
    }
}
