package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.*;

import cookbook.objects.CommentObject;
import cookbook.objects.QuanitityIngredients;
import cookbook.objects.ingredientObject;
import cookbook.objects.recipeObject;
import cookbook.objects.tagObject;
import cookbook.objects.userObject;

public class recipeControler {

  // ArrayList with the current recipes
  public ArrayList<recipeObject> allRecipes = new ArrayList<>();

  /**
   * returns a list of all recipes.
   */
  public static List<recipeObject> getRecpies() throws SQLException {
    ArrayList<recipeObject> currentRecipeObjects = new ArrayList<>();
    String query = "SELECT * FROM recipe";

    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while (result.next()) {
        recipeObject newRecipe = new recipeObject(
            result.getString("recipe_id"),
            result.getString("name"),
            result.getString("description"),
            result.getString("instructions"),
            result.getBoolean("star"));

        // this will upade the class ArrayList.
        currentRecipeObjects.add(newRecipe);

      }

      // Comment so iker can access it.
      // adding ingredients for each recipe.
      for (recipeObject recipeObject : currentRecipeObjects) {
        String id = recipeObject.getId();
        String ingQuery = "SELECT * " +
                          "FROM ingredients " +
                          "JOIN recipe_ingredients ON recipe_ingredients.ingredient_id = ingredients.ingredient_id " +
                          "WHERE recipe_ingredients.recipe_id = ?";
        try (PreparedStatement ingStatement = conn.prepareStatement(ingQuery)) {
          ingStatement.setString(1, id);
          ResultSet ingResultSet = ingStatement.executeQuery();
          System.out.println(ingResultSet);
          while (ingResultSet.next()) {
            System.out.println("ingredient_name: " + ingResultSet.getString("ingredient_name"));
            System.out.println("ingredient_id: " + ingResultSet.getString("ingredient_id"));
            recipeObject.addIngredient(new QuanitityIngredients(
              ingResultSet.getString("unit"),
              ingResultSet.getFloat("amount"),
              new ingredientObject(ingResultSet.getString("ingredient_id"), ingResultSet.getString("ingredient_name")))
            );
          }

          String commentQuery = "SELECT * " + 
              "FROM comment " +
              "JOIN recipe r ON r.recipe_id = comment.recipe_id " +
              "WHERE r.recipe_id = ?";
          try (PreparedStatement commentStatement = conn.prepareStatement(commentQuery)) {
            commentStatement.setString(1, id);
            ResultSet commentResult = commentStatement.executeQuery();
            while (commentResult.next()) {
              System.out.println("commentID: " + commentResult.getString("comment_id"));
              System.out.println("commentText: " + commentResult.getString("text"));
              CommentObject comment = new CommentObject(
                commentResult.getString("comment_id"),
                commentResult.getString("text"),
                commentResult.getString("user_id"),
                commentResult.getString("recipe_id"));
              recipeObject.addComment(comment);
              
            }
          } catch (SQLException e) {
            System.out.println("Error adding comment to recipe via recipeController" + e);
          }


          // we can add tag to the object here.

          String tagQuery = "SELECT tag.tag_id, tag.name " +
              "FROM tag " +
              "JOIN recipe_tag ON recipe_tag.tag_id = tag.tag_id " +
              "WHERE recipe_tag.recipe_id = ?";
          try (PreparedStatement tagtatement = conn.prepareStatement(tagQuery)) {
            tagtatement.setString(1, id);
            ResultSet tagResultSet = tagtatement.executeQuery();
            while (tagResultSet.next()) {
              tagObject newTag = new tagObject(
                  tagResultSet.getString("tag_id"),
                  tagResultSet.getString("name"));
              recipeObject.addTag(newTag);
            }

          } catch (SQLException e) {
            System.out.println("Error adding tag: " + e);
          }

        } catch (SQLException e) {
          System.out.println("Adding ingredients query" + e);
        }
      }

      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return currentRecipeObjects;
  }

  /**
   * updates favorite recipe of the logged in user.
   */
  public boolean updateFavoriteStatus(recipeObject selectedRecipeObject) throws SQLException {
    userObject loggedUser = userController.loggedInUser;
    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
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

  /**
   * returns list of favorite recipes of the logged user.
   * @return
   * @throws SQLException
   */
  public static List<recipeObject> favoriteObjects() throws SQLException {
    List<recipeObject> favoriteRecipies = new ArrayList<>();
    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    userObject loggedInUser = userController.loggedInUser;
    String user_id = loggedInUser.getId();

    String searchQuery = "SELECT * FROM Recipe "
        + "JOIN starred ON starred.recipe_id = recipe.recipe_id "
        + "JOIN user ON user.user_id = starred.user_id "
        + "WHERE user.user_id =(?)";

    try (PreparedStatement SQLstatement = conn.prepareStatement(searchQuery)) {
      SQLstatement.setString(1, user_id);
      ResultSet result = SQLstatement.executeQuery();

      while (result.next()) {
        recipeObject faveRecipe = new recipeObject(
            result.getString("recipe_id"),
            result.getString("name"),
            result.getString("description"),
            result.getString("instructions"),
            result.getBoolean("star"));

        favoriteRecipies.add(faveRecipe);
      }

      for (recipeObject recipeObject : favoriteRecipies) {
        String id = recipeObject.getId();
        String ingQuery = "SELECT * " +
                          "FROM ingredients " +
                          "JOIN recipe_ingredients ON recipe_ingredients.ingredient_id = ingredients.ingredient_id " +
                          "WHERE recipe_ingredients.recipe_id = ?";
        try (PreparedStatement ingStatement = conn.prepareStatement(ingQuery)) {
          ingStatement.setString(1, id);
          ResultSet ingResultSet = ingStatement.executeQuery();
          while (ingResultSet.next()) {
            recipeObject.addIngredient(new QuanitityIngredients(
              ingResultSet.getString("unit"),
              ingResultSet.getFloat("amount"),
              new ingredientObject(ingResultSet.getString("ingredient_id"), ingResultSet.getString("ingredient_name")))
            );
          }

          // we can add tag to the object here.

        } catch (SQLException e) {
          System.out.println("Adding ingredients query" + e);
        }
      }

      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }

    return favoriteRecipies;
  }

  /**
   * adds the date to the recipe for weekly list.
   */
  public static void adddate(String recipeId, Timestamp created_at , String userId) throws SQLException {


    String query = "INSERT into weekly_list values ((?),(?),(?))";
    Connection conn = DriverManager
            .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    try (PreparedStatement preparedStmnt = conn.prepareStatement(query)) {
      preparedStmnt.setString(1, userId);
      preparedStmnt.setString(2, recipeId);
      preparedStmnt.setString(3, String.valueOf(created_at));
      preparedStmnt.executeUpdate();

    } catch (SQLException e) {
      if (e instanceof SQLIntegrityConstraintViolationException) {
        return;
      } else {
        e.printStackTrace();
      }
    }

  }

/**
 * adds new recipe.
 */
  public static void addRecipe(String recipeId, String recipeName, String shortDesc, String longDesc) throws SQLException{
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String query = "INSERT INTO recipe VALUES(?,?,?,?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, recipeId);
      sqlStatement.setString(2, recipeName);
      sqlStatement.setString(3, shortDesc);
      sqlStatement.setString(4, longDesc);
      sqlStatement.setBoolean(5, false);
      sqlStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println("lala" + e);
    }
  }
}
