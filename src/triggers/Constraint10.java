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
public class Constraint10 {
    
    public static void checkConstraint10(Integer taskid, Integer original, String doctype) throws SQLException
    {
        if(original!=null)  //if original is null then it means that the  added document is original and other constraints will apply
        {
         
                Connection conn = DriverManager.getConnection("jdbc:default:connection");
                PreparedStatement fetchRows = conn.prepareStatement("SELECT DOCUMENTID FROM DOCUMENTS WHERE TASKID=? AND DOCTYPENAME=? AND ORIGINALDOCID<>?");
                fetchRows.setInt(1, taskid);
                fetchRows.setString(2, doctype);
                fetchRows.setInt(3, original);
                ResultSet result = fetchRows.executeQuery();
                if(result.next())   //if there is at least one record then it means that there is another document that refers to the same type of document in the same task
                {
                    
                    System.out.println("CAN'T Insert, there is already another document of this type in this type. You can only have one type of document in a Task");
                    throw new SQLException("CAN'T Insert this document, there is already another document of this type in the same Task."
                            + " You can only have one type of document in a paticular Task");
                } 
            
        }
    }
}
