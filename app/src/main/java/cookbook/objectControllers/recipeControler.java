package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cookbook.objects.ingredientObject;
import cookbook.objects.recipeObject;

public class recipeControler {
  
  // ArrayList with the current recipes
  public ArrayList<recipeObject> allRecipes = new ArrayList<>();

  public static List<recipeObject> getRecpies() throws SQLException {
    ArrayList<recipeObject> currentRecipeObjects = new ArrayList<>();
    String query = "SELECT * FROM recipe";

    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while(result.next()) {
        recipeObject newRecipe = new recipeObject(
          result.getString("recipe_id"),
          result.getString("name"),
          result.getString("description"),
          result.getInt("category"),
          result.getString("instructions"),
          result.getString("ingredient_id"),
          result.getBoolean("star"));
        
        // this will upade the class ArrayList.
        currentRecipeObjects.add(newRecipe);

      }

      // adding ingredients for each recipe.
      for (recipeObject recipeObject : currentRecipeObjects) {
        String id = recipeObject.getId();
        String ingQuery = "SELECT ingredients.ingredient_id, ingredients.ingredient_name " +
                          "FROM recepie " +
                          "JOIN recepie_ingredients ON recepie_ingredients.recepie_id = recepie.recepie_id " +
                          "JOIN ingredients ON recepie_ingredients.ingredient_id = ingredients.ingredient_id " +
                          "WHERE recepie.recepie_id = ?";
        try (PreparedStatement ingStatement = conn.prepareStatement(ingQuery)) {
          ingStatement.setString(1, id);
          ResultSet ingResultSet = ingStatement.executeQuery();
          while (ingResultSet.next()) {
            ingredientObject newIng = new ingredientObject(
              ingResultSet.getString("ingredient_id"),
              ingResultSet.getString("name_ingredient")
            );
            recipeObject.addIngredient(newIng);
          }

          // we can add tag to the object here.

        } catch (SQLException e) {
          System.out.println( "Adding ingredients query" + e);
        }
      }



      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return currentRecipeObjects;
  }

  
  

}
