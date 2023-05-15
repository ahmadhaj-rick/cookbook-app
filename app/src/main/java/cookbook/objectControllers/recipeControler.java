package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
          result.getString("category"),
          result.getString("instructions"),
          result.getBoolean("star"));
        
        // this will upade the class ArrayList.
        currentRecipeObjects.add(newRecipe);

      }
      
      //Comment so iker can access it.
      // adding ingredients for each recipe.
      for (recipeObject recipeObject : currentRecipeObjects) {
        String id = recipeObject.getId();
        String ingQuery = "SELECT ingredients.ingredient_id, ingredients.ingredient_name " +
                          "FROM ingredients " +
                          "JOIN recipe_ingredients ON recipe_ingredients.ingredient_id = ingredients.ingredient_id " +
                          "WHERE recipe_ingredients.recipe_id = ?";
        try (PreparedStatement ingStatement = conn.prepareStatement(ingQuery)) {
          ingStatement.setString(1, id);
          ResultSet ingResultSet = ingStatement.executeQuery();
          while (ingResultSet.next()) {
            ingredientObject newIng = new ingredientObject(
              ingResultSet.getString("ingredient_id"),
              ingResultSet.getString("ingredient_name")
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

  public static void addRecipe(String uniqueID, String name, String description, String category, String instructions) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String query = "INSERT into recipe VALUES(?,?,?,?,?,?);";
    try(PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, uniqueID);
      sqlStatement.setString(2, name);
      sqlStatement.setString(3, description);
      sqlStatement.setString(4, category);
      sqlStatement.setString(5, instructions);
      sqlStatement.setBoolean(6, false);
      sqlStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

}
