package cookbook.javaFX;

import cookbook.objectControllers.recipeControler;
import cookbook.objects.recipeObject;
import cookbook.objects.userObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class homePage {

  @FXML
  public TextField search;
  @FXML
  public TableView<recipeObject> recipeLists;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    TableView<recipeObject> recipeLists = new TableView<>();
    TableColumn<recipeObject, String> recipeNameColumn = new TableColumn<>("Name");
    recipeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    recipeLists.getColumns().add(recipeNameColumn);

    List<recipeObject> recipes;
    try {
      recipes = recipeControler.getRecpies();
      ObservableList<recipeObject> recipeNames = FXCollections.observableArrayList();
      for (recipeObject recipe : recipes) {
        recipeNames.add(new recipeControler(recipe.getName()));
      }
      recipeLists.setItems(recipeNames);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  public void searchMethod() throws SQLException, IOException {
    String searchTxt = search.getText();
    recipeControler controller = new recipeControler(); // create an instance of recipeControler
    List<recipeObject> recipes = controller.getRecpies(); // call the getRecpies()
    List<recipeObject> filteredRecipes = new ArrayList<>();
    for (recipeObject recipe : recipes) {
      // check if the recipe name, ingredients, or tags contain the search string
      if (recipe.getName().toLowerCase().contains(searchTxt.toLowerCase())
              || recipe.getInstructions().toLowerCase().contains(searchTxt.toLowerCase())
              || recipe.getName_ingredient().toLowerCase().contains(searchTxt.toLowerCase())
              || recipe.getTag_name().toLowerCase().contains(searchTxt.toLowerCase()) ){
        if (!filteredRecipes.contains(recipe)) {
          // add the recipe to the filtered list if it hasn't been added already
          filteredRecipes.add(recipe);
        }
      }
    }
    // set the items of the TableView to the filtered list of recipes
    ObservableList<recipeObject> observableFilteredRecipes = FXCollections.observableArrayList(filteredRecipes);
    recipeLists.setItems(observableFilteredRecipes);
  }

}
