package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import cookbook.objects.MessageObject;

public class MessageController {

  // send a message from one user to another.
/**
 * method to send messages.
 */
  public static void sendMessage(String from_User, String to_User, String recipe_Id, String body,String time, Timestamp created_at) throws SQLException {

    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    // Insert message into db.
    String query = "INSERT INTO message  VALUES (?, ?, ?, ?, ?, ?)";

    // Generate a unique ID for the message.
    UUID uniqueID = UUID.randomUUID();
    String messageID = uniqueID.toString();

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, messageID);
      sqlStatement.setString(2, from_User);
      sqlStatement.setString(3, to_User);
      sqlStatement.setString(4, recipe_Id);
      sqlStatement.setString(5, body);
      sqlStatement.setTimestamp(6, created_at);

      sqlStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  // Get Messages by user from one user to other user.

  /**
   * gets message between one user and another.
   */
  public static List<MessageObject> getMessages(String from_User, String to_User) throws SQLException {

    List<MessageObject> messages = new ArrayList<>();
    String query = "SELECT * FROM message WHERE from_user = ? AND to_user = ?";

    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, from_User);
      sqlStatement.setString(2, to_User);

      ResultSet result = sqlStatement.executeQuery();

      while (result.next()) {
        String Id = result.getString("message_id");
        MessageObject message = new MessageObject(
          Id,
            result.getString("from_User"),
            result.getString("to_User"),
            result.getString("recipe_Id"),
            result.getString("body"),
            result.getTimestamp("created_at"));

        messages.add(message);
      }

      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }

    return messages;
  }

  // display the name of the user.

  /**
   * gets the name of a specific user.
   */
  public static String getName(String user_id) throws SQLException {
    String query = "SELECT name FROM user WHERE id = ?";

    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, user_id);

      ResultSet result = sqlStatement.executeQuery();

      if (result.next()) {
        return result.getString("name");
      }

      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }

    return null;
  }

  // Retrieve all messages sent by a user based on their ID.

  /**
   * retrieves a list of messages sent by a user.
   */

  public static List<MessageObject> getMessagesByUserId(String user_id) throws SQLException {
    List<MessageObject> messages = new ArrayList<>();
    String query = "SELECT * FROM message WHERE from_user = ?";

    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, user_id);

      ResultSet result = sqlStatement.executeQuery();

      while (result.next()) {
        String Id = result.getString("message_id");
        MessageObject message = new MessageObject(
          Id,
            result.getString("from_User"),
            result.getString("to_User"),
            result.getString("recipe_Id"),
            result.getString("body"),
            result.getTimestamp("created_at"));

        messages.add(message);
      }

      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }

    return messages;
  }

  // Get the name of a recipe based on its ID.

  /**
   * gets the name of a recipe.
   */
  public static String getRecipeName(String userId) throws SQLException {
    String query = "SELECT name FROM recipe WHERE recipe_id = ?";
    String RecipeName = null;
    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, userId);

      ResultSet result = sqlStatement.executeQuery();

      if (result.next()) {
        return result.getString("name");
      }

      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }

    return RecipeName;
  }

  // Retrieve all messages sent to or from a user based on their ID.

  /**
   * gets all messages sent from one user to another.
   */
  public static List<MessageObject> getAllMessagesByUserId(String from_user_ID, String to_user_Id) throws SQLException {
    List<MessageObject> messages = new ArrayList<>();
    String query = "SELECT * FROM message WHERE from_user = ? OR to_user = ?";

    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, from_user_ID);
      sqlStatement.setString(2, to_user_Id);

      ResultSet result = sqlStatement.executeQuery();

      while (result.next()) {
        String Id = result.getString("message_id");
        MessageObject message = new MessageObject(
          Id,
            result.getString("from_user"),
            result.getString("to_user"),
            result.getString("recipe_id"),
            result.getString("body"),
            result.getTimestamp("created_at"));

        messages.add(message);
      }

      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }

    return messages;
  }

}