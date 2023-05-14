package cookbook.javaFX;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WeeklyListController {
  @FXML
  private Label Weeklabel;

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
  }

  public void weekChanged(String newValue) {
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

    }
  }
}
