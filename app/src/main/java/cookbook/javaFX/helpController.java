package cookbook.javaFX;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import cookbook.objects.HelpMain;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class helpController implements Initializable {
  @FXML
  private VBox helpContainer;

  @FXML
  private TextField searchField;


  @FXML
  private ScrollPane scrollPane;

private final List<HelpMain> Info = Arrays.asList(
        new HelpMain("How to add a new recipe?",
                "To add a new recipe, \n click on the add recipe option from the main menu, which will navigate you to the adding recipe menu, you will be then able to provide all the relevant information for the desired recipe you want to add. When done, click on add recipe to save the recipe you added to your cookbook."),
        new HelpMain("How do I send a recipe to another person?",
                "To send a recipe: \n Click on the recipe you would like to send, then click the Mail icon on the bottom. Then, click on the user you would like to send the recipe to, and fill in an optional message. Once done, click 'Send' and you are finished."),
        new HelpMain("How do I check my messages?",
                "To check your messages: \\nClick the Messages icon. Which takes you to your messages menu. Click the user you'd like to see your messages from, which shows your inbox from them."),
        new HelpMain("How do I favorite a recipe?",
                "To favorite a recipe: \\nClick on the recipe you want to Favourite, then click the favorite button on the bottom of the recipes list."),
        new HelpMain("How do I see the recipes I have favorited?",
                "Favorite recipes can be inspected from the recipes menu by clicking the 'Favourites only' checkbox."),
        new HelpMain("How do I search for a given ingredient?",
                "To search for a specific ingredient: \n Simply type the ingredient name into the search box above the list of recipes and press the search button."),
        new HelpMain("How do I check my weekly list?",
                "To check your weekly list: \nSimply click on the Week List Menu option. This takes you to the Week List menu. From here, select the week you would like to view."),
        new HelpMain("How do I add a recipe to the weekly list?",
                "In the recipes menu.\nSelect the recipe you would like to add, then click the 'WeekList' button. Then, select the date you would like to plan this meal for."),
        new HelpMain("How do I search by tag?",
                "Simply type the tag name into the search bar above the list of recipes and press the search button."),
        new HelpMain("How do I create a new user?",
                "ADMIN ONLY:\nAs an admin, access the Admin Menu Option from the Home Screen Menu. From here, you can add a new user"),
        new HelpMain("How do I modify a user?",
                "ADMIN ONLY:\nAs an admin, click on the Admin Menu from the Home page. There, you are allowed to select users and modify them as you wish.")

);

private void showSearchresults() {
  String[] searchTerms = searchField.getText().split(" ");
  List<HelpMain> filteredInfo = Info.stream().filter(Info -> {
    for (String searchTerm : searchTerms) {
      if (Info.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
        return true;
      }
    }
    return false;
  }).collect(Collectors.toList());

  helpContainer.getChildren().clear();

  for (HelpMain Info :filteredInfo)
  {
    VBox container = new VBox();
    Text titleText = new Text(Info.getTitle());
    titleText.getStyle();
    container.getChildren().add(titleText);

    String bodyTxt = Info.getBody();
    String editedBody = bodyTxt.substring(0, Math.min(50, bodyTxt.length()));

    Text bodyElement = new Text(editedBody);
    bodyElement.setStyle("-fx-font-size: 14;");


    VBox.setMargin(container, new Insets(0, 0, 30, 0));

    if (editedBody.length() < bodyTxt.length()) {
      var isOpen = new Object() {
        boolean value = false;
      };

      Hyperlink openLink = new Hyperlink("...read more");

      openLink.setOnAction(e -> {
        if (!isOpen.value) {
          bodyElement.setText(bodyTxt);
          openLink.setText("hide");
        } else {
          bodyElement.setText(editedBody);
          openLink.setText("...read more");
        }
        isOpen.value = !isOpen.value;
      });
      TextFlow textFlow = new TextFlow(bodyElement, openLink);
      textFlow.setPadding(new Insets(10, 0, 0, 0));
      container.getChildren().add(textFlow);
    } else {
      TextFlow textFlow = new TextFlow(bodyElement);
      textFlow.setPadding(new Insets(10, 0, 0, 0));
      container.getChildren().add(textFlow);
    }

    helpContainer.getChildren().add(container);
    VBox.setMargin(container, new Insets(0, 0, 30, 0));
  }

  }

  @FXML
  void searchAction(ActionEvent event) {
    showSearchresults();
    System.out.println("We r here");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  scrollPane.getStyleClass().clear();
  showSearchresults();
  }
}

