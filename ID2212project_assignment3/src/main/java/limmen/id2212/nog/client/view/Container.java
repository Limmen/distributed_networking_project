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
    private final MainPanel mainPanel;
    private final Color defaultBackColor;
    private final Color defaultForeColor;
    private final ArrayList<ChatPanel> blinkingPanes = new ArrayList();
    Container(GuiController contr){
        this.contr = contr;
        mainPanel = new MainPanel(contr);
        addTab("Chat center", mainPanel);
        addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if(getSelectedIndex() != 0){
                    if(blinkingPanes.contains((ChatPanel) getComponentAt(getSelectedIndex()))){
                        blinkingPanes.remove((ChatPanel) getComponentAt(getSelectedIndex()));
                        setForegroundAt(indexOfComponent(getComponentAt(getSelectedIndex())), defaultForeColor);
                        setBackgroundAt(indexOfComponent(getComponentAt(getSelectedIndex())), defaultBackColor);
                    }
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
        ArrayList<ChatRoom> publicRooms = new ArrayList();
        for(ChatRoom r : chatRooms){
            if(r.chatRoomIsPublic())
                publicRooms.add(r);
            for(Client c : r.getUsers()){
                if(c.getName().equals(contr.getClient().getName())){
                    if(!checkIfTabisCreated("ChatRoom " +  r.getID())){
                        addTab("ChatRoom " + r.getID(), new ChatPanel(r, contr));
                    }
                }
            }
        }
        mainPanel.updateMainFrameChatRooms(publicRooms);
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
                    p.updateChat();
                    
                    if(!blinkingPanes.contains(p) && indexOfComponent(p) != getSelectedIndex())
                        blinkingPanes.add(p);
                }
            }
        }
        catch(RemoteException | ClassCastException e){
            e.printStackTrace();
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
