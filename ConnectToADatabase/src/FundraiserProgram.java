
/**
 * Connect to a Database Learning Team B PRG/421 February 23, 2015 Roland Morales
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The FundraiserProgram class creates a GUI, collects data from the panels, and
 * displays the data on a table.
 */
public final class FundraiserProgram extends JFrame {

    // The following variables will reference the
    // custom panel objects.
    private static DonorPanel donor;
    private static CharityPanel charity;
    private static TablePanel table;

    // The following variables will reference objects
    // needed to add the Save, Refresh, and Exit buttons.
    public static JPanel buttonPanel;
    public static JButton save;
    public static JButton refresh;
    public static JButton db;
    public static JButton exit;

    // The following variable will be used to increment the output 
    // for position purposes.
    static int donorCount = 0;

    int rowCount = 0;
    
    FileWriter fwriter = null;

    // The following variable will reference an object
    // needed to open a file.
    PrintWriter outputFile;

    String fileName = "DonorData.txt";

    // Scanner reference variable.
    private Scanner input;
    
    // Create DecimalFormat objects to format dollar and percent output.
    DecimalFormat dollar = new DecimalFormat("$#,##0.00");

    /**
     * Constructor
     *
     * @throws java.io.FileNotFoundException
     */
    public FundraiserProgram() throws FileNotFoundException {

        //Create and set up the window.
        JFrame frame = new JFrame("Charity Donation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
        try {
            fwriter = new FileWriter(fileName, true);
        } catch (IOException ex) {
            Logger.getLogger(FundraiserProgram.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Test for exceptions.
            try {
                // Create file scanner.
                input = new Scanner(new File(fileName));

                for (int count = 1; count <= rowCount; count++) {
                    input.nextLine();
                    input.nextLine();
                    input.nextLine();
                }

                // Display all available data.
                while (input.hasNext()) {

                    TablePanel.tableModel.setRowCount(donorCount + 1);
                    TablePanel.tableModel.setValueAt(input.nextLine(),
                            donorCount, 0);
                    TablePanel.tableModel.setValueAt(input.nextLine(),
                            donorCount, 1);
                    TablePanel.tableModel.setValueAt(dollar.format(input.nextDouble()),
                            donorCount, 2);
                    
                    input.nextLine();

                    // Increment donorCount
                    donorCount++;
                    rowCount++;
                }

            } catch (FileNotFoundException ex) {

                // Display error message.
                JOptionPane.showMessageDialog(null,
                        "File not found",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                // Exit the application.
                System.exit(0);
            } finally {

                // Close the file. 
                input.close();
            }
            
                // Create a named constant for the URL.
            // NOTE: This value is specific for Java DB.
            final String DB_URL = "jdbc:derby:DonorDB;create=true";

            try {
                // Create a connection to the database.
                Connection conn
                        = DriverManager.getConnection(DB_URL);

                // If the DB already exists, drop the tables.
                CreateDonorDB.dropTables(conn);

                // Build the Coffee table.
                CreateDonorDB.buildDonorTable(conn);

                // Close the connection.
                conn.close();
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
    }

    /**
     * @param pane
     */
    public void addComponentsToPane(Container pane) {

        donor = new DonorPanel(); // Reference the donor panel
        charity = new CharityPanel(); // Reference the charity panel
        table = new TablePanel(); // Reference the table panel

        // Create a new grid layout and reference variable for constraints.
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Donor panel placement.
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(10, 10, 10, 0);
        c.gridx = 0;
        c.gridy = 0;
        pane.add(donor, c);

        // Charity panel placement.
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 1;
        c.gridy = 0;
        pane.add(charity, c);

        // Call the buildButtonPanel method to
        // create the button panel.
        buildButtonPanel();

        // Button panel placement.
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(250, 10, 150, 0);
        c.gridx = 0;
        c.gridy = 2;
        pane.add(buttonPanel, c);

        // Tabel panel placement.
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 100;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10, 0, 0, 10);
        c.gridx = 1;
        c.gridwidth = 3;
        c.gridheight = 3;
        c.gridy = 2;
        pane.add(table, c);
    }

    /**
     * The buildButtonPanel method builds the button panel.
     */
    private void buildButtonPanel() {

        // Create a panel for the buttons.
        buttonPanel = new JPanel();

        // Create a new grid layout with three rows and one column.
        buttonPanel.setLayout(new GridLayout(3, 1));

        // Create panels for the buttons. 
        JPanel savepanel = new JPanel();
        JPanel dbpanel = new JPanel();
        JPanel exitpanel = new JPanel();

        // Position the button panels.
        savepanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        dbpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        exitpanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Create the buttons. 
        save = new JButton("Save to Database");
        db = new JButton("  Query Database ");
        exit = new JButton("     Exit Program    ");

        // Register the action listeners.
        save.addActionListener(new SaveButtonListener());
        db.addActionListener(new queryDBButtonListener());
        exit.addActionListener(new ExitButtonListener());

        // Add the buttons to the panels.
        savepanel.add(save);
        dbpanel.add(db);
        exitpanel.add(exit);

        // Add the panels to the button panel.
        buttonPanel.add(savepanel);
        buttonPanel.add(dbpanel);
        buttonPanel.add(exitpanel);
    }

    /**
     * Private inner class that handles the event when the user clicks the
     * Calculate button.
     */
    private class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Test the first name text field for input.
            if (DonorPanel.firstTextField.getText() == null
                    || DonorPanel.firstTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter First Name.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                // Test the last name text field for input.    
            } else if (DonorPanel.lastTextField.getText() == null
                    || DonorPanel.lastTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter Last Name.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                // Test the pledge text field for input.
            } else if (DonorPanel.pledgeTextField.getText() == null
                    || DonorPanel.pledgeTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter Pledge Amount.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                // Test pledge amount for proper input... 
                // passed from exception handling in DonorPanel.java
            } else if (DonorPanel.getPledgeAmount() <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter a valid pledge "
                        + "amount.", "Error",
                        JOptionPane.ERROR_MESSAGE);

                // Test the combobox for input.
            } else if (CharityPanel.getCharity() == null
                    || CharityPanel.getCharity().equals("")) {
                JOptionPane.showMessageDialog(null, "Please select a charity.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);

                // If no issues are found, data is passed to the file.
            } else {
                try {

                    fwriter = new FileWriter(fileName, true);
                    PrintWriter outputFile = new PrintWriter(fwriter);

                    outputFile.println(DonorPanel.getDonor());
                    outputFile.println(CharityPanel.getCharity());
                    outputFile.println(DonorPanel.getPledgeAmount());
                    DonorPanel.firstTextField.setText(null);
                    DonorPanel.lastTextField.setText(null);
                    DonorPanel.pledgeTextField.setText(null);
                    CharityPanel.charityBox.setSelectedIndex(0);
                } catch (IOException ex) {
                    // Display error message.
                    JOptionPane.showMessageDialog(null,
                            "File not found",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);

                    // Exit the application.
                    System.exit(0);
                } finally {
                    try {
                        fwriter.close();
                    } catch (IOException ex) {
                        // Display error message.
                        JOptionPane.showMessageDialog(null,
                                "No file to close",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);

                        // Exit the application.
                        System.exit(0);
                    }
                }
                // Test for exceptions.
                try {
                    // Create file scanner.
                    input = new Scanner(new File(fileName));

                    for (int count = 1; count <= rowCount; count++) {
                        input.nextLine();
                        input.nextLine();
                        input.nextLine();
                    }

                    // Display all available data.
                    while (input.hasNext()) {

                        TablePanel.tableModel.setRowCount(donorCount + 1);
                        TablePanel.tableModel.setValueAt(input.nextLine(),
                                donorCount, 0);
                        TablePanel.tableModel.setValueAt(input.nextLine(),
                                donorCount, 1);
                        TablePanel.tableModel.setValueAt(dollar.format(input.nextDouble()),
                                donorCount, 2);
                        
                        input.nextLine();

                        // Increment donorCount
                        donorCount++;
                        rowCount++;
                    }

                } catch (FileNotFoundException ex) {

                    // Display error message.
                    JOptionPane.showMessageDialog(null,
                            "File not found",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);

                    // Exit the application.
                    System.exit(0);
                } finally {

                    // Close the file. 
                    input.close();
                }
                
                // Create a named constant for the URL.
            // NOTE: This value is specific for Java DB.
            final String DB_URL = "jdbc:derby:DonorDB;";

            try {
                // Create a connection to the database.
                Connection conn
                        = DriverManager.getConnection(DB_URL);

                // Build the Coffee table.
                CreateDonorDB.insertToDonorTable(conn);

                // Close the connection.
                conn.close();
            } catch (Exception ex) {
                System.out.println("ERROR: " + ex.getMessage());
            }
            }
        }
    }
    
    private class queryDBButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            DonorDBViewer dbv = new DonorDBViewer();
        }
    }

    private class ExitButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Exit the application.
            System.exit(0);
        }
    }

    /**
     *
     * @param args
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        FundraiserProgram fundraiserProgram = new FundraiserProgram();
    }
}
