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
  public ArrayList<tagObject> allTags = new ArrayList<>();

  public static List<tagObject> getTags() throws SQLException {
    ArrayList<tagObject> tags = new ArrayList<>();
    String query = "SELECT * FROM tag;";

    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while (result.next()) {
        tagObject user = new tagObject(
            result.getString("tag_id"),
            result.getString("name"));

        tags.add(user);
      }
      result.close();
    } catch (SQLException e) {
      System.out.println(e);
    }
    return tags;
  }

  public static void addTag(String tag_id, String tag_name) throws SQLException {
    String query = "INSERT INTO tag VALUES(?, ?);";
    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, tag_id);
      sqlStatement.setString(2, tag_name);

      sqlStatement.executeUpdate();

    } catch (SQLException e) {
      System.out.println(e);
    }
  }
  
  public static void addTagToRecipe(String RecipeID, String TagID) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String query = "INSERT INTO recipe_tag VALUES(?,?)";
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, TagID);
      sqlStatement.setString(2, RecipeID);
      sqlStatement.executeUpdate();
      
    //  int rowsAffected = sqlStatement.executeUpdate();
    //  System.out.println(rowsAffected + "rows affected");
    }
  }
}

