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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import limmen.id2212.nog.client.model.Client;
import limmen.id2212.nog.server.ChatRoom;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class MainPanel extends JPanel{
    private final GuiController contr;
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final DefaultTableModel chatRoomsModel;
    private final String[] chatRoomColumnNames;
    private final DefaultListModel usersModel;   
    private final DefaultListModel blockedModel;   
    private final JTable chatRoomsTable;
    private final JList usersList;
    private final JList blockedList;
    public MainPanel(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("NOG Chat rooms");
        lbl.setFont(Title);
        add(lbl, "span 2");
        try{
        lbl = new JLabel("User: " + contr.getClient().getName());
        add(lbl, "span 2, gaptop 10");
        }
        catch(RemoteException e){
            contr.remoteExceptionHandler(e);
        }
        JPanel chatRoomsPanel = new JPanel(new MigLayout("wrap 1"));
        chatRoomColumnNames = new String[3];
        chatRoomColumnNames[0] = "ID";
        chatRoomColumnNames[1] = "Creator";
        chatRoomColumnNames[2] = "Number of users";
        String[][] RowData = new String[0][0];
        lbl = new JLabel("Chatrooms");
        lbl.setFont(Plain);
        chatRoomsPanel.add(lbl, "span 1, gaptop 20");
        chatRoomsModel = new DefaultTableModel(RowData,chatRoomColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        chatRoomsTable = new JTable(chatRoomsModel);
        chatRoomsTable.setFont(Plain);
        chatRoomsTable.getTableHeader().setFont(PBold);
        JScrollPane scroll = new JScrollPane(chatRoomsTable);
        scroll.setPreferredSize(new Dimension(500,400));
        chatRoomsPanel.add(scroll, "span 1");
        JPanel usersPanel = new JPanel(new MigLayout("wrap 1"));
        lbl = new JLabel("Online users");
        lbl.setFont(Plain);
        usersPanel.add(lbl, "span 1, gaptop 20");
        usersModel = new DefaultListModel<String>();
        usersList = new JList(usersModel);
        usersList.setFont(Plain);
        scroll = new JScrollPane(usersList);
        scroll.setPreferredSize(new Dimension(200,400));
        usersPanel.add(scroll, "span 1");
        add(chatRoomsPanel, "span 1");
        add(usersPanel, "span 1");
        JButton newChatRoomButton = new JButton("Create new chatroom");
        newChatRoomButton.setFont(PBold);
        newChatRoomButton.addActionListener(contr.new NewChatRoomListener());
        add(newChatRoomButton, "span 2, gaptop 20");
        JButton joinChatRoomButton = new JButton("Join selected chatRoom");
        joinChatRoomButton.setFont(PBold);
        joinChatRoomButton.addActionListener(contr.new JoinChatRoomListener(chatRoomsTable));
        add(joinChatRoomButton, "span 2");
        lbl = new JLabel("Blocked users:");
        lbl.setFont(Plain);
        add(lbl, "span 2, gaptop 20");
        blockedModel = new DefaultListModel<String>();
        blockedList = new JList(blockedModel);
        blockedList.setFont(Plain);
        scroll = new JScrollPane(blockedList);
        scroll.setPreferredSize(new Dimension(200,150));
        add(scroll, "span 2");
        updateBlocked();
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
    void updateMainFrameClients(ArrayList<Client> clients) throws RemoteException{
        if(clients.size() < 1 )
            return;
        usersModel.removeAllElements();
        for(Client c  : clients){
            usersModel.addElement(c.getName());
        }        
        repaint();
        revalidate();
    }
    void updateMainFrameChatRooms(ArrayList<ChatRoom> chatRooms) throws RemoteException{
        chatRoomsModel.getDataVector().removeAllElements();
        if(chatRooms.size() < 1 )
            return;
        String[][] rowData = new String[chatRooms.size()][3];
        for(int i = 0; i <  chatRooms.size(); i++)
        {
            ChatRoom c = chatRooms.get(i);
            rowData[i][0] = Integer.toString(c.getID());
            rowData[i][1] = c.getCreator().getName();
            rowData[i][2] = Integer.toString(c.getUsers().size());
        }
        chatRoomsModel.setDataVector(rowData, chatRoomColumnNames);
        chatRoomsTable.repaint();
        repaint();
        revalidate();
    }
    
}
