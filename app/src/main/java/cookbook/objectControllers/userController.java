package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import cookbook.objects.userObject;;

public class userController {
  
  //Returns a list of the users, This is mainly for the admin screen to edit users information.

  public static List<userObject> getUsers() throws SQLException {
    String query = "SELECT * FROM user;";
    List<userObject> allUsers = new ArrayList<>();
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    try(PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while(result.next()) {
        userObject newUser = new userObject(
          result.getInt("user_id"),
          result.getString("fname"),
          result.getString("username"),
          result.getString("password"),
          result.getBoolean("admin_id"));
        
        allUsers.add(newUser);
      }
      result.close();
    } catch(SQLException x) {
      System.out.println(x);
    }
    return allUsers;
  }
  

  public static userObject searchByUsername(String username) throws SQLException {
    String query = "SELECT * FROM user WHERE username=(?) LIMIT 1;";

    //If no user is found, return nothing.
    userObject user = null;
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");;
    try(PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, username);
      ResultSet result = sqlStatement.executeQuery();
      if(result.next()) {
        user = new userObject(
        result.getInt("user_id"),
        result.getString("fname"),
        result.getString("username"),
        result.getString("password"),
        result.getBoolean("admin_id"));
      }
    result.close();
    } catch(SQLException x) {
      System.out.println(x);
    }
    return user;
  }
  

  public static userObject searchForUser(String username, String password) throws SQLException {

    String query = "SELECT * FROM user WHERE username=(?) AND password=(?) LIMIT 1;";

    //If theres no user with that information, return null.
    userObject user = null;
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try(PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, username);
      sqlStatement.setString(2, password);
      ResultSet result = sqlStatement.executeQuery();
      if(result.next()) {
        user = new userObject(
        result.getInt("user_id"),
        result.getString("fname"),
        result.getString("username"),
        result.getString("password"),
        result.getBoolean("admin_id"));
      }
      result.close();
    } catch (SQLException x) {
      System.out.println(x);
    }
    return user;
  }
}