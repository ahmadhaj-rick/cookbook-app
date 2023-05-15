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
      System.out.println("error creating user table: " + ee);
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
      System.out.println("error creating recipe table: " + ee);
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
      System.out.println("error creating ingredients table created: " + ee);
    }
  }
  
  private void createTableRecipeIngredients () {
    String recipeIngredientsTbName =    "CREATE TABLE IF NOT EXISTS recipe_ingredients ("
    + "recipe_id varchar(60) not null,"
    + "ingredient_id varchar(60) not null,"
    + "PRIMARY KEY (recipe_id, ingredient_id),"
    + "FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id) ON UPDATE CASCADE ON DELETE CASCADE,"
    + "FOREIGN KEY (ingredient_id) REFERENCES ingredients(ingredient_id) ON UPDATE CASCADE ON DELETE CASCADE)";
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
    +"PRIMARY KEY (user_id, recipe_id),"
    +"FOREIGN KEY (user_id) REFERENCES user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,"
    +"FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id) ON UPDATE CASCADE ON DELETE CASCADE)";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(starred);
      System.out.println("starred table created");
    } catch (Exception e) {
      System.out.println("Error Creating starred table: " + e);
    }
  }
  
  public void createtag() {
    String tag = "CREATE TABLE IF NOT EXISTS tag ("
    +"tag_id varchar(60) NOT NULL,"
    +"name varchar(60) NOT NULL,"
    +"PRIMARY KEY (tag_id))";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(tag);
      System.out.println("tag table created");
    } catch (Exception e) {
      System.out.println("Error Creating tag table: " + e);
    }
  }
  
  public void createRecipe_tag() {
    String recipe_tag = "CREATE TABLE IF NOT EXISTS recipe_tag ("
    +"tag_id varchar(60) NOT NULL,"
    +"recipe_id varchar(60) NOT NULL,"
    +"PRIMARY KEY (tag_id, recipe_id),"
    +"FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON UPDATE CASCADE ON DELETE CASCADE,"
    +"FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id) ON UPDATE CASCADE ON DELETE CASCADE)";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(recipe_tag);
      System.out.println("recipe_tag table created");
    } catch (Exception e) {
      System.out.println("Error Creating recipe_tag table: " + e);
    }
  }
  
  public void createMessage() {
    String message = "CREATE TABLE IF NOT EXISTS message ("
    +"message_id varchar(60) NOT NULL,"
    +"to_user varchar(60) NOT NULL,"
    +"from_user varchar(60) NOT NULL,"
    +"recipe_id varchar(60) NOT NULL,"
    +"body varchar(255),"
    +"created_at DATETIME DEFAULT NOW(),"
    +"PRIMARY KEY (message_id),"
    +"CONSTRAINT fk_to_user FOREIGN KEY (to_user) REFERENCES user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,"
    +"CONSTRAINT fk_from_user FOREIGN KEY (from_user) REFERENCES user(user_id) ON UPDATE CASCADE ON DELETE CASCADE,"
    +"CONSTRAINT fk_recipe FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id) ON UPDATE CASCADE ON DELETE CASCADE)";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(message);
      System.out.println("message table created");
    } catch (Exception e) {
      System.out.println("Error Creating message table: " + e);
    }
  }

   // add a weekly list table to the database.

   private void createTableWeeklyList() {
    String weeklyListTbName = "CREATE TABLE IF NOT EXISTS weekly_list ("
        + "weekly_list_id VARCHAR(60) NOT NULL,"
        + "user_id VARCHAR(60) NOT NULL,"
        + "recipe_id VARCHAR(60) NOT NULL,"
        + "week_number INT NOT NULL,"
        + "PRIMARY KEY (weekly_list_id),"
        + "FOREIGN KEY (user_id) REFERENCES user(user_id),"
        + "FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id))";
    try {
      Connection cnn = DriverManager.getConnection(dbUrl + dbna + creds);
      Statement stm = cnn.createStatement();
      stm.executeUpdate(weeklyListTbName);
      System.out.println("Weekly List table created");
    } catch (SQLException ee) {
      System.out.println("Error creating Weekly List table");
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
      createTableWeeklyList();
    }
    catch (Exception bread) {
      System.out.println(bread);
    }
  }
}
