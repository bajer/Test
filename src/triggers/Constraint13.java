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
public class Constraint13 {
    public static void checkConstraint13(Integer taskid, Integer original) throws SQLException
    {
        if(original!=null)      //again checking if the added document is not an original itself if it is no point checking
        {
            
         
                Connection conn = DriverManager.getConnection("jdbc:default:connection");
                PreparedStatement fetchRows = conn.prepareStatement("SELECT TASKID FROM DOCUMENTS WHERE DOCUMENTID=?");
                fetchRows.setInt(1, original);
                ResultSet result = fetchRows.executeQuery();
                if(result.next())   //if there is at least one record then it means that there is another document that refers to the same type of document in the same task
                {
                    if(result.getInt("TASKID")!=taskid)
                    {
                    throw new SQLException("Can't insert this document. The Task of the original document this document referes to is different to the Task of this document");
                    }
                } 
            
        }
    }
}
