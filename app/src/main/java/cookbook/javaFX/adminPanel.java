package cookbook.javaFX;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import cookbook.objectControllers.userController;
import cookbook.objects.userObject;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class adminPanel {
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
  public TextField txtpassword;


  public void adminCreateUser(ActionEvent event) throws SQLException, IOException {
    String name = txtDisplayName.getText();
    String username = txtUserName.getText();
    String password = txtpassword.getText();
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


}
