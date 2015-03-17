
/**
 * Connect to a Database Learning Team B PRG/421 February 23, 2015 Roland Morales
 */

import java.awt.*;
import javax.swing.*;

/**
 * The DonorPanel class allows the user to input first and last name and
 * donation amount.
 */
public class DonorPanel extends JPanel {

    public static JTextField firstTextField; // Text field for first name.
    public static JTextField lastTextField; // Text field for last name.
    public static JTextField pledgeTextField; // Text field for last name.

    public static double pledgeAmount;

    /**
     * Constructor
     */
    public DonorPanel() {

        // Create a GridLayout manager with
        // three rows and one column.
        setLayout(new GridLayout(3, 1));

        // Create text fields for first and last name
        // and pledge amount.
        firstTextField = new JTextField(15);
        lastTextField = new JTextField(15);
        pledgeTextField = new JTextField(15);

        // Create labels for first and last name
        // and pledge amount.
        JLabel messageLabelFirst = new JLabel("First Name");
        JLabel messageLabelLast = new JLabel("Last Name");
        JLabel messageLabelPledge = new JLabel("Pledge Amount");

        // Create panels for first and last name
        // and pledge amount.
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        // Position the panels.
        panel1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel2.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel3.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Add the labels to the panels.
        panel1.add(messageLabelFirst);
        panel2.add(messageLabelLast);
        panel3.add(messageLabelPledge);

        // Add the text fields to the panels.
        panel1.add(firstTextField);
        panel2.add(lastTextField);
        panel3.add(pledgeTextField);

        // Add the panels to the content pane.
        add(panel1); // Row 1.
        add(panel2); // Row 2.
        add(panel3); // Row 3.
    }

    /**
     * The getDonor method returns the employee's full name.
     */
    public static String getDonor() {

        String firstName;
        String lastName;

        firstName = firstTextField.getText();

        lastName = lastTextField.getText();

        // Returns firstname and lastname.
        return firstName + " " + lastName;

    }

    /**
     * The getPledgAmount method returns the pledge amount.
     */
    public static double getPledgeAmount() {

        String str = pledgeTextField.getText();
        try {
            pledgeAmount = Double.parseDouble(str);

        } catch (NumberFormatException z) {
            pledgeAmount = -1;
        }
        return pledgeAmount;
    }
}
