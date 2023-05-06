package cookbook.javaFX;

import cookbook.objectControllers.recipeControler;
import cookbook.objects.ingredientObject;
import cookbook.objects.recipeObject;
import cookbook.objects.userObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import cookbook.objects.tagObject;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class homePage implements Initializable {

  @FXML
  public TextField search;
  @FXML
  public Text IngField;
  @FXML
  public TableView<recipeObject> recipeLists;

  public List<recipeObject> recipes;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      recipes = recipeControler.getRecpies();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    System.out.println(recipes.size() + "Size of elements");
    ObservableList<recipeObject> recipeList = FXCollections.observableArrayList(recipes);

    recipeLists.getColumns().clear();

    TableColumn<recipeObject, String> recipeNameColumn = new TableColumn<>("Name");
    recipeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    recipeLists.getColumns().add(recipeNameColumn);
    recipeLists.getItems().clear();
    recipeLists.setItems(recipeList);

    recipeLists.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() > 0) {
          recipeObject selectedRecipeObject = recipeLists.getSelectionModel().getSelectedItem();
          System.out.println("We out here ");
          System.out.println(selectedRecipeObject.getName());
          if (selectedRecipeObject != null) {
            System.out.println("We inside ");
            List<ingredientObject> ingredientObjects = selectedRecipeObject.getIngredientsList();
            System.out.println(ingredientObjects.size() + "inggg");
            System.out.println("We inside 3");
            StringBuilder sb = new StringBuilder();
            for (ingredientObject ingredient : ingredientObjects) {
              sb.append(ingredient.getName()).append(", \n");
              System.out.println(sb);
            }
            if (sb.length() > 2) {
              sb.setLength(sb.length() - 2);
            }
            IngField.setText(sb.toString());
          }
        }
      }
    });

  }


  public void searchMethod() throws SQLException, IOException {
    String searchTxt = search.getText();
    recipeControler controller = new recipeControler(); // create an instance of recipeControler
    List<recipeObject> recipes = controller.getRecpies(); // call the getRecpies()
    List<recipeObject> filteredRecipes = new ArrayList<>();

    String[] tagToSearch = searchTxt.split(",");
    for (recipeObject recipe : recipes) {
      List<tagObject> tags = recipe.getTagList();
      for (String tgToSearch : tagToSearch) {
        for (tagObject tag : tags) {

          // check if the recipe name, ingredients, or tags contain the search string
          if (tag.getTag_name().toLowerCase().contains(tgToSearch.trim().toLowerCase())) {
            if (!filteredRecipes.contains(recipe)) {
              // add the recipe to the filtered list if it hasn't been added already
              filteredRecipes.add(recipe);
            }
          }
        }
      }
    }
    // set the items of the TableView to the filtered list of recipes
    ObservableList<recipeObject> observableFilteredRecipes = FXCollections.observableArrayList(filteredRecipes);
    recipeLists.setItems(observableFilteredRecipes);
  }
}
