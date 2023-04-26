package cookbook;

import javafx.fxml.FXMLLoader;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
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

        URL url = new File("src/main/java/cookbook/resources/login.fxml").toURI().toURL();
        
        
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        // Display Login scene
        Scene loginScene = new Scene(root);
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Connect to MySQL database and display connection status
        Label mysqlLabel;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cookbook?user=root&password=root&useSSL=false");
            mysqlLabel = new Label("Driver found and connected");
        } catch (SQLException e) {
            mysqlLabel = new Label("An error has occurred: " + e.getMessage());
        }

        // Create welcome scene with MySQL connection status
        StackPane welcomePane = new StackPane();
        Label welcomeLabel = new Label("Welcome to my JavaFX application!");
        welcomePane.getChildren().addAll(welcomeLabel, mysqlLabel);
        Scene welcomeScene = new Scene(welcomePane, 400, 300);


        // Add a fade-in animation to the welcome scene
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), welcomePane);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();

        // Switch to welcome scene after 5 seconds
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        primaryStage.setScene(welcomeScene);
                    }
                },
                5000
        );

        primaryStage.setTitle("Bread");


    
       Login login = loader.getController();
       login.loginButton.setOnAction(e -> {
        try{
            login.userLogin(e);
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    });
       



    }

    public static void main(String[] args) {
        launch(args);
    }
}
