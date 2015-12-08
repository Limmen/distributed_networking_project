/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package limmen.id2212.proj.client.view;

import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import limmen.id2212.proj.client.model.Participant;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class EditFrame extends JFrame {
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    private final ArrayList<Participant> participants;
    private final Participant participant;
    private final GuiController contr;
    private final EditFrame frame;
    public EditFrame(Participant p, ArrayList<Participant> participants, GuiController contr){
        frame = this;
        this.participants = participants;
        this.participant = p;
        this.contr = contr;
        this.setLayout(new MigLayout());
        this.setTitle("Edit Participant");
        this.setContentPane(new JScrollPane(new Container()));
        pack();
        setLocationRelativeTo(null);    // centers on screen
        setVisible(true);
    }
    
    private class Container extends JPanel{
        Container(){
            setLayout(new MigLayout("wrap 1, insets 50 50 50 50"));  //insets T, L, B, R
            add(new EditPanel(), "span 1");
        }
    }
    private class EditPanel extends JPanel{
        JTextField idField;
        JTextField nameField;
        JTextField countryField;
        JTextField genderField;
        JTextField birthdayField;
        JTextField heightField;
        JTextField weightField;
        JTextField sportField;
        public EditPanel(){
            final SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd");
            setLayout(new MigLayout("wrap 2"));
            JLabel lbl = new JLabel("Edit participant: " + participant.getName() + " ID:" + participant.getID());
            lbl.setFont(Title);
            add(lbl, "span 2");
            lbl = new JLabel("Name");
            lbl.setFont(Plain);
            add(lbl, "span 1");
            nameField = new JTextField(25);
            nameField.setFont(Plain);
            nameField.setText(participant.getName());
            add(nameField, "span 1");
            lbl = new JLabel("Gender");
            lbl.setFont(Plain);
            add(lbl, "span 1");
            genderField = new JTextField(25);
            genderField.setFont(Plain);
            genderField.setText(Character.toString(participant.getGender()));
            add(genderField, "span 1");
            lbl = new JLabel("Country");
            lbl.setFont(Plain);
            add(lbl, "span 1");
            countryField = new JTextField(25);
            countryField.setFont(Plain);
            countryField.setText(participant.getCountry());
            add(countryField, "span 1");
            lbl = new JLabel("Birthday");
            lbl.setFont(Plain);
            add(lbl, "span 1");
            birthdayField = new JTextField(25);
            birthdayField.setFont(Plain);
            birthdayField.setText(format.format(participant.getBirthday()));
            add(birthdayField, "span 1");
            lbl = new JLabel("Height");
            lbl.setFont(Plain);
            add(lbl, "span 1");
            heightField = new JTextField(25);
            heightField.setFont(Plain);
            heightField.setText(Float.toString(participant.getHeight()));
            add(heightField, "span 1");
            lbl = new JLabel("Weight");
            lbl.setFont(Plain);
            add(lbl, "span 1");
            weightField = new JTextField(25);
            weightField.setFont(Plain);
            weightField.setText(Float.toString(participant.getWeight()));
            add(weightField, "span 1");
            lbl = new JLabel("Sport");
            lbl.setFont(Plain);
            add(lbl, "span 1");
            sportField = new JTextField(25);
            sportField.setFont(Plain);
            sportField.setText(participant.getSport());
            add(sportField, "span 1");
            JButton save = new JButton("Save edit");
            save.addActionListener(contr. new EditListener(idField, nameField, genderField,
                    countryField, birthdayField, heightField, weightField, countryField,
                    participant, frame));
            add(save, "span 2");
        }        
    }
}
