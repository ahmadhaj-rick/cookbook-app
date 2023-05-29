package cookbook.javaFX;

import cookbook.objectControllers.MessageController;
import cookbook.objectControllers.userController;
import cookbook.objects.userObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Time;
import java.sql.Timestamp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class for the Send Messages view. Initializes the view components and handles sending messages.
 */
public class SendMessages implements Initializable {

  @FXML
  public Label recipeName;

  @FXML
  public TextField txtUserName;

  @FXML
  public TextArea sendMessageText;
  @FXML
  public Label date;

  @FXML
  public Label recipeId;

  public TableView<userObject> namelst;
  public List<userObject> users;

  // send message button

    /**
   * Action event handler for the send message button. Sends a message to the selected user.
   *
   * @param event The action event triggered by clicking the send message button.
   * @throws ParseException If an error occurs while parsing the timestamp.
   */

  @FXML
  void sendMessageAction(ActionEvent event) throws ParseException {

    userObject user = namelst.getSelectionModel().getSelectedItem();
    userObject selectedUser = userController.loggedInUser;
    try {
      String time = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());

      Timestamp timestamp = Timestamp.valueOf(time);
      date.setText("Message sent successfully at :  " + timestamp);

      sendMessageText.setWrapText(true);
      MessageController.sendMessage(user.getId(), selectedUser.getId(), recipeId.getText(), sendMessageText.getText(),time, timestamp );

    } catch (Exception e) { // this generic but you can control another types of exception
      System.out.println("error");
    }
    txtUserName.clear();
    sendMessageText.clear();

  }

  // receive information about the recipe id

    /**
   * Receives the recipe ID information and displays it on the view.
   *
   * @param id The recipe ID to be displayed.
   */

  public void passInformation(String id) {
    recipeId.setText(id);

  }

    /**
   * Receives the recipe name information and displays it on the view.
   *
   * @param name The recipe name to be displayed.
   */

  public void passNameInformation(String name) {
    recipeName.setText(name);

  }

    /**
   * Initializes the controller when the corresponding view is loaded.
   * Retrieves the list of users, configures the user table, and sets up the event listener for user selection.
   *
   * @param location  The location used to resolve relative paths for the root object,
   *                  or null if the location is not known.
   * @param resources The resources used to localize the root object,
   *                  or null if the root object was not localized.
   */

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      users = userController.getUsers();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println(users.size());
    ObservableList<userObject> userList = FXCollections.observableArrayList(users);

    namelst.getColumns().clear();

    TableColumn<userObject, String> nameColumn = new TableColumn<>("Name");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<userObject, String> usernameColumn = new TableColumn<>("User Name");
    usernameColumn.setCellValueFactory(new PropertyValueFactory<>("user_name"));

    namelst.getColumns().add(nameColumn);
    namelst.getColumns().add(usernameColumn);
    namelst.getItems().clear();
    namelst.setItems(userList);

    namelst.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        txtUserName.setText(newValue.getName());
        txtUserName.setText(newValue.getUser_name());
      } else {
        // Clear the text fields if no user is selected
        txtUserName.setText("");
      }
    });

  }
}
