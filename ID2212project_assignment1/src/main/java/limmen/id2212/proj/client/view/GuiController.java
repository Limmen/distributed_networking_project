/*
* Course project - ID2212 Network Programming with Java
* Royal Institute of Technology
* 2015 (c) Kim Hammar
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import limmen.id2212.proj.client.model.Participant;
import limmen.id2212.proj.client.model.PutWorker;
import limmen.id2212.proj.client.model.RequestWorker;

/**
 * A controller. All calls to the model from the view go through here.
 * All calls from the model to update the view also goes through here.
 * @author kim
 */
public class GuiController {
    private final GuiController contr = this;
    private final StartFrame startFrame;
    private MainFrame mainFrame;
    private final DateFormat format;
    private ArrayList<Participant> participants = new ArrayList();
    
    /**
     * Class constructor.
     * Creates start-frame upon initialization.
     */
    public GuiController(){
        startFrame = new StartFrame(contr);
        format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
    }
    
    /**
     * Main-method, entry-point of the program.
     * @param args
     */
    public static void main(String[] args){
        new GuiController();
    }
    
    /**
     * This method is called when the user closes the mainFrame.
     * Will close the mainframe and show the startframe.
     */
    void closeMainFrame(){
        startFrame.setVisible(true);
        mainFrame.setVisible(false);
        mainFrame = null;
        
    }
    
    /**
     * Generic dialog-box for when the user enters invalid input.
     */
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
     * Spawns a RequestWorker to fetch participants from server.
     */
    public void getParticipants(){
        new RequestWorker(contr).execute();
    }
    
    /**
     * Spawns a PutWorker to store participants at the server.
     * @param participants list of all participants.
     */
    public void putParticipants(ArrayList<Participant> participants){
        this.participants = participants;
        new PutWorker(contr, participants).execute();
        updateParticipants(participants);        
    }
    
    /**
     * Updates participants at the client.
     * If the mainFrame is not already visible, it will be initialized.
     * @param participants list of all participants.
     */
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
    //ActionListener for connect-button at startframe.
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
    //ActionListener for add-button at mainFrame.
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
                updateParticipants(participants);
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
    //ActionListener for delete-button at MainFrame.
    class DeleteListener implements ActionListener {
        private final JTable table;
        
        DeleteListener(JTable table){
            this.table = table;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<Participant> updatedParticipants = new ArrayList();
            int row = table.getSelectedRow();
            if(row != -1){
                for(Participant p : participants){
                    if(!(p.getID() == Integer.parseInt((String) table.getModel().getValueAt(table.convertRowIndexToModel(row), 0)) &&
                            p.getName().equals(table.getModel().getValueAt(table.convertRowIndexToModel(row), 1)) &&
                            Character.toString(p.getGender()).equals(table.getModel().getValueAt(table.convertRowIndexToModel(row), 2)) &&
                            p.getCountry().equals(table.getModel().getValueAt(table.convertRowIndexToModel(row), 3)) &&
                            format.format(p.getBirthday()).equals(table.getModel().getValueAt(table.convertRowIndexToModel(row), 4)) &&
                            p.getHeight() == Float.parseFloat((String) table.getModel().getValueAt(table.convertRowIndexToModel(row), 5)) &&
                            p.getWeight() == Float.parseFloat((String) table.getModel().getValueAt(table.convertRowIndexToModel(row), 6)) &&
                            p.getSport().equals(table.getModel().getValueAt(table.convertRowIndexToModel(row), 7)))){
                        updatedParticipants.add(p);
                    }
                }
                participants = updatedParticipants;
                new PutWorker(contr, participants).execute();
                updateParticipants(participants);
            }
        }
    }
    //ActionListener for Edit-button at EditFrame
    class EditListener implements ActionListener{
        JTextField nameField;
        JTextField genderField;
        JTextField countryField;
        JTextField birthdayField;
        JTextField heightField;
        JTextField weightField;
        JTextField sportField;
        EditFrame editPanel;
        Participant participant;
        EditListener(JTextField nameField,
                JTextField genderField, JTextField countryField,JTextField birthdayField,
                JTextField heightField, JTextField weightField, JTextField sportField, Participant participant,EditFrame frame){
            this.nameField = nameField;
            this.genderField = genderField;
            this.countryField = countryField;
            this.birthdayField = birthdayField;
            this.heightField = heightField;
            this.weightField = weightField;
            this.sportField  = sportField;
            this.editPanel = frame;
            this.participant = participant;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            if(nameField.getText().length() > 0 &&
                    genderField.getText().length() == 1 &&
                    countryField.getText().length() > 0 &&
                    birthdayField.getText().length() > 0 &&
                    weightField.getText().length() > 0 &&
                    heightField.getText().length() > 0 &&
                    sportField.getText().length() > 0){
                try{
                    participant.setName(nameField.getText());
                    participant.setGender(genderField.getText().charAt(0));
                    participant.setCountry(countryField.getText());
                    participant.setBirthday(format.parse(birthdayField.getText()));
                    participant.setWeight(Float.parseFloat(weightField.getText()));
                    participant.setHeight(Float.parseFloat(heightField.getText()));
                    participant.setSport(sportField.getText());
                    new PutWorker(contr, participants).execute();
                    updateParticipants(participants);
                    editPanel.dispose();}
                catch(ParseException | NumberFormatException e2){
                    invalidInput();
                }
            }
            else{
                invalidInput();
            }
        }
    }
}
