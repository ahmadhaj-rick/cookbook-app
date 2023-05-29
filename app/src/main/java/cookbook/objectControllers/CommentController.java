package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The CommentController class provides methods for adding comments to a database.
 */

public class CommentController {

  /**
   * Adds a comment to the database.
   *
   * @param ID       the ID of the comment
   * @param text     the text of the comment
   * @param userID   the ID of the user associated with the comment
   * @param recipeID the ID of the recipe associated with the comment
   * @throws SQLException if a database error occurs
   */
  
  public static void addComment(String ID, String text, String userID, String recipeID) throws SQLException {
    //method implementation
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

  /**
   * Deletes a comment from the database.
   *
   * @param userID    the ID of the user who owns the comment
   * @param commentID the ID of the comment to be deleted
   * @throws SQLException if a database error occurs
   */
  
  public static void deleteComment(String userID, String commentID) throws SQLException{
    String query = "DELETE FROM comment WHERE user_id=(?) AND comment_id=(?)";
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, userID);
      sqlStatement.setString(2, commentID);

      sqlStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Error deleting comment." + e);
    }
  }
    /**
   * Edits the text of a comment in the database.
   *
   * @param id   the ID of the comment to be edited
   * @param text the updated text of the comment
   * @throws SQLException if a database error occurs
   */
  public static void editComment(String id, String text) throws SQLException {
    String query = "UPDATE comment SET text=(?) WHERE comment_id=(?)";
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, text);
      sqlStatement.setString(2, id);
      sqlStatement.executeUpdate(); //so it shows
    } catch (SQLException e) {
      System.out.println("Error editing comment," + e);
    }
  }
  
}
