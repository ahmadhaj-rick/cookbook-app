package cookbook.dbTools;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class databasemn {
  private static String dbUrl = "jdbc:mysql://localhost:3306/";
  private String creds = "?user=root&password=root";
  private static String dbna = "cookbook";


  public void createDb() {
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + creds);
      Statement stm = cnn.createStatement();
      int myR = stm.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbna);
      System.out.println("database created");
    } catch(SQLException err) {
      err.printStackTrace();
    }
  }

  private void createTableuser() {
    String usertbname = "CREATE TABLE IF NOT EXISTS user ("
                        + "user_id VARCHAR(60) NOT NULL," 
                        + "fname VARCHAR(20) NOT NULL," 
                        + "username VARCHAR(40) NOT NULL," 
                        + "password VARCHAR(85) NOT NULL," 
                        + "admin_id BOOLEAN not null," 
                        + "PRIMARY KEY (user_id)," 
                        + "UNIQUE (username))"; 
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(usertbname);
      System.out.println("table created");
    }
    catch (SQLException ee) {
      System.out.println("error ocurred during table creation");
    }
  }

  private void createTablerecepie() {
    String recepietbname = "CREATE TABLE IF NOT EXISTS recepie ("
                            + "recepie_id VARCHAR(60) NOT NULL," 
                            + "name VARCHAR(50) NOT NULL," 
                            + "description VARCHAR(100) NOT NULL,"
                            + "category VARCHAR(50) NOT NULL," 
                            + "instructions VARCHAR(300) NOT NULL," 
                            + "PRIMARY KEY (recepie_id))";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(recepietbname);
      System.out.println("table created");
    }
    catch (SQLException ee) {
      System.out.println("error ocurred during table creation");
    } 
  }

  private void createTableingredients() {
    String ingredientstbname = "CREATE TABLE IF NOT EXISTS ingredients ("
                                + "ingredient_id VARCHAR(60) NOT NULL," 
                                + "ingredient_name VARCHAR(50) NOT NULL," 
                                + "PRIMARY KEY (ingredient_id))";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(ingredientstbname);
      System.out.println("table created");
    }
    catch (SQLException ee) {
      System.out.println("error ocurred during table creation");
    }
  }

 

  public void database_mn() {
    try {
      createDb();
      createTableuser();
      createTablerecepie();
      createTableingredients();
    }
    catch (Exception bread) {
      System.out.println(bread);
    }
  }
}
