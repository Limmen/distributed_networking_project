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
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
 * A controller. All calls to the model from the view go through here.
 * All calls from the model to update the view also goes through here.
 * @author kim
 */
public class GuiController {
    private static final String DEFAULT_SERVER_NAME = "ID2212_NOG_CHAT_ROOMS";
    private final GuiController contr = this;
    private MainFrame mainFrame;
    private final StartFrame startFrame;
    private NogChatServer serverobj;
    private Client client;
    
    /**
     * Class constructor.
     */
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
    
    /**
     * Method called when a chatroom that the client is present in gets destroyed.
     * This is a method that will update the GUI to reflect the changes.
     * invokeLater to explicit add the event to the EDT since this method
     * will usually get called outside of the EDT.
     * @param creator username of the user that destroyed the room
     * @param id id of the chatroom
     */
    public void chatRoomDestroyed(final String creator,final int id){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "ChatRoom with id: " + id +
                        " was destroyed by: " + creator,
                        "ChatRoomDestroyed", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    
    /**
     * Method called when the client list of blocked users have gotten updated.
     * This method will update the GUI to reflect the changes.
     * invokeLater to explicit add the event to the EDT since this method
     * will usually get called outside of the EDT.
     * @param blocked the updated list of blocked users.
     */
    public void updateBlocked(final ArrayList<String> blocked){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame.updateBlocked(blocked);
            }
        });
    }
    /**
     * Method called when a swing-worker have fetched the updated list of
     * clients from the server. This method will update the GUI to reflect
     * the changes. invokeLater to explicit add the event to the EDT since this
     * method will get called from outside of the EDT.
     * @param clients list of clients
     */
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
    /**
     * Method called when a swing-worker have fetched the updated list of
     * chatrooms from the server. This method will update the GUI to reflect
     * the changes. invokeLater to explicit add the event to the EDT since this
     * method will get called from outside of the EDT.
     * @param chatRooms list of chatrooms
     */
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
    /**
     * Method called when a chatroom that the client is present in is updated.
     * This method will update the GUI to reflect the changes.
     * invokeLater to explicit add the event to the EDT since this
     * method will get called from outside of the EDT.
     * @param r the chatroom
     */
    public void updateChat(final ChatRoom r){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame.updateChat(r);
            }
        });
    }
    /**
     * Method called when a attempt to register a client at the server was
     * successful.
     * This method will create a new mainframe to represent the connection to
     * the chatserver. invokeLater to explicit add the event to the EDT since
     * this method will get called from outside the EDT.
     *
     * @param client The newly registered client.
     */
    public void successfulRegistration(final Client client){
        this.client = client;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainFrame = new MainFrame(contr);
                startFrame.setVisible(false);
            }
        });
        getClients();
        getChatRooms();
    }
    
    /**
     * Method called when a attempt to register a client at the server failed
     * because the username already was taken.
     * invokeLater to explicit add the event to the EDT since this method will
     * get called from outside the EDT.
     * @param client the client that could'nt be registered.
     */
    public void failedRegistration(Client client){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "That username is already taken",
                        "Invalid username", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    /**
     * getClients. This method will start a swing-worker to fetch clients
     * from the server.
     */
    public void getClients(){
        new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.getClients), client).execute();
    }
    
    /**
     * getChatRooms. This method will start a swing-worker to fetch chatrooms
     * from the server.
     */
    public void getChatRooms(){
        new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.getChatRooms), client).execute();
    }
    //ActionListener for join-button at startframe.
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
    //ActionListener for send-button at mainpanel
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
    //ActionListener for "join chatroom" button at mainpanel
    class JoinChatRoomListener implements ActionListener {
        private final JTable table;
        
        JoinChatRoomListener(JTable table){
            this.table = table;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = table.getSelectedRow();
            if(row != -1){
                int chatRoomID = Integer.parseInt((String)table.getModel().getValueAt(table.convertRowIndexToModel(row), 0));
                new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.joinChat), chatRoomID, client).execute();
            }
        }
    }
    Client getClient(){
        return client;
    }
    //ActionListener for "create new chatroom" button at mainpanel
    class NewChatRoomListener implements ActionListener {
        NewChatRoomListener(){
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.addChatRoom), client).execute();
        }
    }
    //ActionListener for "destroy chatroom" button at chatpanel.
    class DestroyChatRoomListener implements ActionListener {
        private int id;
        DestroyChatRoomListener(int id){
            this.id = id;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.destroyChatRoom),id, client).execute();
        }
    }
    //ActionListener for "leave chatroom" button at chatpanel.
    class LeaveChatRoomListener implements ActionListener {
        private int id;
        LeaveChatRoomListener(int id){
            this.id = id;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.leaveChatRoom),id, client).execute();
        }
    }
    //ActionListener for "block user" button at chatpanel/mainpanel
    class BlockUserListener implements ActionListener {
        private JList users;
        BlockUserListener(JList users){
            this.users = users;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                client.blockClient((String) users.getSelectedValue());
            }
            catch(RemoteException e2){
                remoteExceptionHandler(e2);
            }
        }
    }
    //ActionListener for "unblock user" button at chatpanel/mainpanel
    class UnBlockUserListener implements ActionListener {
        private JList blockedUsers;
        UnBlockUserListener(JList blockedUsers){
            this.blockedUsers = blockedUsers;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                client.unBlockClient((String) blockedUsers.getSelectedValue());
            }
            catch(RemoteException e2){
                remoteExceptionHandler(e2);
            }
        }
    }
    //ActionListener for "DM user" at chatpanel
    class InviteUserListener implements ActionListener {
        private JList users;
        InviteUserListener(JList users){
            this.users = users;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
            if(client.getBlockedList().contains((String) users.getSelectedValue())){
                JOptionPane.showMessageDialog(null, "You can't invite " + users.getSelectedValue()
                        + " to a private chat when he/she is on your blocked list",
                        "DirectMessage error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if(users.getSelectedValue().equals(client.getName())){
                JOptionPane.showMessageDialog(null, "You can't invite yourself to a private chat",
                        "DirectMessage error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            }
            catch(RemoteException e3){
                remoteExceptionHandler(e3);
            }
                
            new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.privateChatRoom),(String) users.getSelectedValue(), client).execute();
        }
    }
    //Method called when the mainframe gets closed
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
    
    /**
     * Method called when remote exception occurs. Informs the user about
     * what happened
     * @param e the remoteexception
     */
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
    /**
     * Method called when a user tries to invite another user to a chatroom but
     * it fails since the other user have blocked this user.    
     * @param username Name of the username that have blocked this user
     */
    public void BlockedMessage(final String username){        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "You  can't invite " + username
                        + " to a private chat since he/she has blocked you",
                        "PrivateChatRoomError", JOptionPane.INFORMATION_MESSAGE);
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
