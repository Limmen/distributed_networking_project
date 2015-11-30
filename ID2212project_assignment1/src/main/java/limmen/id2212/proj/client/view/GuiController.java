/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import limmen.id2212.proj.client.model.Participant;
import limmen.id2212.proj.client.model.PutWorker;
import limmen.id2212.proj.client.model.RequestWorker;

/**
 *
 * @author kim
 */
public class GuiController {
    private final GuiController contr = this;
    private final StartFrame startFrame;
    private MainFrame mainFrame;
    public GuiController(){
        startFrame = new StartFrame(contr);
    }
    public static void main(String[] args){
        new GuiController();
    }
    public void closeMainFrame(){
        startFrame.setVisible(true);
        mainFrame.setVisible(false);
        mainFrame = null;
        
    }
    public void invalidInput(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(null, "That is not valid input",
                        "Invalid input", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
    public void updateParticipants(){
        new RequestWorker(contr).execute();
    }
    public void updateParticipants(final ArrayList<Participant> participants){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(mainFrame == null || !mainFrame.isVisible()){
                    mainFrame = new MainFrame(contr);
                    startFrame.setVisible(false);
                }
                mainFrame.updateParticipants(participants);
            }
        });        
    }
    class ConnectListener implements ActionListener {
        private final JTextField hostField;
        private final JTextField portField;
        
        ConnectListener(JTextField hostField, JTextField portField){
            this.hostField = hostField;
            this.portField = portField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(hostField.getText().length() > 0 && portField.getText().length() > 0){
                new RequestWorker(contr).execute();
            }
            else{
                invalidInput();
            }
            hostField.setText("");
            portField.setText("");
        }        
    }
    class SaveListener implements ActionListener {
        private final MainPanel mainPanel;
        
        SaveListener(MainPanel mainPanel){
            this.mainPanel = mainPanel;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Participant> participants = mainPanel.getTableData();
            new PutWorker(contr, participants).execute();
        }
    }
}
