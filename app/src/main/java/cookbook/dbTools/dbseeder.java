package cookbook.dbTools;

import java.io.File;
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
      File file = new File("src/main/java/cookbook/dbTools/seeding.json");
      if (file.exists()) {
        System.out.println("yes");
        String currdir = System.getProperty("user.dir");
        System.out.println(currdir);
      }else {
        System.out.println("No");
      }
      
      String json = new String(Files.readAllBytes(Paths.get("src/main/java/cookbook/dbTools/seeding.json")));
      
      // Parse the JSON file
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> data = mapper.readValue(json, new TypeReference<Map<String, Object>>() {});
      
      // Insert the users into the database
      Object userObj = data.get("user");
      if (userObj instanceof List) {
        List<?> userList = (List<?>) userObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowUserObj : userList) {
            if (rowUserObj instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowUserObj;
              String sqlUser = "INSERT IGNORE INTO user (user_id, fname, username, password, admin_id) VALUES (?, ?, ?, ?, ?)";
              try(PreparedStatement stmt = cnn.prepareStatement(sqlUser)) {
                stmt.setString(1, (String) row.get("user_id"));
                stmt.setString(2, (String) row.get("fname"));
                stmt.setString(3, (String) row.get("username"));
                stmt.setString(4, (String) row.get("password"));
                stmt.setBoolean(5, (Boolean) row.get("admin_id"));
                stmt.executeUpdate();
              } 
            }
          }
        }
      }
      
      // Insert the recipes into the database.
      Object recipeObj = data.get("recipe");
      if (recipeObj instanceof List) {
        List<?> recipeList = (List<?>) recipeObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowRecipe : recipeList) {
            if (rowRecipe instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowRecipe;
              String sqlRecipe = "INSERT IGNORE INTO recipe (recipe_id, name, description, category, instructions) VALUES (?, ?, ?, ?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sqlRecipe)) {
                stmt.setString(1, (String) row.get("recipe_id"));
                stmt.setString(2, (String) row.get("name"));
                stmt.setString(3, (String) row.get("description"));
                stmt.setString(4, (String) row.get("category"));
                stmt.setString(5, (String) row.get("instructions"));
                stmt.executeUpdate();
              }
            }
          }
        }
      }
      
      // Insert the ingredients into the database
      Object ingredientsObj = data.get("ingredients");
      if (ingredientsObj instanceof List) {
        List<?> ingredientsList = (List<?>) ingredientsObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowIngObj : ingredientsList) {
            if (rowIngObj instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowIngObj;
              String sqlIngred = "INSERT IGNORE INTO ingredients (ingredient_id, ingredient_name) VALUES (?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sqlIngred)) {
                stmt.setString(1, (String) row.get("ingredient_id"));
                stmt.setString(2, (String) row.get("ingredient_name"));
                stmt.executeUpdate();
              }
            }
          }
        }
      }
      
      
      // Insert the recipe ingredients into the database
      Object recipeIngredientsObj = data.get("recipe_ingredients");
      if (recipeIngredientsObj instanceof List) {
        List<?> tagList = (List<?>) recipeIngredientsObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowObj : tagList) {
            if (rowObj instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowObj;
              String sql = "INSERT IGNORE INTO recipe_ingredients (recipe_id, ingredient_id) VALUES (?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, (String) row.get("recipe_id"));
                stmt.setString(2, (String) row.get("ingredient_id"));
                stmt.executeUpdate();
              }
            }
          }
        }
      }


       // Insert the recipe ingredients into the database
       Object tag = data.get("tag");
       if (tag instanceof List) {
         List<?> recipeIngredientsList = (List<?>) tag;
         try (Connection cnn = DriverManager.getConnection(dbUrl)) {
           for (Object rowObj : recipeIngredientsList) {
             if (rowObj instanceof Map) {
               Map<?, ?> row = (Map<?, ?>) rowObj;
               String sql = "INSERT IGNORE INTO tag (tag_id, name) VALUES (?, ?)";
               try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                 stmt.setString(1, (String) row.get("tag_id"));
                 stmt.setString(2, (String) row.get("name"));
                 stmt.executeUpdate();
               }
             }
           }
         }
       }
      
      
    } catch (IOException | SQLException e) {
      // Handle the exception here
      e.printStackTrace();
    }
  }
  
}