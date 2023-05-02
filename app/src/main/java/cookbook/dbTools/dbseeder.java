package cookbook.dbTools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;




public class dbseeder {
  
  
  public void seed() {
    String dbUrl = "jdbc:mysql://localhost:3306/cookbook?user=root&password=root";
    
    try {
      // Read the JSON file
      String json = new String(Files.readAllBytes(Paths.get("seeder.json")));
      
      // Parse the JSON file
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> data = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
      
      // Insert the ingredients into the database
      Object ingredientsObj = data.get("ingredients");
      if (ingredientsObj instanceof List) {
        List<?> ingredientsList = (List<?>) ingredientsObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowObj : ingredientsList) {
            if (rowObj instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowObj;
              String sql = "INSERT INTO ingredients (ingredient_id, ingredient_name) VALUES (?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, (String) row.get("ingredient_id"));
                stmt.setString(2, (String) row.get("ingredient_name"));
                stmt.executeUpdate();
              }
            }
          }
        }
      }
      
      /* // Insert the recipe ingredients into the database
      Object recipeIngredientsObj = data.get("recipe_ingredients");
      if (recipeIngredientsObj instanceof List) {
        List<?> recipeIngredientsList = (List<?>) recipeIngredientsObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowObj : recipeIngredientsList) {
            if (rowObj instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowObj;
              String sql = "INSERT INTO recepie_ingredients (recepie_id, ingredient_id) VALUES (?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, (String) row.get("recepie_id"));
                stmt.setString(2, (String) row.get("ingredient_id"));
                stmt.executeUpdate();
              }
            }
          }
        }
      } */
    } catch (IOException | SQLException e) {
      // Handle the exception here
      e.printStackTrace();
    }
  }
  
}