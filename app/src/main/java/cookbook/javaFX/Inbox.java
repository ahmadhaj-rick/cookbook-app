package cookbook.javaFX;

import cookbook.objectControllers.userController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class Inbox {
  @FXML
    public Button back;
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
        mainStage.setResizable(false);
        mainStage.centerOnScreen();
        userController user = new userController();
        String name = user.loggedInUser.getName();
        mainStage.setTitle("Welcome back to the main menu dear " + name );

    }
}
