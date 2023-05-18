package cookbook.objects;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ScheduledRecipeObject {
  private String recipeId;
  private String recipeName;
  private String userId;
  private Date date;
  private List<QuanitityIngredients> Quanitityingredients = new ArrayList<>();

  public ScheduledRecipeObject(String recipeId, String recipeName, String userId, Date date) {
    this.recipeId = recipeId;
    this.recipeName = recipeName;
    this.userId = userId;
    this.date = date;
  }

  public String getRecipeId() {
    return recipeId;
  }

  public String getRecipeName() {
    return recipeName;
  }

  public String getUserId() {
    return userId;
  }

  public Date getDate() {
    return date;
  }

  public void addIngredient(QuanitityIngredients ingredient) {
    this.Quanitityingredients.add(ingredient);
  }

  public List<QuanitityIngredients> getIngredients() {
    return Quanitityingredients;
  }

  @Override
  public String toString() {
    return "ScheduledRecipeEntity{" +
            "recipeId='" + recipeId + '\'' +
            ", recipeName='" + recipeName + '\'' +
            ", userId='" + userId + '\'' +
            ", date=" + date +
            ", ingredients=" + Quanitityingredients +
            '}';
  }
}

