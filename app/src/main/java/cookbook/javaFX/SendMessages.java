package cookbook.javaFX;

import cookbook.objects.userObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.ParseException;
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

    public TableView<userObject> userList;
    public TableColumn<userObject, String> displayNameColumn;
    public TableColumn<userObject, String> userColumn;

    // send message button
    @FXML
    void sendMessageAction(ActionEvent event) throws ParseException {

    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
