
/**
 * Connect to a Database Learning Team B PRG/421 February 23, 2015 Roland Morales
 */

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

/**
 * The CharityPanel class allows the user to input first and last name.
 */
public class CharityPanel extends JPanel {

    private JPanel charities; // To hold components
    private JPanel selectedCharityPanel; // To hold components
    private JLabel label; // To display a message
    public static JComboBox charityBox; // List of charities
    public static JTextField selectedCharity; // The selected charity

    // The following array holds the values that will be
    // displayed in the charityBox combo box.
    private String[] charity = {null, "American Red Cross", "Salvation Army",
        "Toys for Tots", "Susan G. Komen", "Warren Buffet"};

    /**
     * Constructor
     */
    public CharityPanel() {

        // Create a BorderLayout manager for the content pane.
        setLayout(new BorderLayout());

        // Build the panels.
        buildCharityPanel();
        buildSelectedCharityPanel();

        // Add the panels to the content pane.
        add(charities, BorderLayout.CENTER);
        add(selectedCharityPanel, BorderLayout.SOUTH);
    }

    /**
     * The buildCharityPanel method adds a combo box with the charities to a
     * panel.
     */
    private void buildCharityPanel() {
        // Create a panel to hold the combo box.
        charities = new JPanel();

        charities.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Create charity selection label.
        label = new JLabel("Please Select a Charity  ");

        // Create the combo box and set dimensions.
        charityBox = new JComboBox(charity);
        charityBox.setPreferredSize(new Dimension(300, 20));

        // Register an action listener.
        charityBox.addActionListener(new ComboBoxListener());

        // Add the label and combo box to the panel.
        charities.add(label);
        charities.add(charityBox);
    }

    private void buildSelectedCharityPanel() {
        // Create a panel to hold the text field.
        selectedCharityPanel = new JPanel();

        selectedCharityPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Create the label.
        label = new JLabel("You selected: ");

        // Create the uneditable text field.
        selectedCharity = new JTextField(27);
        selectedCharity.setEditable(false);

        // Add the label and text field to the panel.
        selectedCharityPanel.add(label);
        selectedCharityPanel.add(selectedCharity);
    }

    /**
     * Private inner class that handles the event when the user selects an item
     * from the combo box.
     */
    private class ComboBoxListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String selection = (String) charityBox.getSelectedItem();
            selectedCharity.setText(selection);
        }
    }

    public static String getCharity() {

        String str;
        str = selectedCharity.getText();
        return str;
    }
}
