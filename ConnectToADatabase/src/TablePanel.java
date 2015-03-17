
/**
 * Connect to a Database Learning Team B PRG/421 February 23, 2015 Roland Morales
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The TablePanel class allows the user input.
 */
public class TablePanel extends JPanel {

    public static JTextField donorname = new JTextField(8); // Text field for Donar Name.
    public static JTextField charityname = new JTextField(10); // Text field for Charity Name.
    public static JTextField pledgeamount = new JTextField(5); // Text field for Amount Pledged.
    
    public static DefaultTableModel tableModel;

    int donorCount = 0;

    /**
     * Constructor
     */
    public TablePanel() {

        // Input components Panel
        JPanel inputSectionPanel = new JPanel();
        JLabel donornameLb = new JLabel("Name Of Donor");
        JLabel charitynameLb = new JLabel("Name of Charity");
        JLabel pledgeamountLb = new JLabel("Pledge Amount");

        inputSectionPanel.setLayout(new BoxLayout(inputSectionPanel,
                BoxLayout.Y_AXIS));
        inputSectionPanel.add(donornameLb);
        inputSectionPanel.add(donorname);
        inputSectionPanel.add(charitynameLb);
        inputSectionPanel.add(charityname);
        inputSectionPanel.add(pledgeamountLb);
        inputSectionPanel.add(pledgeamount);

        JTable saveTable = new JTable();
        
        // Create Container to hold the table and buttons
        Container tableButtonContainer = new Container();
        tableButtonContainer.setLayout(new BorderLayout());
        tableButtonContainer.setLayout(new BoxLayout(tableButtonContainer,
                BoxLayout.Y_AXIS));

        // Create table model having 3 columns
        tableModel = new DefaultTableModel(0, 3);

        // Set column headers
        String col[] = {"Donor Name", "Charity Name", "Pledge Amount"};
        tableModel.setColumnIdentifiers(col);
        saveTable.setModel(tableModel);

        // Create JScrollPane to contain the table
        JScrollPane tableScroller = new JScrollPane(saveTable,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        tableScroller.setViewportView(saveTable);
        tableButtonContainer.add(saveTable);

        tableButtonContainer.add(tableScroller);
        tableScroller.setViewportView(saveTable);

        add(tableButtonContainer);
    }
}
