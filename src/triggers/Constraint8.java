/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package triggers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Wojtek
 */
public class Constraint8 {      //not neccessary as the referential constraint precents a document form being deleted if there are references to it via foreign key

    

    public static void delete_original(Integer docID, Integer original) {
        System.out.println("docid = " + docID + " originalID = " + original);
        if (original == null) {
           
            try {
                Connection conn = DriverManager.getConnection("jdbc:default:connection");
                PreparedStatement fetchRows = conn.prepareStatement("SELECT * FROM DOCUMENTS WHERE ORIGINALDOCID=?");
                fetchRows.setInt(1, docID);
                ResultSet result = fetchRows.executeQuery();
                if(result.next())
                {
                    System.out.println("CAN'T Delete");
                } 
            } catch (SQLException ex) {
                System.out.println("Error connecting to the database on the derby side " + ex.getLocalizedMessage());
            }
        }
    }
}
