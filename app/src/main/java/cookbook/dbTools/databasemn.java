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
      System.out.println("User table created");
    }
    catch (SQLException ee) {
      System.out.println("error creating user table");
    }
  }
  
  private void createTablerecipe() {
    String recipetbname = "CREATE TABLE IF NOT EXISTS recipe ("
                            + "recipe_id VARCHAR(60) NOT NULL," 
                            + "name VARCHAR(50) NOT NULL," 
                            + "description VARCHAR(100) NOT NULL,"
                            + "category VARCHAR(50) NOT NULL," 
                            + "instructions VARCHAR(300) NOT NULL,"
                            + "star BOOLEAN NOT NULL," 
                            + "PRIMARY KEY (recipe_id))";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(recipetbname);
      System.out.println("recipe table created");
    }
    catch (SQLException ee) {
      System.out.println("error creating recipe table");
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
      System.out.println("ingredients table created");
    }
    catch (SQLException ee) {
      System.out.println("error creating ingredients table created");
    }
  }
  
  private void createTableRecipeIngredients () {
    String recipeIngredientsTbName =    "CREATE TABLE IF NOT EXISTS recipe_ingredients ("
    + "recipe_id varchar(60) not null,"
    + "ingredient_id varchar(60) not null,"
    + "PRIMARY KEY (recipe_id, ingredient_id),"
    + "FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id),"
    + "FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id))";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(recipeIngredientsTbName);
      System.out.println("Recipe_Ingredients table created");
    } catch (Exception e) {
      System.out.println("Error Creating Recipe_Ingredients table\n" + e);
    }
  }
  
  public void createStarred() {
    String starred = "CREATE TABLE IF NOT EXISTS starred ("
    +"user_id varchar(60) NOT NULL,"
    +"recipe_id varchar(60) NOT NULL,"
    +"FOREIGN KEY (user_id) REFERENCES user(user_id),"
    +"FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id))";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(starred);
      System.out.println("starred table created");
    } catch (Exception e) {
      System.out.println("Error Creating starred table");
    }
  }

  public void createtag() {
    String starred = "CREATE TABLE IF NOT EXISTS tag ("
    +"tag_id varchar(60) NOT NULL,"
    +"name varchar(60) NOT NULL,";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(starred);
      System.out.println("tag table created");
    } catch (Exception e) {
      System.out.println("Error Creating tag table");
    }
  }

  public void createRecipe_tag() {
    String starred = "CREATE TABLE IF NOT EXISTS recipe_tag ("
    +"tag_id varchar(60) NOT NULL,"
    +"recipe_id varchar(60) NOT NULL,"
    +"FOREIGN KEY (tag_id) REFERENCES tag(tag_id),"
    +"FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id))";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(starred);
      System.out.println("recipe_tag table created");
    } catch (Exception e) {
      System.out.println("Error Creating recipe_tag table");
    }
  }

  public void createMessage() {
    String starred = "CREATE TABLE IF NOT EXISTS message ("
    +"to_user varchar(60) NOT NULL,"
    +"from_user varchar(60) NOT NULL,"
    +"recipe_id varchar(60) NOT NULL,"
    +"body varchar(60) NOT NULL,"
    +"created_at varchar(60) NOT NULL,"
    +"FOREIGN KEY (tag_id) REFERENCES tag(tag_id),"
    +"FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id))";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(starred);
      System.out.println("message table created");
    } catch (Exception e) {
      System.out.println("Error Creating message table");
    }
  }
  
  
  
  public void database_mn() {
    try {
      createDb();
      createTableuser();
      createTablerecipe();
      createTableingredients();
      createTableRecipeIngredients();
      createStarred();
      createtag();
      createRecipe_tag();
      createMessage();
    }
    catch (Exception bread) {
      System.out.println(bread);
    }
  }
}
