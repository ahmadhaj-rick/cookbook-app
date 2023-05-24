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
    @FXML
    void sendMessageAction(ActionEvent event) throws ParseException {

        userObject user = namelst.getSelectionModel().getSelectedItem();
        userObject selectedUser = userController.loggedInUser;
        try {
            String time = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now());

            Timestamp timestamp = Timestamp.valueOf(time);
            date.setText("Message sent successfully at :  "   + timestamp);


            sendMessageText.setWrapText(true);
            MessageController.sendMessage(user.getId(), selectedUser.getId(), recipeId.getText(), sendMessageText.getText(),timestamp );

        } catch (Exception e) { //this generic but you can control another types of exception
            System.out.println("error");
        }
        txtUserName.clear();
        sendMessageText.clear();


    }

    // receive information about the recipe id
    public void passInformation(String id) {
        recipeId.setText(id);

    }

    public void passNameInformation(String name) {
        recipeName.setText(name);

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
