/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package limmen.id2212.proj.client.view;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author kim
 */
public class NewPartPanel extends JPanel {
    private final GuiController contr;
    private final Font Plain = new Font("Serif", Font.PLAIN, 14);
    private final Font Title = new Font("Serif", Font.PLAIN, 18);
    private final Font PBold = Plain.deriveFont(Plain.getStyle() | Font.BOLD);
    public NewPartPanel(GuiController contr){
        this.contr = contr;
        setLayout(new MigLayout("wrap 2"));
        JLabel lbl = new JLabel("Add new participant to the NOG information-system");
        lbl.setFont(Title);
        add(lbl, "span 2, gapbottom 20");
        lbl = new JLabel("ID");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField idField = new JTextField(25);
        idField.setFont(Plain);
        add(idField, "span 1");
        lbl = new JLabel("Name");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField nameField = new JTextField(25);
        nameField.setFont(Plain);
        add(nameField, "span 1");
        lbl = new JLabel("Gender");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField genderField = new JTextField(25);
        genderField.setFont(Plain);
        add(genderField, "span 1");
        lbl = new JLabel("Country");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField countryField = new JTextField(25);
        countryField.setFont(Plain);
        add(countryField, "span 1");
        lbl = new JLabel("Birthday");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField birthdayField = new JTextField(25);
        birthdayField.setFont(Plain);
        add(birthdayField, "span 1");
        lbl = new JLabel("Height");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField heightField = new JTextField(25);
        heightField.setFont(Plain);
        add(heightField, "span 1");
        lbl = new JLabel("Weight");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField weightField = new JTextField(25);
        weightField.setFont(Plain);
        add(weightField, "span 1");
        lbl = new JLabel("Sport");
        lbl.setFont(Plain);
        add(lbl, "span 1");
        final JTextField sportField = new JTextField(25);
        sportField.setFont(Plain);
        add(sportField, "span 1");
        JButton addButton = new JButton("Add");
        addButton.setFont(PBold);
        addButton.addActionListener(contr. new AddListener(idField,nameField,
        genderField,countryField,birthdayField, heightField, weightField,sportField));
        add(addButton, "span 2, gaptop 20");
    }
}
