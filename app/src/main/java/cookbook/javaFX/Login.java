
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

/**
 * Handles the action when the user login button is clicked.
 * Retrieves the entered username and password from the user interface.
 * Searches for a user with the given username and password using the userController's searchForUser() method.
 * If a user is found, it loads the mainmenu.fxml file, sets it as the root of the scene, and displays the scene in the main menu stage.
 * Sets the stage dimensions, centers it on the screen, and disables resizing.
 * Displays an error message if the login credentials are invalid.
 *
 * @param event The action event that triggered the method.
 * @throws SQLException If there is an SQL-related error.
 * @throws IOException  If there is an I/O error.
 */

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

/**
 * Initializes the Login controller after its root element has been completely processed.
 * Calls the toggleShowPassword() method with a null parameter.
 *
 * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
 * @param resources The resources used to localize the root object, or null if the root object was not localized.
 */

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.toggleShowPassword(null);

  }

/**
 * Toggles the visibility of the password fields based on the state of the show password checkbox.
 * If the checkbox is selected, it sets the text of the passtext field to match the passwordtxt field and shows the passtext field while hiding the passwordtxt field.
 * If the checkbox is not selected, it sets the text of the passwordtxt field to match the passtext field and shows the passwordtxt field while hiding the passtext field.
 *
 * @param actionEvent The action event that triggered the method.
 */

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

 
