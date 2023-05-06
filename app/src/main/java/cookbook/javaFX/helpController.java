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
                "From the Home Screen Menu: \nClick the Add Menu option (Plus Icon). Fill in the recipe name, long description, short description and tags (either existing or custom). Click the 'Add new recipe' button and you're finished."),
        new HelpMain("How do I send a recipe to another person?",
                "From the Home Screen Menu: \nClick the Home icon. this takes you to a menu of your existing recipes. Click the recipe you would like to send, then click the Mail icon on the botton left. From here, click on the user you want to send a recipe to, and fill in an optional message. Once done, click 'Send' and you are finished."),
        new HelpMain("How do I check my messages?",
                "From the Home Screen Menu: \nClick the Messages icon. This takes you to your messages. Click the user you'd like to see your messages from, and they will then be shown in the 'Inbox'. Select the message you'd like to view, and it will appear in full in the 'Messages'."),
        new HelpMain("How do I add a recipe to my favorites?",
                "From the Recipes Menu: \nClick the recipe you'd like to Favourite, then click the Favouyrite button on the bottom of the recipes list (Heart Icon). This adds that recipe to your favourites."),
        new HelpMain("How do I see my favorite recipes?",
                "Favourited recipes can be viewed from the Recipes Menu by checking the 'Favourites only' checkbox."),
        new HelpMain("How do I search for a given ingredient?",
                "Recipes with a given ingredient can be searched for in the Recipes Menu. Simply type the ingredient name into the search box above the list of recipes and press Enter."),
        new HelpMain("How do I check my weekly list?",
                "From the Home Screen Menu: \nClick the Week List Menu option (Calendar Icon). This takes you to the Week List menu. From here, select the week you would like to view the meals for."),
        new HelpMain("How do I add a recipe to the weekly list?",
                "A recipe can be added to the Weekly List from the Recipes Menu.\nIn the Recipes Menu: \nSelect the recipe you would like to add, then click the 'WeekList' button. From here, select the date you would like to plan this meal on."),
        new HelpMain("How do I search by tag?",
                "Recipes with a given tag can be searched for in the Recipes Menu. Simply type the tag name into the search box above the list of recipes and press Enter."),
        new HelpMain("How do I create a new user?",
                "NOTE: Only Admins are able to create new Users.\nAs an admin, access the Admin Menu Option from the Home Screen Menu. From here, specify the name, username and password of the new user you want to create. Once done, hit the 'Add' button."),
        new HelpMain("How do I modify a user?",
                "NOTE: Only Admins can modify Users.\nAs an admin, click on the Admin Menu from the Home page. Select the user you want to modify and their details should appear in the respective text boxes. You can now edit these details and click 'Add' ")

);

private void showSearchresults() {
  String[] searchTerms = searchField.getText().split(" ");
  List<HelpMain> filteredInfo = Info.stream().filter(Info -> {
    for (String searchTerm : searchTerms) {
      if (!Info.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
        return false;
      }
    }
    return true;
  }).collect(Collectors.toList());

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

    helpContainer.getChildren().add(container);
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
  }

  }


  @FXML
  void searchAction(ActionEvent event) {
    showSearchresults();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
  scrollPane.getStyleClass().clear();
  showSearchresults();
  }
}

