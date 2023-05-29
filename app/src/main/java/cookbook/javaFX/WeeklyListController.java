package cookbook.javaFX;
import cookbook.objectControllers.ScheduledRecipeController;
import cookbook.objectControllers.WeekCalendar;
import cookbook.objectControllers.userController;
import cookbook.objects.QuanitityIngredients;
import cookbook.objects.ScheduledRecipeObject;
import cookbook.objects.userObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Controller for the weekly list view.
 */

public class WeeklyListController implements Initializable {
  @FXML
  private ListView<String> fridayListView;
  @FXML
  private ListView<String> mondayListView;
  @FXML
  private ListView<String> saturdayListView;
  @FXML
  private VBox weekSelection;
  @FXML
  private ListView<String> sundayListView;
  @FXML
  private ListView<String> thursdayListView;
  @FXML
  private ListView<String> tuesdayListView;
  @FXML
  private ListView<String> WednesdayListView;
  @FXML
  private Button openShopp;
  @FXML
  private ComboBox<String> weeksComboxBox;
  @FXML
  private Button back;

  private ObservableList<QuanitityIngredients> shoppingList = FXCollections.observableArrayList();
  private LocalDate initialDateGlobal;

  public static List<LocalDate> getDatesBetween(
          LocalDate startDate, LocalDate endDate) {

    long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate) + 1;
    return IntStream.iterate(0, x -> x + 1)
            .limit(numOfDaysBetween)
            .mapToObj(x -> startDate.plusDays(x))
            .collect(Collectors.toList());
  }

  //Clearing all data to initialize
  public void clearAll() {
    mondayListView.getItems().clear();
    tuesdayListView.getItems().clear();
    WednesdayListView.getItems().clear();
    thursdayListView.getItems().clear();
    fridayListView.getItems().clear();
    saturdayListView.getItems().clear();
    sundayListView.getItems().clear();
    shoppingList.clear();
  }

    /**
     * Handles the back button action.
     *
     * @param event the action event
     * @throws SQLException if an SQL exception occurs
     * @throws IOException  if an I/O exception occurs
     */

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
    mainStage.setResizable(true);
    mainStage.centerOnScreen();
    userObject user = userController.loggedInUser;
    String name = user.getName();
    mainStage.setTitle("Welcome back to the main menu dear " + name );

  }

/**
 * Updates the view and performs actions when the week is changed.
 *
 * @param newValue the new value representing the selected week
 * @throws SQLException if an error occurs while accessing the database
 */
  
  public void weekChanged(String newValue) throws SQLException {
    clearAll();
    initialDateGlobal = LocalDate.parse(newValue.split(" - ")[0]);
    LocalDate endingDate = LocalDate.parse(newValue.split(" - ")[1]);
    List<LocalDate> datesInBetween = getDatesBetween(initialDateGlobal, endingDate);

    for (LocalDate date : datesInBetween) {
      String str = String.valueOf(date);
      LocalDate localDate = LocalDate.parse(str.toString());
      String day = String.valueOf(localDate.getDayOfWeek());
      ListView<String> currentListView;

      if (day.equals("MONDAY")) {
        currentListView = mondayListView;
      } else if (day.equals("TUESDAY")) {
        currentListView = tuesdayListView;
      } else if (day.equals("WEDNESDAY")) {
        currentListView = WednesdayListView;
      } else if (day.equals("THURSDAY")) {
        currentListView = thursdayListView;
      } else if (day.equals("FRIDAY")) {
        currentListView = fridayListView;
      } else if (day.equals("SATURDAY")) {
        currentListView = saturdayListView;
      } else {
        currentListView = sundayListView;
      }

      List<ScheduledRecipeObject> dateSchedule = ScheduledRecipeController.getDateSchedule(Date.valueOf(date));
      if (dateSchedule == null) {
        ;
      } else {
        for (ScheduledRecipeObject entity : dateSchedule) {
          currentListView.getItems().add(entity.getRecipeName());
          for (QuanitityIngredients qe : entity.getIngredients()) {
            if (shoppingList.contains(qe)) {
              int index = shoppingList.indexOf(qe);
              shoppingList.get(index).addAmount(qe.getAmount());
            } else {
              shoppingList.add(qe);
            }
          }
        }
      }
    }
  }

/**
 * Initializes the controller class.
 *
 * @param location  the location used to resolve relative paths for the root object, or null if the location is not known
 * @param resources the resources used to localize the root object, or null if the root object was not localized
 */

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    ObservableList<String> weekList = FXCollections.observableArrayList(WeekCalendar.getFollowingWeeks(11));
    weeksComboxBox.setItems(weekList);
    weeksComboxBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
      public void changed(ObservableValue<? extends String> ov,
                          final String oldvalue, final String newvalue) {
        try {
          weekChanged(newvalue);
        } catch (SQLException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }

/**
 * Opens the shopping list.
 *
 * @param event the action event triggering the method
 * @throws IOException if an error occurs while loading the FXML file
 */
  
  @FXML
  void openShoppingList(ActionEvent event) throws IOException {
   if (!shoppingList.isEmpty() && initialDateGlobal!=null) {
      URL url = new File("src/main/java/cookbook/resources/shoppingList.fxml").toURI().toURL();
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(url);
      loader.load();
      Parent p = loader.getRoot();

      // Pass the shopping list to next controller
      ShoppingListController shoppingController = loader.getController();
      shoppingController.getShoppingList(shoppingList, initialDateGlobal);

      Stage stage = new Stage();
      stage.setScene(new Scene(p));
      stage.setTitle("Shopping List");

      stage.show();
    } else {

      if (weeksComboxBox.getSelectionModel().getSelectedItem() == null) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("NOTE");
        alert.setContentText("Please select a Week");
        alert.show();
      } else {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("NOTE");
        alert.setContentText("No shopping list for this Week.");
        alert.show();
      }
    }
  }

}

