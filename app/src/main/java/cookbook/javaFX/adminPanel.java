 package cookbook.javaFX;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import cookbook.objectControllers.userController;
import cookbook.objects.userObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class adminPanel implements Initializable {
  @FXML
  public Button modifyuser;
  @FXML
  public Button deleteuser;
  @FXML
  public Button createuser;
  @FXML
  public TextField txtUserName;
  @FXML
  public TextField txtDisplayName;
  @FXML
  public TextField txtPassword;
  @FXML
  public TableView<userObject> userlst;
  @FXML
  public Button back;
  
  public List<userObject> users;
  
  
  
  public void refreshData() {
    initialize(null, null);
  }
  
  public void adminCreateUser(ActionEvent event) throws SQLException, IOException {
    String name = txtDisplayName.getText();
    String username = txtUserName.getText();
    String password = txtPassword.getText();
    try {
      userController.addUser(name, username, password, false);
      Alert success = new Alert(Alert.AlertType.INFORMATION);
      success.setTitle("Success!");
      success.setContentText("You successfully created a new user!");
      success.show();
      loadData();
    } catch (SQLException x) {
      Alert failure = new Alert(Alert.AlertType.INFORMATION);
      failure.setTitle("Success!");
      failure.setContentText(x.toString());
      failure.show();
    }
  }
  
  public void adminDeleteUser(ActionEvent event) throws SQLException, IOException{
    userObject user = userlst.getSelectionModel().getSelectedItem();
    /*String name = txtDisplayName.getText();
    String username = txtUserName.getText();
    String password = txtPassword.getText();*/
    
    if (user == null) {
      return;
    } else {
      try{
        userController.deleteUser(user.getName(), user.getUser_name(), user.getPass());
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success!");
        success.setContentText("You successfully deleted a user!");
        success.show();
        loadData();
      } catch (SQLException x) {
        Alert failure = new Alert(Alert.AlertType.INFORMATION);
        failure.setTitle("Success!");
        failure.setContentText(x.toString());
        failure.show();
      }
    }
  }
  
  public void modifyUser(ActionEvent event) throws SQLException, IOException {
    userObject user = userlst.getSelectionModel().getSelectedItem();
    if(user == null) {
      return;
    }
    userController.editUser(txtDisplayName.getText(), txtUserName.getText(), txtPassword.getText(), user.getId());
    Alert success = new Alert(Alert.AlertType.INFORMATION);
    success.setTitle("Success!");
    success.setContentText("You successfully modified a user!");
    success.show();
    loadData();
    
  }

  public void backButton(ActionEvent event) throws SQLException, IOException {
    URL url = new File("src/main/java/cookbook/resources/mainmenu.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene loginScene = new Scene(root);

    Stage mainStage = (Stage) back.getScene().getWindow();
    mainStage.setScene(loginScene);
    mainStage.show();
    mainStage.setHeight(740);
    mainStage.setWidth(1000);
    mainStage.setResizable(true);
    mainStage.centerOnScreen();
    userController user = new userController();
    String name = user.loggedInUser.getName();
    mainStage.setTitle("Welcome back to the main menu dear " + name );

  }
  
  /*
  public void adminModifyUser(ActionEvent event) throws SQLException, IOException { 
    String name = txtDisplayName.getText(); 
    String username = txtUserName.getText(); 
    String password = txtPassword.getText(); // get the selected user from the table view 
    
    userObject selectedUser = userlst.getSelectionModel().getSelectedItem(); // check if a user is selected 
    
    if (selectedUser != null) { // get the userID of the selected user 
      String i = selectedUser.getId(); 
      try { // modify the user with the new values 
        userController.ModifyUser(i, name, username, password, false); 
        Alert success = new Alert(Alert.AlertType.INFORMATION); 
        success.setTitle("Success!");
        success.setContentText("You successfully modified the user!"); 
        success.show(); 
      } catch(SQLException x) { 
        Alert failure = new Alert(Alert.AlertType.INFORMATION); 
        failure.setTitle("Failure!"); 
        failure.setContentText(x.toString()); 
        failure.show(); } 
      } else { // no user is selected 
        Alert warning = new Alert(Alert.AlertType.WARNING); 
        warning.setTitle("Warning!"); warning.setContentText("Please select a user from the table to modify."); warning.show(); 
      } 
    } */
    
    
    /*private void loadData() {
      users = userController.getUsers();
      ObservableList<userObject> user = FXCollections.observableArrayList(users);
      userlst.getItems().clear();
      userlst.getItems().addAll(user);
    }
    
    // adding data to the columns
    private void initiatecols() {
      
      displayNameColumn.setCellValueFactory(new PropertyValueFactory<>("displayName"));
      
    }*/

    private void loadData() throws SQLException {
      users = userController.getUsers();
      ObservableList<userObject> user = FXCollections.observableArrayList(users);
      userlst.getItems().clear();
      userlst.getItems().addAll(user);
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      try {
        users = userController.getUsers();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      System.out.println(users.size());
      ObservableList<userObject> userList = FXCollections.observableArrayList(users);
      
      userlst.getColumns().clear();
      // for some reasons there is an empty colum !!!!
      
      TableColumn<userObject, String> nameColumn = new TableColumn<>("Name");
      nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
      
      TableColumn<userObject, String> usernameColumn = new TableColumn<>("User Name");
      usernameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));
      
      TableColumn<userObject, String> passwordColumn = new TableColumn<>("Password");
      passwordColumn.setCellValueFactory(new PropertyValueFactory<>("pass"));
      
      userlst.getColumns().add(nameColumn);
      userlst.getColumns().add(usernameColumn);
      userlst.getColumns().add(passwordColumn);
      userlst.getItems().clear();
      userlst.setItems(userList);

      userlst.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
          txtDisplayName.setText(newValue.getName());
          txtUserName.setText(newValue.getUser_name());
          txtPassword.setText(newValue.getPass());
        } else {
          // Clear the text fields if no user is selected
          txtDisplayName.setText("");
          txtUserName.setText("");
          txtPassword.setText("");
        }
      });
      
    }
    
  }
  