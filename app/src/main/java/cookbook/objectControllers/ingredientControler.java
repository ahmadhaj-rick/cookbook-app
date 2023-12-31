package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.ResultSet;

import cookbook.objects.ingredientObject;

/**
 * The ingredientControler class provides methods for retrieving ingredients from a database.
 */

public class ingredientControler {

/**
  * Retrieves a list of ingredients from the database.
  *
  * @return a list of ingredientObject representing the ingredients
  * @throws SQLException if a database error occurs
  */
  
  public static List<ingredientObject> getIngredients() throws SQLException {
    ArrayList<ingredientObject> currentIngredients = new ArrayList<>();
    String query = "SELECT * FROM ingredients";
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    try (PreparedStatement sqlstatement = conn.prepareStatement(query)) {
      ResultSet result = sqlstatement.executeQuery();
      while(result.next()) {
        ingredientObject newIngredient = new ingredientObject(
        result.getString("ingredient_id"),
        result.getString("ingredient_name"));
        currentIngredients.add(newIngredient);
      }
      result.close();
    }
    catch (SQLException e) {
      System.out.println(e);
    } 
    return currentIngredients;
  }
  
  /**
   * Adds an ingredient to the database.
   *
   * @param uniqueID the unique ID of the ingredient
   * @param name     the name of the ingredient
   * @throws SQLException if a database error occurs
   */

  public static void addIngredient(String uniqueID, String name) throws SQLException{
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String query = "INSERT INTO ingredients VALUES(?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, uniqueID);
      sqlStatement.setString(2, name);
      sqlStatement.executeUpdate();
    }
    catch (SQLException e) {
      System.out.println(e);
    }
  }

  /**
   * Adds an ingredient to a recipe in the database.
   *
   * @param recipeID     the ID of the recipe
   * @param ingredientID the ID of the ingredient
   * @param unit         the unit of measurement for the ingredient
   * @param amount       the amount of the ingredient
   * @throws SQLException if a database error occurs
   */
 
  
  public static void addIngredientToRecipe(String recipeID, String ingredientID, String unit, Float amount) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String query = "INSERT INTO recipe_ingredients VALUES (?,?,?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, recipeID);
      sqlStatement.setString(2, ingredientID);
      sqlStatement.setString(3, unit);
      sqlStatement.setFloat(4, amount);
      sqlStatement.executeUpdate();
      
      //int rowsAffected = sqlStatement.executeUpdate();
      //System.out.println(rowsAffected + " rows affected");

    } catch (SQLException e) {
      System.out.println("Heres the error" + e);
    }
  }

}
