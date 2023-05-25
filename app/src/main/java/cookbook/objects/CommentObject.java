package cookbook.objects;

public class CommentObject {
  private String id;
  private String text;
  private String userID;
  private String recipeID;
  

  public CommentObject(String id, String text, String userID, String recipeID) {
    setId(id);
    setText(text);
    setUserId(userID);
    setRecipeId(recipeID);
  }

  private void setId(String ID) {
    this.id = ID;
  }

  public String getID() {
    return id;
  }

  private void setText(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }

  private void setUserId(String userID) {
    this.userID = userID;
  }

  public String getUserId() {
    return userID;
  }

  private void setRecipeId(String recipeID) {
    this.recipeID = recipeID;
  }

  public String getRecipeID() {
    return recipeID;
  }

  public void updateText(String text) {
    setText(text);
  }



}
