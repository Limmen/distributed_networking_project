/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
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
    private final DateFormat format;
    private ArrayList<Participant> participants = new ArrayList();
    public GuiController(){
        startFrame = new StartFrame(contr);
        format = new SimpleDateFormat("yyyy/mm/dd", Locale.ENGLISH);
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
    public void getParticipants(){
        new RequestWorker(contr).execute();
    }
    public void putParticipants(ArrayList<Participant> participants){
        new PutWorker(contr, participants).execute();
        
    }
    public void updateParticipants(final ArrayList<Participant> participants){
        this.participants = participants;
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
    class AddListener implements ActionListener{
        JTextField idField;
        JTextField nameField;
        JTextField genderField;
        JTextField countryField;
        JTextField birthdayField;
        JTextField heightField;
        JTextField weightField;
        JTextField sportField;
        AddListener(JTextField idField, JTextField nameField,
        JTextField genderField, JTextField countryField,JTextField birthdayField, 
        JTextField heightField, JTextField weightField, JTextField sportField){
            this.idField = idField;
            this.nameField = nameField;
            this.genderField = genderField;
            this.countryField = countryField;
            this.birthdayField = birthdayField;
            this.heightField = heightField;
            this.weightField = weightField;
            this.sportField  = sportField;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(idField.getText().length() < 1 ||
                    nameField.getText().length() < 1 ||
                    genderField.getText().length() != 1 ||
                    countryField.getText().length() < 1 ||
                    birthdayField.getText().length() < 1 ||
                    heightField.getText().length() < 1 ||
                    weightField.getText().length() < 1 ||
                    sportField.getText().length() < 1){
                invalidInput();
                clear();
                return;
            }                            
            try{
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                char gender = genderField.getText().charAt(0);
                String country = genderField.getText();
                Date birthday = format.parse(birthdayField.getText());
                Float height = Float.parseFloat(heightField.getText());
                Float weight = Float.parseFloat(weightField.getText());
                String sport = sportField.getText();
                
                for(Participant p : participants){
                    if(p.getID() == id){
                        invalidInput();
                        clear();
                        return;
                    }
                }
                participants.add(new Participant(id,name,gender,country,birthday,
                height,weight,sport));
                new PutWorker(contr, participants).execute();                
            }catch(ParseException | NumberFormatException e2){
                invalidInput();
                clear();
            }
            clear();
            
        }
        void clear(){
            idField.setText("");
            nameField.setText("");
            genderField.setText("");
            countryField.setText("");
            birthdayField.setText("");
            heightField.setText("");
            weightField.setText("");
            sportField.setText("");
        }
        
    }
}
