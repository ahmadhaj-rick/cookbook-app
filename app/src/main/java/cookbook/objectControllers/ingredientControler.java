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

public class ingredientControler {
  
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
 
  public static void addIngredientToRecipe(String recipeID, String ingredientID) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String query = "INSERT INTO recipe_ingredients VALUES (?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, recipeID);
      sqlStatement.setString(2, ingredientID);
      sqlStatement.execute();
      
      int rowsAffected = sqlStatement.executeUpdate();
      System.out.println(rowsAffected + " rows affected");

    } catch (SQLException e) {
      System.out.println(e);
    }
  }

}
