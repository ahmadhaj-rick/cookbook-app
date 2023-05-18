package cookbook.javaFX;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import cookbook.objects.recipeObject;
import cookbook.objects.ingredientObject;
import cookbook.objects.tagObject;
import cookbook.objectControllers.recipeControler;
import cookbook.objectControllers.userController;
import cookbook.objects.userObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import cookbook.objects.recipeObject;
import cookbook.objectControllers.recipeControler;

public class mainMenu {
  
  @FXML 
  public Button homebtn;
  @FXML 
  public Button addbuttom;
  @FXML 
  public Button inboxbutton;
  @FXML 
  public Button weeklistbutton;
  @FXML 
  public Button helpbutton;
  @FXML 
  public Button adminPanel;
  @FXML 
  public Button quitbutton;
  
  
  public void homeClick(ActionEvent event) throws SQLException, IOException {
    
    // Go back to the home screen
    URL url = new File("src/main/java/cookbook/resources/homepage.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene homeScene = new Scene(root);
    
    Stage homeStage = (Stage) homebtn.getScene().getWindow();
    homeStage.setScene(homeScene);
    homeStage.show();
    
  }
  
  public void addClick(ActionEvent event) throws SQLException, IOException {
    
    /** // Go to the add recipe screen
    URL url = new File("src/main/java/cookbook/resources/addrecipe.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene addScene = new Scene(root);
    
    Stage addStage = (Stage) addbutton.getScene().getWindow();
    addStage.setScene(addScene);
    addStage.show();  */
    
  }
  public void inboxClick(ActionEvent event) throws SQLException, IOException {
    
    // Go to the inbox screen
    URL url = new File("src/main/java/cookbook/resources/inbox.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene inboxScene = new Scene(root);
    
    Stage inboxStage = (Stage) inboxbutton.getScene().getWindow();
    inboxStage.setScene(inboxScene);
    inboxStage.show();
    
  } 
  public void weeklistClick(ActionEvent event) throws SQLException, IOException {
    
    // Go to the week list screen
    URL url = new File("src/main/java/cookbook/resources/weeklyList.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene weeklistScene = new Scene(root);
    
    Stage weeklistStage = (Stage) weeklistbutton.getScene().getWindow();
    weeklistStage.setScene(weeklistScene);
    weeklistStage.show();
    
  }
  
  public void helpClick(ActionEvent event) throws SQLException, IOException {
    // Go to the week list screen
    URL url = new File("src/main/java/cookbook/resources/help.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene weeklistScene = new Scene(root);

    Stage weeklistStage = (Stage) weeklistbutton.getScene().getWindow();
    weeklistStage.setScene(weeklistScene);
    weeklistStage.show();


  }
  
  public void adminPanelClick(ActionEvent event) throws SQLException, IOException {
    
    userObject loggedUser = userController.loggedInUser;
    
    if (loggedUser.getAdminPrivelages().equals(false)) {
      Alert error = new Alert(Alert.AlertType.INFORMATION);
      error.setTitle("Big NoNo");
      error.setContentText("You are a peasent <3.");
      error.show();
    }else {
      URL url = new File("src/main/java/cookbook/resources/adminpanel.fxml").toURI().toURL();
      FXMLLoader loader = new FXMLLoader(url);
      Parent root = loader.load();
      Scene adminScene = new Scene(root);
      
      Stage adminStage = (Stage) adminPanel.getScene().getWindow();
      adminStage.setScene(adminScene);
      adminStage.show();
      
      
      // Wait for the admin panel to close
      adminStage.setOnCloseRequest(e -> {
        try {
          URL url2 = new File("src/main/java/cookbook/resources/mainmenu.fxml").toURI().toURL();
          FXMLLoader loader2 = new FXMLLoader(url2);
          Parent root2 = loader2.load();
          Scene mainMenu = new Scene(root2);
          
          Stage mainMenuStage = new Stage();
          mainMenuStage.setScene(mainMenu);
          mainMenuStage.show();
          
        } catch (IOException ioException) {
          ioException.printStackTrace();
        }
      });
    }
    
    
    
    
  }
  
  public void exitClick(ActionEvent event) throws SQLException, IOException {
    URL url = new File("src/main/java/cookbook/resources/login.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene loginScene = new Scene(root);

    Stage appStage = (Stage) quitbutton.getScene().getWindow();
    appStage.setScene(loginScene);
    appStage.show();
    appStage.setHeight(500);
    appStage.setWidth(650);
    appStage.setResizable(true);
    appStage.centerOnScreen();
    appStage.setTitle("Sign In");


    
    
  }
  
}
