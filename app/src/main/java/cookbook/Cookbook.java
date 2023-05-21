package cookbook;

import cookbook.javaFX.Login;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

import cookbook.dbTools.databasemn;
import cookbook.dbTools.dbseeder;
import javafx.scene.Parent;

public class Cookbook extends Application {
  
  @Override
  public void start(Stage primaryStage) throws Exception {

    // Create a StackPane to hold the welcome screen content
    StackPane welcomePane = new StackPane();
    
    // Load the GIF image and add it to the StackPane
    ImageView imageView = new ImageView(new Image(new FileInputStream("src/main/java/cookbook/resources/images/welcome.gif")));
    welcomePane.getChildren().add(imageView);
    
    // Display the welcome scene with the StackPane
    Scene welcomeScene = new Scene(welcomePane, 470, 350);
    primaryStage.setScene(welcomeScene);
    welcomeScene.getStylesheets().add("src/main/java/cookbook/resources/application.css");
    primaryStage.getIcons().add(new Image(new FileInputStream("src/main/java/cookbook/resources/images/CB.png")));
    primaryStage.show();
    
    // Set a timer to switch to the login scene after 5 seconds
    new java.util.Timer().schedule(
    new java.util.TimerTask() {
      @Override
      public void run() {
        Platform.runLater(() -> {
          try {
            // Load the FXML file for the login scene
            URL url = new File("src/main/java/cookbook/resources/login.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();
            
            // Display the login scene
            Scene loginScene = new Scene(root);
            primaryStage.setScene(loginScene);
            
            Login loginController = loader.getController();
            loginController.loginButton.setOnAction(e -> {
             try{
                 loginController.userLogin(e);
             } catch (SQLException | IOException ex) {
                 ex.printStackTrace();
             }
          });

          } catch (IOException e) {
            e.printStackTrace();
          }
        });
      }
    },
    4000
    );

    // init the database

    databasemn initiatedatabase = new databasemn();
    initiatedatabase.database_mn();

    dbseeder seed = new dbseeder();
    seed.seed();
    
    // Connect to the MySQL database and display the connection status
  
    
    primaryStage.setTitle("Welcome to our beloved app, BREAD");





  }
 
  public static void main(String[] args) {
    launch(args);
  }
}
