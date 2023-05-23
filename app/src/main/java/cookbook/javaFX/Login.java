
package cookbook.javaFX;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import cookbook.objectControllers.userController;
import cookbook.objects.userObject;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
public class Login implements Initializable {
  @FXML
  public TextField user;
  @FXML
  public CheckBox showPass;
  @FXML
  public PasswordField passwordtxt;

  @FXML
  public TextField passtext;
  @FXML
  public Button loginButton;

  public void userLogin(ActionEvent event) throws SQLException, IOException {
    String username = user.getText();
    String pass = passwordtxt.getText();
    //String shown_password = passtext.getText();

    userObject realUser = userController.searchForUser(username, pass);

    // using else-if statements to switch scene if user is found
    if(realUser != null ) {
      URL url = new File("src/main/java/cookbook/resources/mainmenu.fxml").toURI().toURL();
      FXMLLoader loader = new FXMLLoader(url);
      Parent root = loader.load();
      Scene mainMenu = new Scene(root);

      Stage mainMenuStage = (Stage) loginButton.getScene().getWindow();
      mainMenuStage.setScene(mainMenu);
      mainMenuStage.setHeight(740);
      mainMenuStage.setWidth(1000);
      mainMenuStage.centerOnScreen();
      mainMenuStage.setResizable(false);
      mainMenuStage.show();
    } /**else if (shown_password != null ) {
      URL url = new File("src/main/java/cookbook/resources/mainmenu.fxml").toURI().toURL();
      FXMLLoader loader = new FXMLLoader(url);
      Parent root = loader.load();
      Scene mainMenu = new Scene(root);

      Stage mainMenuStage = (Stage) loginButton.getScene().getWindow();
      mainMenuStage.setScene(mainMenu);
      mainMenuStage.setHeight(737);
      mainMenuStage.setWidth(1000);
      mainMenuStage.centerOnScreen();
      mainMenuStage.setResizable(false);
      mainMenuStage.show();
    }*/ else {
      Alert error = new Alert(Alert.AlertType.INFORMATION);
      error.setTitle("Error");
      error.setContentText("Invalid login credentials, please try again. Sincerely, Bread.");
      error.show();

    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.toggleShowPassword(null);

  }

  public void toggleShowPassword(ActionEvent actionEvent) {
      if (showPass.isSelected()) {
        passtext.setText(passwordtxt.getText());
        passtext.setVisible(true);
        passwordtxt.setVisible(false);
      } else {
        passwordtxt.setText(passtext.getText());
        passtext.setVisible(false);
        passwordtxt.setVisible(true);
      }
    }

  }

 
