package cookbook.objects;

import java.sql.Timestamp;

public class MessageObject {
  private String id;
  private String from_User;
  private String to_User;
  private String recipe_Id;
  private String body;
  private Timestamp created_at;

  public MessageObject(String id, String from_User, String to_User, String recipe_Id, String body,
      Timestamp created_at) {

    setId(id);
    setFrom_User(from_User);
    setTo_User(to_User);
    setRecipe_Id(recipe_Id);
    setBody(body);
    setCreated_at(created_at);
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
