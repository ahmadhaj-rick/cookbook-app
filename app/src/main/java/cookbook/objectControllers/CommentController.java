package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CommentController {
    
    public static void addComment(String ID, String text, String userID, String recipeID) throws SQLException {
        String query = "INSERT INTO comment VALUES (?,?,?,?)";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
        try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
          sqlStatement.setString(1, ID);
          sqlStatement.setString(2, text);
          sqlStatement.setString(3, userID);
          sqlStatement.setString(4, recipeID);
          sqlStatement.executeUpdate();
        } catch (SQLException e) {
          System.out.println("Error adding comment," + e);
        }
      }
    

      public void deleteComment(String id) throws SQLException{
        String query = "DELETE FROM comment WHERE id=(?)";
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
        try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
          sqlStatement.setString(1, id);
          sqlStatement.executeUpdate();
        } catch (SQLException e) {
          System.out.println("Error deleting comment." + e);
        }
    
      }
    
}
