package cookbook.javaFX;

import cookbook.objectControllers.userController;
import cookbook.objects.QuanitityIngredients;
import cookbook.objects.userObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

  public class ShoppingListController implements Initializable {

    @FXML
    private ListView<QuanitityIngredients> ingView;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button modifyBtn;
    @FXML
    private Button amounttUp;
    @FXML
    private Button amounttDown;
    @FXML
    private Label currentIng;
    @FXML
    private Label amount_text;

    private LocalDate startDateglobal;

    private ObservableList<QuanitityIngredients> ingredients = FXCollections.observableArrayList();
    private ObservableList<QuanitityIngredients> x = FXCollections.observableArrayList();

    //Clearing everything
    private void clear() {
      ingView.getItems().clear();
      ingredients.clear();
    }

  }
