package cookbook.objectControllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

public class MessageController {

  // send a message from one user to another.

  public static void sendMessage(String id, String from_User, String to_User, String recipe_Id, String body,
  Timestamp created_at){


    // Generate a unique ID for the message.
    UUID uniqueID = UUID.randomUUID();
    String messageID = uniqueID.toString();
    
  }



}
