package cookbook.objects;

import java.sql.Timestamp;
/**
 * The MessageObject class represents a message with its properties such as message ID, sender, receiver, recipe ID, message body, and creation timestamp.
 */
public class MessageObject {
  private String message_id;
  private String from_User;
  private String to_User;
  private String recipe_Id;
  private String body;
  private Timestamp created_at;

   /**
   * Constructs a new MessageObject with the specified message properties.
   *
   * @param message_id   the ID of the message
   * @param from_User    the sender of the message
   * @param to_User      the receiver of the message
   * @param recipe_Id    the ID of the associated recipe
   * @param body         the body content of the message
   * @param created_at   the timestamp when the message was created
   */

  public MessageObject(String message_id, String from_User, String to_User, String recipe_Id, String body,
      Timestamp created_at) {

    setMessage_id(message_id);
    setFrom_User(from_User);
    setTo_User(to_User);
    setRecipe_Id(recipe_Id);
    setBody(body);
    setCreated_at(created_at);
  }

  public String getMessage_id() {
    return message_id;
  }

  public void setMessage_id(String message_id) {
    this.message_id = message_id;
  }

  public String getFrom_User() {
    return from_User;
  }

  public void setFrom_User(String from_User) {
    this.from_User = from_User;
  }

  public String getTo_User() {
    return to_User;
  }

  public void setTo_User(String to_User) {
    this.to_User = to_User;
  }

  public String getRecipe_Id() {
    return recipe_Id;
  }

  public void setRecipe_Id(String recipe_Id) {
    this.recipe_Id = recipe_Id;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public Timestamp getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Timestamp created_at) {
    this.created_at = created_at;
  }

}
