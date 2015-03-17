
/**
 * Connect to a Database Learning Team B PRG/421 February 23, 2015 Roland Morales
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;   // Needed for JDBC classes
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This program creates the CoffeeDB database.
 */
public class CreateDonorDB {
    
    static String fileName = "DonorData.txt";
    
    // Scanner reference variable.
    private static Scanner input;
    
    static int rowCount = 0;
    
    // Create DecimalFormat objects to format dollar and percent output.
    static DecimalFormat dollar = new DecimalFormat("$#,##0.00");
    

    /**
     * The dropTables method drops any existing in case the database already
     * exists.
     */
    public static void dropTables(Connection conn) {
        System.out.println("Checking for existing tables.");

        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();;

            try {
                // Drop the UnpaidOrder table.
                stmt.execute("DROP TABLE Donor");
                System.out.println("Donor table dropped.");
            } catch (SQLException ex) {
				// No need to report an error.
                // The table simply did not exist.
            }
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * The buildCoffeeTable method creates the Coffee table and adds some rows
     * to it.
     */
    public static void buildDonorTable(Connection conn) {
        
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();

            // Create the table.
            stmt.execute("CREATE TABLE Donor ("
                    + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 11111, INCREMENT BY 1),"
                    + "DonorName CHAR(25), "
                    + "Charity CHAR(20), "
                    + "PledgeAmount CHAR (15), "
                    + "CONSTRAINT primary_key PRIMARY KEY (id) "
                    + ")");

            try {
                // Create file scanner.
                input = new Scanner(new File(fileName));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CreateDonorDB.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            while (input.hasNext()) {
                
                String name = input.nextLine();
                String dnr = input.nextLine();
                String pldg = input.nextLine();
                
                double dPldg = Double.parseDouble(pldg);
                
            // Insert row #1.
            stmt.execute("INSERT INTO Donor(DonorName , Charity, PledgeAmount) "
                    + "VALUES ('"+name+"', '"+dnr+"', '"+dollar.format(dPldg)+"')");
            
            rowCount ++;
            }

            System.out.println("Donor table created.");
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
    public static void insertToDonorTable(Connection conn) {
        
        try {
            // Get a Statement object.
            Statement stmt = conn.createStatement();
            
            try {
                // Create file scanner.
                input = new Scanner(new File(fileName));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CreateDonorDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            for (int count = 1; count <= rowCount; count++) {
                        input.nextLine();
                        input.nextLine();
                        input.nextLine();
                    }
                
            while (input.hasNext()) {
                
                String name = input.nextLine();
                String dnr = input.nextLine();
                String pldg = input.nextLine();
                
                double dPldg = Double.parseDouble(pldg);
                
            // Insert row #1.
            stmt.execute("INSERT INTO Donor(DonorName , Charity, PledgeAmount) "
                    + "VALUES ('"+name+"', '"+dnr+"', '"+dollar.format(dPldg)+"')");
            }

            System.out.println("Line(s) added to Donor table.");
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
