package cookbook;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cookbook.javaFX.Login;
import javafx.util.Duration;
import javafx.scene.Parent;

public class Cookbook extends Application {
  
  @Override
  public void start(Stage primaryStage) throws Exception {
    
    // Create a StackPane to hold the welcome screen content
    StackPane welcomePane = new StackPane();
    
    // Load the GIF image and add it to the StackPane
    ImageView imageView = new ImageView(new Image(new FileInputStream("src/main/java/cookbook/resources/bread-spin.gif")));
    welcomePane.getChildren().add(imageView);
    
    // Display the welcome scene with the StackPane
    Scene welcomeScene = new Scene(welcomePane, 400, 300);
    primaryStage.setScene(welcomeScene);
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
            
            Login login = loader.getController();
            login.loginButton.setOnAction(e -> {
             try{
                 login.userLogin(e);
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
    5000
    );
    databasemn initiatedatabase = new databasemn();
    initiatedatabase.createDb();
    
    // Connect to the MySQL database and display the connection status
  
    
    primaryStage.setTitle("My JavaFX App");

  
    


  }
 
  public static void main(String[] args) {
    launch(args);
  }
}
