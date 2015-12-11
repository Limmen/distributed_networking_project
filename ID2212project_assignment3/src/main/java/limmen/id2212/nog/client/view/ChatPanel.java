/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.awt.Dimension;
import java.awt.Font;
import java.rmi.RemoteException;
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
 *
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
    private final JList usersList;
    public ChatPanel(ChatRoom chatRoom, GuiController contr) throws RemoteException{
        this.chatRoom = chatRoom;
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("ChatRoom " + chatRoom.getID());
        lbl.setFont(Title);
        add(lbl, "span 2");
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
        updateChat();        
    }
    ChatRoom getChatRoom(){
        return chatRoom;
    }
    void updateChat(){
        String chat = "";
        try{
            for(String s : chatRoom.getMessages()){
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
    
    
}