/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import limmen.id2212.nog.client.model.Client;
import limmen.id2212.nog.server.ChatRoom;
import net.miginfocom.swing.MigLayout;

/**
 * ChatPanel that represents a chat on the mainframe.
 * @author kim
 */
public class ChatPanel extends JPanel{
    private final ChatRoom chatRoom;
    private final GuiController contr;
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final JTextArea chatArea;
    private final DefaultListModel usersModel;
    private final DefaultListModel blockedModel;
    private final JList usersList;
    private final JList blockedList;
    
    /**
     * Class constructor.
     * @param chatRoom ChatRoom that this panel reflects the state of
     * @param contr GuiController
     * @throws RemoteException throws RemoteException
     */
    public ChatPanel(ChatRoom chatRoom, GuiController contr) throws RemoteException{
        this.chatRoom = chatRoom;
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("ChatRoom " + chatRoom.getID());
        lbl.setFont(Title);
        add(lbl, "span 2");
        try{
            lbl = new JLabel("User: " + contr.getClient().getName());
            add(lbl, "span 2, gaptop 10");
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
        chatArea = new JTextArea("");
        chatArea.setLineWrap(true);
        chatArea.setEditable(false);
        chatArea.setFont(Plain);
        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(300, 450));
        JPanel chatPanel = new JPanel(new MigLayout("wrap 1"));
        chatPanel.add(scroll, "span 1");
        JPanel usersPanel = new JPanel(new MigLayout("wrap 1"));
        lbl = new JLabel("Online users");
        lbl.setFont(Plain);
        usersPanel.add(lbl, "span 1, gaptop 20");
        usersModel = new DefaultListModel<String>();
        usersList = new JList(usersModel);
        usersList.setFont(Plain);
        scroll = new JScrollPane(usersList);
        scroll.setPreferredSize(new Dimension(170,350));
        usersPanel.add(scroll, "span 1");
        JButton blockButton = new JButton("Block selected user");
        blockButton.addActionListener(contr. new BlockUserListener(usersList));
        JButton inviteButton = new JButton("DM selected user");
        inviteButton.addActionListener(contr. new InviteUserListener(usersList));
        usersPanel.add(blockButton, "span 1");
        usersPanel.add(inviteButton, "span 1");
        add(chatPanel, "span 1");
        add(usersPanel, "span 1");
        JTextArea messageArea = new JTextArea("");
        messageArea.setLineWrap(true);
        messageArea.setFont(Plain);
        scroll = new JScrollPane(messageArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setPreferredSize(new Dimension(300, 100));
        add(scroll, "span 2");
        JButton sendButton = new JButton("Send");
        sendButton.setFont(Title);
        sendButton.addActionListener(contr. new SendListener(chatRoom, messageArea));
        add(sendButton, "span 2");
        if(contr.getClient().equals(chatRoom.getCreator())){
            JButton destroyRoom = new JButton("Destroy chatroom");
            destroyRoom.setFont(PBold);
            destroyRoom.addActionListener(contr.new DestroyChatRoomListener(chatRoom.getID()));
            add(destroyRoom, "span 2");
        }
        else{
            JButton leaveRoom = new JButton("Leave chatroom");
            leaveRoom.setFont(PBold);
            leaveRoom.addActionListener(contr. new LeaveChatRoomListener(chatRoom.getID()));
            add(leaveRoom, "span 2");
        }
        lbl = new JLabel("Blocked users:");
        lbl.setFont(Plain);
        add(lbl, "span 2, gaptop 20");
        blockedModel = new DefaultListModel<String>();
        blockedList = new JList(blockedModel);
        blockedList.setFont(Plain);
        scroll = new JScrollPane(blockedList);
        scroll.setPreferredSize(new Dimension(200,150));
        add(scroll, "span 2");
        JButton unBlockButton = new JButton("Unblock selected user");
        unBlockButton.addActionListener(contr. new UnBlockUserListener(blockedList));
        add(unBlockButton, "span 2");
        updateChat();
        updateBlocked();
    }
    ChatRoom getChatRoom(){
        return chatRoom;
    }
    void updateChat(){
        String chat = "";
        try{
            for(String s : chatRoom.getMessages(contr.getClient())){
                chat = chat + s;
            }
            chatArea.setText(chat);
            usersModel.removeAllElements();
            for(Client c  : chatRoom.getUsers()){
                usersModel.addElement(c.getName());
            }
            repaint();
            revalidate();
        }
        catch(RemoteException e){
            e.printStackTrace();
        }
    }
    void updateBlocked(ArrayList<String> blocked){
        blockedModel.removeAllElements();
        for(String str  : blocked){
            blockedModel.addElement(str);
        }
        repaint();
        revalidate();
    }
    void updateBlocked(){
        try{
            blockedModel.removeAllElements();
            for(String str  : contr.getClient().getBlockedList()){
                blockedModel.addElement(str);
            }
            repaint();
            revalidate();
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
    }
    
    
}
