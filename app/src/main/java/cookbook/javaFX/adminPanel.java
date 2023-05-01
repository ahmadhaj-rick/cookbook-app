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

  public List<userObject> users;



  public void refreshData() {

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
    } catch(SQLException x) {
      Alert failure = new Alert(Alert.AlertType.INFORMATION);
      failure.setTitle("Success!");
      failure.setContentText(x.toString());
      failure.show();
    }
  }

  public void adminDeleteUser(ActionEvent event) throws SQLException, IOException{
    String name = txtDisplayName.getText();
    String username = txtUserName.getText();
    String password = txtPassword.getText();
    
    try{
      userController.deleteUser(name, username, password);
      Alert success = new Alert(Alert.AlertType.INFORMATION);
      success.setTitle("Success!");
      success.setContentText("You successfully deleted a user!");
      success.show();
    } catch (SQLException x) {
      Alert failure = new Alert(Alert.AlertType.INFORMATION);
      failure.setTitle("Success!");
      failure.setContentText(x.toString());
      failure.show();
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

  }


}
