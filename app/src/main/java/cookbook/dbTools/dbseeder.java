package cookbook.dbTools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
      } else {
        System.out.println("No");
      }
      
      String json = new String(Files.readAllBytes(Paths.get("src/main/java/cookbook/dbTools/seeding.json")));
      
      // Parse the JSON file
      ObjectMapper mapper = new ObjectMapper();
      Map<String, Object> data = mapper.readValue(json, new TypeReference<Map<String, Object>>() {
      });
      
      // Insert the users into the database
      Object userObj = data.get("user");
      if (userObj instanceof List) {
        List<?> userList = (List<?>) userObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowUserObj : userList) {
            if (rowUserObj instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowUserObj;
              String sqlUser = "INSERT IGNORE INTO user (user_id, fname, username, password, admin_id) VALUES (?, ?, ?, ?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sqlUser)) {
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
              String sqlRecipe = "INSERT IGNORE INTO recipe (recipe_id, name, description, instructions) VALUES (?, ?, ?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sqlRecipe)) {
                stmt.setString(1, (String) row.get("recipe_id"));
                stmt.setString(2, (String) row.get("name"));
                stmt.setString(3, (String) row.get("description"));
                stmt.setString(4, (String) row.get("instructions"));
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
              String sql = "INSERT IGNORE INTO recipe_ingredients (recipe_id, ingredient_id, unit, amount) VALUES (?, ?, ?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, (String) row.get("recipe_id"));
                stmt.setString(2, (String) row.get("ingredient_id"));
                stmt.setString(3, (String) row.get("unit"));
                stmt.setString(4, (String) row.get("amount"));
                stmt.executeUpdate();
              }
            }
          }
        }
      }
      
      // Insert the tags for each recipe into the database.
      Object tagObj = data.get("tag");
      if (tagObj instanceof List) {
        List<?> tagsList = (List<?>) tagObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowRecipe : tagsList) {
            if (rowRecipe instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowRecipe;
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
      
      // Insert the recipe tags into the database
      Object recipeTagsObj = data.get("recipe_tag");
      if (recipeTagsObj instanceof List) {
        List<?> recipeTagsList = (List<?>) recipeTagsObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowObj : recipeTagsList) {
            if (rowObj instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowObj;
              String sql = "INSERT IGNORE INTO recipe_tag (recipe_id, tag_id) VALUES (?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, (String) row.get("recipe_id"));
                stmt.setString(2, (String) row.get("tag_id"));
                stmt.executeUpdate();
              }
            }
          }
        }
      }
      
      // Insert the messages into the database
      Object messagesObj = data.get("message");
      if (messagesObj instanceof List) {
        List<?> messagesList = (List<?>) messagesObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowObj : messagesList) {
            if (rowObj instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowObj;
              String sql = "INSERT INTO message (message_id, from_user, to_user, recipe_id, body, created_at) VALUES (?, ?, ?, ?, ?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, (String) row.get("message_id"));
                stmt.setString(2, (String) row.get("from_user"));
                stmt.setString(3, (String) row.get("to_user"));
                stmt.setString(4, (String) row.get("recipe_id"));
                stmt.setString(5, (String) row.get("body"));
                stmt.setTimestamp(6,(Timestamp) row.get(" created_at "));
                stmt.executeUpdate();
              }
            }
          }
        }
      }
      
      // Insert the comments into the database.
      Object commentObject = data.get("comment");
      if (commentObject instanceof List) {
        List<?> commentList = (List<?>) commentObject;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowComment : commentList) {
            if (rowComment instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowComment;
              String commentId = (String) row.get("comment_id");
              String selectSql = "SELECT comment_id FROM comment WHERE comment_id = ?";
              
              try (PreparedStatement selectStmt = cnn.prepareStatement(selectSql)) {
                selectStmt.setString(1, commentId);
                ResultSet resultSet = selectStmt.executeQuery();
                
                // If a row is returned, the comment already exists, so skip insertion
                if (resultSet.next()) {
                  continue;
                }
              }
              String sql = "INSERT INTO comment (comment_id, text, user_id, recipe_id) VALUES (?,?,?,?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, commentId);
                stmt.setString(2, (String) row.get("text"));
                stmt.setString(3, (String) row.get("user_id"));
                stmt.setString(4, (String) row.get("recipe_id"));
                stmt.executeUpdate();
              }
            }
          }
        }
      }
      
      
      // Insert the weekly list into the database
      Object weeklyListObj = data.get("weekly_list");
      if (weeklyListObj instanceof List) {
        List<?> weeklyList = (List<?>) weeklyListObj;
        try (Connection cnn = DriverManager.getConnection(dbUrl)) {
          for (Object rowObj : weeklyList) {
            if (rowObj instanceof Map) {
              Map<?, ?> row = (Map<?, ?>) rowObj;
              String sql = "INSERT INTO weekly_list (user_id, recipe_id, week_date) VALUES (?, ?, ?)";
              try (PreparedStatement stmt = cnn.prepareStatement(sql)) {
                stmt.setString(1, (String) row.get("user_id"));
                stmt.setString(2, (String) row.get("recipe_id"));
                stmt.setTimestamp(3, Timestamp.valueOf((String) row.get("week_date")));
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