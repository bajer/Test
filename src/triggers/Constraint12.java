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
public class Constraint12 {
    public static void checkConstraint12(Integer taskid, Integer original, String doctype) throws SQLException
    {
        if(original!=null)      //again checking if the added document is not an original itself if it is no point checking
        {
            
         
                Connection conn = DriverManager.getConnection("jdbc:default:connection");
                PreparedStatement fetchRows = conn.prepareStatement("SELECT DOCTYPENAME FROM DOCUMENTS WHERE TASKID=? AND ORIGINALDOCID=?");
                fetchRows.setInt(1, taskid);
                fetchRows.setInt(2, original);
                ResultSet result = fetchRows.executeQuery();
                if(result.next())   //if there is at least one record then it means that there is another document that refers to the same type of document in the same task
                {
                    if(!result.getString("DOCTYPENAME").equals(doctype))
                    {
                    throw new SQLException("Can't insert this document. Incorrect type. The document type of the revised document must be the same as its original");
                    }
                } 
            
        }
    }
}
