package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cookbook.objects.tagObject;

public class tagController {
  //public ArrayList<tagObject> allTags = new ArrayList<>();
  
  
  public static List<tagObject> getTags() throws SQLException {
    ArrayList<tagObject> tags = new ArrayList<>();
    String query = "SELECT * FROM tag;";
    
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while (result.next()) {
        tagObject tag = new tagObject(
        result.getString("tag_id"),
        result.getString("name"));
        tags.add(tag);
      }
      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return tags;
  }
  
  public static tagObject addTag(String tag_id, String tag_name) throws SQLException {
    String query = "INSERT INTO user VALUES(?, ?);";
    Connection conn = DriverManager
    .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, tag_id);
      sqlStatement.setString(2, tag_name);
      
      sqlStatement.executeUpdate();
      
      tagObject tag = new tagObject(tag_id, tag_name);
      return tag;
    } catch (SQLException e) {
      System.out.println(e);
    }
    return null;
    
    
  }
  
  public static void addTagToRecipe(String RecipeID, String TagID) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String query = "INSERT INTO recipe_tag VALEUS(?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, RecipeID);
      sqlStatement.setString(2, TagID);
      sqlStatement.executeUpdate();
      
      int rowsAffected = sqlStatement.executeUpdate();
      System.out.println(rowsAffected + "rows affected");
    }
  }
}

