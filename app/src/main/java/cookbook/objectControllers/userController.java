package cookbook.objectControllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.ResultSet;
import java.sql.SQLException;

import cookbook.objects.userObject;;

public class userController {

  // Returns a list of the users, This is mainly for the admin screen to edit
  // users information.
  public static userObject loggedInUser;

  /**
   * returns list of all users.
   */
  public static List<userObject> getUsers() throws SQLException {
    String query = "SELECT * FROM user;";
    List<userObject> allUsers = new ArrayList<>();
    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      ResultSet result = sqlStatement.executeQuery();
      while (result.next()) {
        userObject newUser = new userObject(
            result.getString("user_id"),
            result.getString("fname"),
            result.getString("username"),
            result.getString("password"),
            result.getBoolean("admin_id"));

        allUsers.add(newUser);
      }
      result.close();
    } catch (SQLException x) {
      System.out.println(x);
    }
    return allUsers;
  }

  /**
   * to search a user by their username.
   */
  public static userObject searchByUsername(String username) throws SQLException {
    String query = "SELECT * FROM user WHERE username=(?) LIMIT 1;";

    // If no user is found, return nothing.
    userObject user = null;
    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    ;
    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, username);
      ResultSet result = sqlStatement.executeQuery();
      if (result.next()) {
        user = new userObject(
            result.getString("user_id"),
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


  /**
   * searches for a user using their id.
   */
  public static userObject searchFromId(String userId) throws SQLException {
    String queryString = "SELECT * FROM user WHERE user_id=(?) LIMIT 1;";

    // If no user is found, return null.
    userObject user = null;

    Connection conn = DriverManager
    .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    try (PreparedStatement preparedStmnt = conn.prepareStatement(queryString)) {
        preparedStmnt.setString(1, userId);
        ResultSet result = preparedStmnt.executeQuery();
        if (result.next()) {
          user = new userObject(
              result.getString("user_id"),
              result.getString("fname"),
              result.getString("username"),
              result.getString("password"),
              result.getBoolean("admin_id"));
        }

        result.close();
    } catch (SQLException e) {
        System.out.println("Error retrieving user" + e);
    }

    return user;
}


/**
 * searches for a user using their username and password.
 */
  public static userObject searchForUser(String username, String password) throws SQLException {

    String query = "SELECT * FROM user WHERE username=(?) AND password=(?) LIMIT 1;";

    // If theres no user with that information, return null.
    loggedInUser = null;
    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, username);
      sqlStatement.setString(2, password);
      ResultSet result = sqlStatement.executeQuery();
      if (result.next()) {
        loggedInUser = new userObject(
            result.getString("user_id"),
            result.getString("fname"),
            result.getString("username"),
            result.getString("password"),
            result.getBoolean("admin_id"));
      }
      result.close();
    } catch (SQLException x) {
      System.out.println(x);
    }
    return loggedInUser;
  }

  /**
   * adds a new user.
   */
  public static void addUser(String name, String username, String password, Boolean isAdmin) throws SQLException {

    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
        String query = "INSERT into user VALUES(?,?,?,?,?);";

    UUID uniqueID = UUID.randomUUID();
    String userID = uniqueID.toString();

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, userID);
      sqlStatement.setString(2, name);
      sqlStatement.setString(3, username);
      sqlStatement.setString(4, password);
      sqlStatement.setBoolean(5, isAdmin);

      sqlStatement.executeUpdate();
    } catch (SQLException x) {
      System.out.println(x);
    }

  }

  /*public static void ModifyUser(String i, String name, String username, String password, Boolean isAdmin) throws SQLException {
    Connection conn = DriverManager
        .getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String query = "UPDATE user SET name = ?, username = ?, password = ?, isAdmin = ? WHERE userID = ?;";

    UUID uniqueID = UUID.randomUUID();
    String userID = uniqueID.toString();

    try (PreparedStatement sqlStatement = conn.prepareStatement(query)) {
      sqlStatement.setString(1, userID);
      sqlStatement.setString(2, name);
      sqlStatement.setString(3, username);
      sqlStatement.setString(4, password);
      sqlStatement.setBoolean(5, isAdmin);

      sqlStatement.executeUpdate();
    } catch (SQLException x) {
      System.out.println(x);
    }
  }*/

  /**
   * edits the information of a user.
   */
  public static void editUser(String name, String username, String password, String userID) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String updateQuery = "UPDATE user SET fname=(?), username=(?), password=(?) WHERE user_id=(?)";

    try(PreparedStatement sqlStatement = conn.prepareStatement(updateQuery)) {
      sqlStatement.setString(1, name);
      sqlStatement.setString(2, username);
      sqlStatement.setString(3, password);
      sqlStatement.setString(4, userID);
      sqlStatement.executeUpdate();
    } catch (SQLException x) {
      System.out.println(x);
    }
  }

  /**
   * deletes a user.
   */
  public static void deleteUser(String name, String username, String password) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
    String deleteString = "DELETE FROM user WHERE fname=(?) AND username=(?) AND password=(?);";
  
    try(PreparedStatement sqlStatement = conn.prepareStatement(deleteString)) {
      sqlStatement.setString(1, name);
      sqlStatement.setString(2, username);
      sqlStatement.setString(3, password);

      sqlStatement.executeUpdate();
    } catch (SQLException x) {
      System.out.println(x);
    }
  }

}