package cookbook.javaFX;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

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
}
