
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
public class Login {
  @FXML
  public TextField user;
  @FXML
  public TextField passwordtxt;
  @FXML
  public Button loginButton;

  //hello this is a test comment


  public void userLogin(ActionEvent event) throws SQLException, IOException {
    String username = user.getText();
    String pass = passwordtxt.getText();

    userObject realUser = userController.searchForUser(username, pass);

    // using else-if statements to switch scene if user is found

    if(realUser != null) {
      URL url = new File("src/main/java/cookbook/resources/mainmenu.fxml").toURI().toURL();
      FXMLLoader loader = new FXMLLoader(url);
      Parent root = loader.load();
      Scene mainMenu = new Scene(root);

      Stage mainMenuStage = (Stage) loginButton.getScene().getWindow();
      mainMenuStage.setScene(mainMenu);
      mainMenuStage.show();
    } else {
      Alert error = new Alert(Alert.AlertType.INFORMATION);
      error.setTitle("Error");
      error.setContentText("Invalid login credentials, please try again. Sincerely, Bread.");
      error.show();

    }
  }

}
 