package cookbook;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class databasemn {
  private String Db = "dbc:mysql://localhost:3306/";
  private String dbna = "cookbook";
  private String user = "root";
  private String pass = "";
  private String userpass = "?user=root&password=";
  private Connection cnn;
  private Statement stm;
  private ResultSet result;


  public void Main() {
    try {
      Connection cnn = DriverManager.getConnection(Db + userpass);
      Statement stm = cnn.createStatement();
      int myR = stm.executeUpdate("CREATE DATABASE IF NOT EXISTS" + dbna);
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
    +  "password VARCHAR(85) NOT NULL," 
    + "admin_id BOOLEAN not null," 
    + "PRIMARY KEY (user_id)," 
    + "UNIQUE (username))"; 
    try {
      cnn = DriverManager.getConnection(Db + dbna, user, pass);
      stm = cnn.createStatement();
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
    + " description VARCHAR(100) NOT NULL,"
    + "category VARCHAR(50) NOT NULL," 
    + " instructions VARCHAR(300) NOT NULL," 
    + " PRIMARY KEY (recepie_id))";
    try {
      cnn = DriverManager.getConnection(Db + dbna, user, pass);
      stm = cnn.createStatement();
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
    +"PRIMARY KEY (ingredient_id))";
    try {
      cnn = DriverManager.getConnection(Db + dbna, user, pass);
      stm = cnn.createStatement();
      stm.executeUpdate(ingredientstbname);
      System.out.println("table created");
    }
    catch (SQLException ee) {
      System.out.println("error ocurred during table creation");
    }
  }

 

  public void database_mn() {
    try {
      cnn = DriverManager.getConnection(Db);
    }
    catch (SQLException ee) {
      Main();
      createTableuser();
      createTablerecepie();
      createTableingredients();
    }
  }
}
