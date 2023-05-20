package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class MessageController {

  // send a message from one user to another.

  public static void sendMessage(String id, String from_User, String to_User, String recipe_Id, String body,
      Timestamp created_at) throws SQLException {

    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    // Insert message into db.
    String query = "INSERT INTO messages (id, from_user, to_user, recipe_id, body, created_at) VALUES (?, ?, ?, ?, ?, ?)";

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

}
