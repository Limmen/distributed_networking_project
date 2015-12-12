/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
*/
package limmen.id2212.nog.client.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import limmen.id2212.nog.client.model.Client;
import limmen.id2212.nog.server.ChatRoom;

/**
 *
 * @author kim
 */
public class Container extends JTabbedPane{
    private final GuiController contr;
    private MainPanel mainPanel;
    private final Color defaultBackColor;
    private final Color defaultForeColor;
    private final ArrayList<ChatPanel> blinkingPanes = new ArrayList();
    Container(GuiController contr){
        this.contr = contr;
        mainPanel = new MainPanel(contr);
        addTab("Chat center", mainPanel);
        addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                System.out.println("Tab: " + getSelectedIndex());
                if(getSelectedIndex() != 0){
                if(blinkingPanes.contains((ChatPanel) getComponentAt(getSelectedIndex())))
                    blinkingPanes.remove((ChatPanel) getComponentAt(getSelectedIndex()));
                }
            }
        });
        defaultBackColor = getBackgroundAt(0);
        defaultForeColor = getForegroundAt(0);
        Timer timer = new Timer(500, new ActionListener() {
            boolean blinkFlag = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                blink(blinkFlag);
                blinkFlag = !blinkFlag;
            }
        });
        timer.start();
    }
    void updateMainFrameClients(ArrayList<Client> clients) throws RemoteException{
        mainPanel.updateMainFrameClients(clients);
    }
    void updateMainFrameChatRooms(ArrayList<ChatRoom> chatRooms) throws RemoteException{
        mainPanel.updateMainFrameChatRooms(chatRooms);
        for(ChatRoom r : chatRooms){
            for(Client c : r.getUsers()){
                if(c.getName().equals(contr.getClient().getName())){
                    System.out.println("Name is equals but references is: " + c.equals(contr.getClient()));
                    System.out.println("The chatroom contains this user");
                    if(!checkIfTabisCreated("ChatRoom " +  r.getID())){
                        System.out.println("The chatRoom tab is not created");
                        addTab("ChatRoom " + r.getID(), new ChatPanel(r, contr));
                    }
                    System.out.println("The chatroom tab is created");
                }
            }
        }
        for(ChatPanel panel : getAllChatTabs()){
            if(!chatRooms.contains(panel.getChatRoom()))
                remove(panel);
        }       
        repaint();
    }
    void updateChat(ChatRoom r){
        try{
        for(int i = 1; i < getTabCount(); i++){
            ChatPanel p = (ChatPanel) getComponentAt(i);
            if(p.getChatRoom().equals(r)){
                System.out.println("Updating chat!");
                p.updateChat();
                if(!blinkingPanes.contains(p) && !p.equals((ChatPanel) getComponentAt(getSelectedIndex())))
                    blinkingPanes.add(p);
            }
        }
        }
        catch(RemoteException e){
            
        }
    }
    void updateBlocked(ArrayList<String> blocked){
        mainPanel.updateBlocked(blocked);
        for(int i = 1; i < getTabCount(); i++){
            ChatPanel p = (ChatPanel) getComponentAt(i);
            p.updateBlocked(blocked);
        }
    }
    private boolean checkIfTabisCreated(String title){
        boolean bool = false;
        for(int i = 0; i < getTabCount(); i++){
            if(getTitleAt(i).equals(title))
                bool = true;
        }
        return bool;
    }
    private ArrayList<ChatPanel> getAllChatTabs(){
        ArrayList<ChatPanel> chats = new ArrayList();
        boolean bool = false;
        for(int i = 1; i < getTabCount(); i++){
            chats.add((ChatPanel) getComponentAt(i));
        }
        return chats;
    } 
    private void blink(boolean blinkFlag) {
        if (blinkFlag) {
            for(ChatPanel p : blinkingPanes){
                setForegroundAt(indexOfComponent(p), Color.green);
                setBackgroundAt(indexOfComponent(p), Color.orange);   
            }
        } else {
             for(ChatPanel p : blinkingPanes){
                setForegroundAt(indexOfComponent(p), defaultForeColor);
                setBackgroundAt(indexOfComponent(p), defaultBackColor);   
            };
        }
        repaint();
    }
}
