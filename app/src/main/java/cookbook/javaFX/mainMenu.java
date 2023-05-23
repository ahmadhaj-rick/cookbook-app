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

public class mainMenu implements Initializable  {
  
  @FXML 
  public Button homebtn;
  @FXML 
  public Button addbutton;
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

  @FXML
  public Label UserNameMain;
  
  
  public void homeClick(ActionEvent event) throws SQLException, IOException {
    
    URL url = new File("src/main/java/cookbook/resources/homepage.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene homeScene = new Scene(root);

    Stage homeStage = (Stage) homebtn.getScene().getWindow();
    homeStage.setScene(homeScene);
    homeStage.show();
    userController user = new userController();
    String name = user.loggedInUser.getName();
    homeStage.setTitle("Welcome to the recipes menu Dear " + name);
    homeStage.setHeight(737);
    homeStage.setWidth(1015);
    homeStage.setResizable(false);
    homeStage.centerOnScreen();
    
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
    userController user = new userController();
    String name = user.loggedInUser.getName();
    weeklistStage.setTitle("Welcome to your Weekly List Dear " + name);
    weeklistStage.setHeight(740);
    weeklistStage.setWidth(1010);
    weeklistStage.setResizable(true);
    weeklistStage.centerOnScreen();
    
  }
  
  public void helpClick(ActionEvent event) throws SQLException, IOException {
    // Go to the week list screen
    URL url = new File("src/main/java/cookbook/resources/help.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene weeklistScene = new Scene(root);

    Stage helpStage = (Stage) weeklistbutton.getScene().getWindow();
    helpStage.setScene(weeklistScene);
    helpStage.show();
    userController user = new userController();
    String name = user.loggedInUser.getName();
    helpStage.setTitle("Figuring about something " + name + ", just search and you will find what you need ");
    helpStage.setHeight(730);
    helpStage.setWidth(815);
    helpStage.centerOnScreen();
    helpStage.setResizable(false);


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
      userController user = new userController();
      String name = user.loggedInUser.getName();
      adminStage.setTitle("welcome Dear " + name + ", You are a great Admin");
      adminStage.setHeight(740);
      adminStage.setWidth(1015);
      adminStage.centerOnScreen();
      adminStage.setResizable(false);
    }
  }

  public void addClick(ActionEvent event) throws SQLException, IOException {
    URL url = new File("src/main/java/cookbook/resources/addNewRecipe.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene addScene = new Scene(root);

    Stage addStage = (Stage) addbutton.getScene().getWindow();
    addStage.setScene(addScene);
    addStage.show();
    userController user = new userController();
    String name = user.loggedInUser.getName();
    addStage.setTitle("It`s always a good idea to have a new recipe that belongs to you " + name);
    addStage.setHeight(750);
    addStage.setWidth(1009);
    addStage.centerOnScreen();
    addStage.setResizable(false);
  }
  
  public void exitClick(ActionEvent event) throws SQLException, IOException {
    URL url = new File("src/main/java/cookbook/resources/login.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene loginScene = new Scene(root);

    Stage appStage = (Stage) quitbutton.getScene().getWindow();
    appStage.setScene(loginScene);
    appStage.show();
    appStage.setHeight(350);
    appStage.setWidth(568);
    appStage.setResizable(false);
    appStage.centerOnScreen();
    appStage.setTitle("Sign In");

  }

  public void userNameUser() {
    //Display the logged in user name
    userController user = new userController();
    String name = user.loggedInUser.getName();
    UserNameMain.setText("Welcome to our lovely app " + name);
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    userNameUser();
  }
}
