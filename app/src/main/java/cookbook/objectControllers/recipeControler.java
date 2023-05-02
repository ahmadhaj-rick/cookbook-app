package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cookbook.objects.recipeObject;

public class recipeControler {
  
  public List<recipeObject> getRecpies() throws SQLException {

    String query = "SELECT recepie.recepie_id, recepie.name, recepie.description, recepie.category, recepie.instructions, ingredients.ingredient_name
    FROM recepie
    JOIN recepie_ingredients ON recepie_ingredients.recepie_id = recepie.recepie_id
    JOIN ingredients ON recepie_ingredients.ingredient_id = ingredients.ingredient_id
    WHERE recepie.recepie_id = <your_recipe_id>;";
    List<recipeObject> allRecipes = new ArrayList<>();

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

        allRecipes.add(newRecipe);
      }
      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return allRecipes;
  }


  

}
