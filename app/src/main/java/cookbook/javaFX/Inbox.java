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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Inbox implements Initializable {
    @FXML
    public Button back;
    @FXML
    private TableView<userObject> usersNames;
    @FXML
    private Label messageBox;
    @FXML
    private ListView<String> inboxlist;

    public List<userObject> users;

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
        mainStage.setTitle("Welcome back to the main menu dear " + name);

    }


    // display all the message and the recipe when selecting the user if there is a message
    @FXML
    void DisplaySelected(MouseEvent event) throws SQLException {
        inboxlist.getItems().clear();
        Label lbl = new Label("No messages from this user");
        inboxlist.setPlaceholder(lbl);

        messageBox.setText("");
        userObject user = usersNames.getSelectionModel().getSelectedItem();
        userObject userSelected = userController.loggedInUser;

        if (user == null)
            return;
        String userId = user.getId();
        var message = MessageController.getMessages(userId,userSelected.getId());
        for (var item : message) {
            String msg = item.getBody();
            String recipe = item.getRecipe_Id();
            var RecipeName = MessageController.getRecipeName(recipe);
            if (RecipeName != null) {
                String allMessages = (String.format("%s\n%s\n\n", RecipeName.toUpperCase(), msg));
                inboxlist.getItems().addAll(allMessages);
            } else {
                // Handle the case when RecipeName is null
                String allMessages = (String.format("%s\n%s\n\n", "Unknown Recipe", msg));
                inboxlist.getItems().addAll(allMessages);
            }
        }
    }

    // display the message in the label
    @FXML
    void DisplayTheMSG(MouseEvent event) {

        String msg = inboxlist.getSelectionModel().getSelectedItem();
        messageBox.setWrapText(true);
        messageBox.setText(msg);

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

        usersNames.getColumns().clear();


        TableColumn<userObject, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        usersNames.getColumns().add(nameColumn);
        usersNames.getItems().clear();
        usersNames.setItems(userList);

    }
}
