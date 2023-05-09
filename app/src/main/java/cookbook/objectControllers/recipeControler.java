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
import cookbook.objects.userObject;

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

  
  public boolean updateFavoriteStatus(recipeObject selectedRecipeObject) throws SQLException {
    userObject loggedUser = userController.loggedInUser;
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String sqlQuery = "SELECT * FROM starred WHERE user_id=(?) AND recipe_id=(?); ";
    String user_id = loggedUser.getId();
    String recipe_id = selectedRecipeObject.getId();
    try (PreparedStatement sqlStatement = conn.prepareStatement(sqlQuery)) {
      sqlStatement.setString(1, user_id);
      sqlStatement.setString(2, recipe_id);
      ResultSet result = sqlStatement.executeQuery();

      if (result.next()) {
        String deleteDuplicate = "DELETE FROM starred WHERE user_id=(?) AND recipe_id=(?); ";
        try (PreparedStatement sqlStatement2 = conn.prepareStatement(deleteDuplicate)) {
          sqlStatement2.setString(1, user_id);
          sqlStatement2.setString(2, recipe_id);
          sqlStatement2.executeUpdate();
          selectedRecipeObject.setStar(false);
          return true;
        } catch (SQLException e) {
          System.out.println(e + "Delete catch");
        }
      } else {
        String insertStarred = "INSERT INTO starred (user_id, recipe_id) VALUES(?,?); ";
        try (PreparedStatement sqlStatement2 = conn.prepareStatement(insertStarred)) {
          sqlStatement2.setString(1, user_id);
          sqlStatement2.setString(2, recipe_id);
          sqlStatement2.executeUpdate();
          selectedRecipeObject.setStar(true);
          return true;
        } catch (SQLException e) {
          System.out.println(e + "Add catch");
        }
      }
    } catch (SQLException e) {
      System.out.println(e + "Main SQL catch");
    }
    return false;
  }


  public static List<recipeObject> favoriteObjects() throws SQLException {
    List<recipeObject> favoriteRecipies = new ArrayList<>();
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    userObject loggedInUser = userController.loggedInUser;
    String user_id = loggedInUser.getId();

    String searchQuery = "SELECT * FROM Recipe R "
          + "JOIN starred S ON S.recipe_id = R.recipe_id "
          + "JOIN starred S ON S.user_id = user.user_id "
          + "WHERE user.user_id =(?)";

    try (PreparedStatement SQLstatement = conn.prepareStatement(searchQuery)) {
      SQLstatement.setString(1, user_id);
      ResultSet result = SQLstatement.executeQuery();

      while (result.next()) {
        recipeObject faveRecipe = new recipeObject(
          result.getString("recipe_id"),
          result.getString("name"),
          result.getString("description"),
          result.getString("category"),
          result.getString("instructions"),
          result.getBoolean("star"));

          favoriteRecipies.add(faveRecipe);
        }
    

      for (recipeObject recipeObject : favoriteRecipies) {
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

    return favoriteRecipies;
  }

}
