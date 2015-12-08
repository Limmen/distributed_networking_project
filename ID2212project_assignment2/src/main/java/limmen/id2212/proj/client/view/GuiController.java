/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import limmen.id2212.proj.client.model.Client;
import limmen.id2212.proj.client.model.ClientImpl;
import limmen.id2212.proj.client.model.NOGWorker;
import limmen.id2212.proj.client.util.ServerCommand;
import limmen.id2212.proj.client.util.ServerCommandName;
import limmen.id2212.proj.client.util.TableDTO;
import limmen.id2212.proj.client.util.TableDTOImpl;
import limmen.id2212.proj.server.model.NogServer;
import limmen.id2212.proj.util.Participant;

/**
 *
 * @author kim
 */
public class GuiController {
    private static final String DEFAULT_SERVER_NAME = "ID2212_NOG_INFORMATION_SYSTEM";
    private final GuiController contr = this;
    private MainFrame mainFrame;
    private final DateFormat format;
    private ArrayList<Participant> participants = new ArrayList();
    private NogServer serverobj;
    private Client client;
    public GuiController(){
        connectToServer();
        mainFrame = new MainFrame(contr);
        registerClient();
        getParticipants();     
        format = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
    }
    public static void main(String[] args){
        new GuiController();
    }
    private void connectToServer(){
        try {
            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            serverobj = (NogServer) Naming.lookup(DEFAULT_SERVER_NAME);
        } catch (Exception e) {
            System.out.println("The runtime failed: " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to server: " + DEFAULT_SERVER_NAME);
    }
    private void registerClient(){
        try{      
            client = new ClientImpl(contr);
            serverobj.registerClient(client);
        }
        catch(RemoteException e){
            remoteExceptionHandler(e);
        }
    }
    public void quit(){
        try{
            serverobj.deRegisterClient(client);            
        }
        catch(RemoteException e){
            remoteExceptionHandler(e);
        }
        System.exit(0);
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
    public final void getParticipants(){
        new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.getParticipants), client).execute();
    }
    public void updateParticipants(final ArrayList<Participant> participants){
        this.participants = participants;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
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
                new NOGWorker(serverobj, contr, new ServerCommand(ServerCommandName.getParticipants), client).execute();
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
                TableDTO p = new TableDTOImpl(id,name,gender,country,birthday,height,weight,sport);
                ServerCommand command = new ServerCommand(ServerCommandName.addParticipant);
                command.setParticipant(p);
                new NOGWorker(serverobj, contr,command, client).execute();                
            }catch(ParseException | NumberFormatException e2){
                invalidInput();
                clear();
            }
            catch(RemoteException e3){
                remoteExceptionHandler(e3);
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
    class DeleteListener implements ActionListener {
        private final JTable table;
        
        DeleteListener(JTable table){
            this.table = table;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
           int row = table.getSelectedRow();
            if(row != -1){
               try {
                   TableDTO p = new TableDTOImpl(Integer.parseInt((String) table.getModel().getValueAt(row, 0)),
                           (String) table.getModel().getValueAt(row, 1),
                           ((String)table.getModel().getValueAt(row, 2)).charAt(0),
                           (String) table.getModel().getValueAt(row, 3),
                           format.parse((String) table.getModel().getValueAt(row, 4)),
                           Float.parseFloat((String) table.getModel().getValueAt(row, 5)),
                           Float.parseFloat((String) table.getModel().getValueAt(row, 6)),
                           (String) table.getModel().getValueAt(row, 7));
                   ServerCommand command = new ServerCommand(ServerCommandName.deleteParticipant);
                   command.setParticipant(p);
                   new NOGWorker(serverobj, contr,command, client).execute();
               } catch (ParseException ex) {
                   Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
               }
               catch(RemoteException e2){
                   contr.remoteExceptionHandler(e2);
               }
            }
        }
    }
    class EditListener implements ActionListener{
        JTextField idField;
        JTextField nameField;
        JTextField genderField;
        JTextField countryField;
        JTextField birthdayField;
        JTextField heightField;
        JTextField weightField;
        JTextField sportField;
        EditFrame editPanel;
        TableDTO participant;
        EditListener(JTextField idField, JTextField nameField,
                JTextField genderField, JTextField countryField,JTextField birthdayField,
                JTextField heightField, JTextField weightField, JTextField sportField, TableDTO participant,EditFrame frame){
            this.idField = idField;
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
                    try {
                        participant.setBirthday(format.parse(birthdayField.getText()));
                    } catch (ParseException ex) {
                        Logger.getLogger(EditFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    participant.setWeight(Float.parseFloat(weightField.getText()));
                    participant.setHeight(Float.parseFloat(heightField.getText()));
                    participant.setSport(sportField.getText());
                    ServerCommand command = new ServerCommand(ServerCommandName.editParticipant);
                    command.setParticipant(participant);
                    new NOGWorker(serverobj, contr,command, client).execute();
                    editPanel.dispose();
                }
                catch(RemoteException e2){
                    contr.remoteExceptionHandler(e2);
                }
            }
            else{
                invalidInput();
            }
        }
    }
}
    
