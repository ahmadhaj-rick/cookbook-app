package cookbook.objectControllers;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import cookbook.objects.QuanitityIngredients;
import cookbook.objects.ScheduledRecipeObject;
import cookbook.objects.ingredientObject;
import cookbook.objects.userObject;

public class ScheduledRecipeController {
  static userObject currUser = userController.loggedInUser;
  
  // perform a join and get a new Scheduled recipe entity instance for a given

  /**
   * returns list of scheduled recipes for a given date.
   */
  public static List<ScheduledRecipeObject> getDateSchedule(Date date) throws SQLException {
    List<ScheduledRecipeObject> schedRecs = new ArrayList<>();
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    
    // Perform join and create SchedRecipeEntity

    //Ahmed can you check this?
    try (PreparedStatement preparedStmnt = conn.prepareStatement("""
    SELECT 
    w.week_date, w.recipe_id, w.user_id, r.name FROM weekly_list w 
    INNER JOIN recipe r ON r.recipe_id = w.recipe_id
    WHERE user_id = (?) AND week_date = (?); 
    ; 
    """)) {
      preparedStmnt.setString(1, currUser.getId());
      preparedStmnt.setDate(2, date);
      ResultSet result = preparedStmnt.executeQuery();
      
      // Create Entity instance
      while (result.next()) {
        String userId = result.getString("user_id");
        String recipeId = result.getString("recipe_id");
        date = result.getDate("week_date");
        ScheduledRecipeObject schedRec = new ScheduledRecipeObject(
        recipeId,
        result.getString("name"),
        userId,
        date);
        
        // Get all Quantified ingredients for the recipe
        PreparedStatement ingredientsStmnt = conn.prepareStatement(
        """
        SELECT * FROM ingredients i inner join recipe_ingredients ri on ri.ingredient_id = i.ingredient_id where ri.recipe_id = (?);
        """);
        ingredientsStmnt.setString(1, recipeId);
        ResultSet ingredientResult = ingredientsStmnt.executeQuery();
        
        while (ingredientResult.next()) {
          // Add all Quantified ingredients to the Scheduled Recipe Entity
          schedRec.addIngredient(new QuanitityIngredients(ingredientResult.getString("unit"),
          ingredientResult.getFloat("amount"), new ingredientObject(
          ingredientResult.getString("ingredient_id"), ingredientResult.getString("ingredient_name"))));
          
        }
        
        schedRecs.add(schedRec);
        
      }
    } catch (SQLException e) {
      System.out.println(e + "Sechdule error here 1111");
    }
    
    return schedRecs;
  }
}

