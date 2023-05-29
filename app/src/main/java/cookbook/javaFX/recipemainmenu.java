package cookbook.javaFX;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

import cookbook.objectControllers.ingredientControler;
import cookbook.objectControllers.recipeControler;
import cookbook.objectControllers.tagController;
import cookbook.objects.QuanitityIngredients;
import cookbook.objects.ingredientObject;
import cookbook.objects.recipeObject;
import cookbook.objects.tagObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class recipemainmenu implements Initializable {

  @FXML
  public TextField recipeName;

  @FXML
  public TextArea recipeShortDesc;

  @FXML
  public TextArea recipeLongDesc;

  @FXML
  public ComboBox<String> tagsDropdown;

  @FXML
  public TextField tagName;

  @FXML
  public Button addTag;

  @FXML
  private TextField fromUser;

  @FXML
  private TextField toUser;

  @FXML
  public TextField ingredientName;

  @FXML
  public Button addIngredient;

  @FXML
  public Button back;

  @FXML
  public Button addRecipie;

  @FXML
  public TextField amount;

  @FXML
  public ComboBox<String> unit;

  @FXML
  public Label tagsLabel;

  @FXML
  public Label ingredientLabel;

  public List<recipeObject> recipes;

  public List<ingredientObject> ingredients;
  public List<QuanitityIngredients> selectedIngredients;

  public List<tagObject> tags;
  public List<tagObject> selectedTags;

/**
 * Creates a new recipe based on the input provided by the user.
 * Retrieves the recipe name, short description, and long description from the corresponding text fields.
 * Generates a unique recipe ID using UUID.randomUUID().
 * Adds the recipe to the recipeController and creates a new recipeObject.
 * Iterates over the selected ingredients and adds them to the created recipe.
 * Iterates over the selected tags and adds them to the created recipe.
 * Displays a success message if the recipe is successfully created.
 * Displays an error message if an SQLException occurs.
 *
 * @param event The ActionEvent that triggered the method.
 * @throws SQLException If an error occurs while accessing the database.
 * @throws IOException  If an error occurs during I/O operations.
 */

  public void createRecipe(ActionEvent event) throws SQLException, IOException {
    // For Recipe
    String recipe_Name = recipeName.getText();
    String shortDescription = recipeShortDesc.getText();
    String longDescription = recipeLongDesc.getText();
    UUID uniqueRecipie = UUID.randomUUID();
    String recipeID = uniqueRecipie.toString();

    try {
      recipeControler.addRecipe(recipeID, recipe_Name, shortDescription, longDescription);
      recipeObject createdRecipe = new recipeObject(recipeID, recipe_Name, shortDescription, longDescription, false);

      // Two Loops that add all the selected ingredients into the recipe.
      for (QuanitityIngredients ingredient : selectedIngredients) {
        createdRecipe.addIngredient(ingredient);
        ingredientControler.addIngredientToRecipe(recipeID, ingredient.ingredientID(), ingredient.getUnit(),
            ingredient.getAmount());
      }

      System.out.println(createdRecipe.getIngredientsList());

      for (tagObject tag : selectedTags) {
        createdRecipe.addTag(tag);
        tagController.addTagToRecipe(recipeID, tag.getTag_id());
      }

      Alert success = new Alert(Alert.AlertType.INFORMATION);
      success.setTitle("Success!");
      success.setContentText("You successfully created a new recipe!");
      success.show();
    } catch (SQLException x) {
      Alert failure = new Alert(Alert.AlertType.INFORMATION);
      failure.setTitle("Faliure!");
      failure.setContentText(x.toString());
      failure.show();
    }
  }

/**
 * Returns to the main menu screen when the back button is clicked.
 * Loads the main menu scene and sets it as the current scene.
 * Displays the main menu stage.
 * Sets the dimensions and properties of the main menu stage.
 *
 * @param event The ActionEvent that triggered the method.
 * @throws SQLException If an error occurs while accessing the database.
 * @throws IOException  If an error occurs during I/O operations.
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
    mainStage.setResizable(false);
    mainStage.centerOnScreen();
  }

  /**
   * Method for adding the tags to the temporary list.
   * We will add all the tags within that list to the recipe later on.
   */
  
  /**
 * Adds the selected tag to the temporary list of tags.
 * Retrieves the tag name from the text field or the dropdown menu.
 * Generates a unique tag ID using UUID.randomUUID().
 * Checks if the tag already exists in the list, and displays an error message if it does.
 * Adds the new tag to the tagController and creates a new tagObject.
 * Displays a success message if the tag is successfully created.
 *
 * @param event The ActionEvent that triggered the method.
 * @throws SQLException If an error occurs while accessing the database.
 * @throws IOException  If an error occurs during I/O operations.
 */

  public void addTagToList(ActionEvent event) throws SQLException, IOException {
    if (tagsDropdown.getSelectionModel().getSelectedItem() == null) {
      String tag_Name = tagName.getText();
      UUID uniqueID = UUID.randomUUID();
      String tagID = uniqueID.toString();

      //If duplicate, do this. Else, do that.
      if (findTag(tag_Name) != null) {
        //If tag already exists, show this.
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error!");
        error.setContentText("Tag already exists!");
        error.show();
      } else {
        // Add the tag to the database and create an object.
        tagController.addTag(tagID, tag_Name);
        tagObject newTag = new tagObject(tagID, tag_Name);
        selectedTags.add(newTag);

        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success!");
        success.setContentText("You successfully created a new tag!");
        success.show();
        updateTagBox();  
        
      }



      //If something is selected, use that one. 
    } else if (tagsDropdown.getSelectionModel().getSelectedItem() != null) {
      String tag_name = tagsDropdown.getSelectionModel().getSelectedItem();
      tagObject myTag = findTag(tag_name);

      if (myTag != null && !selectedTags.contains(myTag)) {
        selectedTags.add(myTag);
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success!");
        success.setContentText("You successfully added a new tag!");
        success.show(); 
      }
    }
    
    tagsDropdown.setValue(null);
    tagName.setText("");
    updateTagsLabel();
  }

  /**
   * The add ingredient button, when button is pressed, add the ingredient to the
   * recipe.
   */

  public void addIngredientToList(ActionEvent event) throws SQLException, IOException {
    try {
      String ingredient_Name = ingredientName.getText();
      UUID uniqueID = UUID.randomUUID();
      String uniqueIngredientID = uniqueID.toString();
      String selectedUnit = unit.getSelectionModel().getSelectedItem();
      String a = amount.getText();
      float selectedAmount = Float.parseFloat(a);

      // add the ingredient to the database aswell as creating an object.
      ingredientControler.addIngredient(uniqueIngredientID, ingredient_Name);
      ingredientObject newIngredientObject = new ingredientObject(uniqueIngredientID, ingredient_Name);
      // add the ingredient to our QuantityIngredient object and then add the object
      // to the list.
      QuanitityIngredients newQuanitityIngredients = new QuanitityIngredients(selectedUnit, selectedAmount,
          newIngredientObject);
      selectedIngredients.add(newQuanitityIngredients);

      // Append the ingredient name to the ingredientLabel
      String currentLabelText = ingredientLabel.getText();
      if (currentLabelText.isEmpty()) {
        ingredientLabel.setText(ingredient_Name);
      } else {
        ingredientLabel.setText(currentLabelText + ", " + ingredient_Name);
      }

      Alert success = new Alert(Alert.AlertType.INFORMATION);
      success.setTitle("Success!");
      success.setContentText("You successfully created a new ingredient!");
      success.show();
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

/**
 * Two methods for handling the comboboxes aswell as finding tags.
 */

  public void updateTagBox() throws SQLException {
    tagsDropdown.getItems().clear();
    tagsDropdown.getItems().add(null);
    for (tagObject tag : tagController.getTags()) {
      String tagname = tag.getTag_name();
      tagsDropdown.getItems().add(tagname);
    }
    
  }

  public tagObject findTag(String tagName) throws SQLException {
    for (tagObject tag : tagController.getTags()) {
      if (tag.getTag_name().equals(tagName)) {
        return tag;
      }
    }    
    return null;
  }

  /**
   * Recipe stuff.
   */

  /**
 * Initializes the controller when the corresponding view is loaded.
 * Retrieves the recipes, initializes the lists for selected tags and ingredients,
 * and initializes the tags list. Updates the tag box and sets the items for the unit ComboBox.
 * Prints the size of the recipes list.
 *
 * @param location  The location used to resolve relative paths for the root object,
 *                  or null if the location is not known.
 * @param resources The resources used to localize the root object,
 *                  or null if the root object was not localized.
 */

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      recipes = recipeControler.getRecpies();
      selectedTags = new ArrayList<>();
      selectedIngredients = new ArrayList<>();
      tags = new ArrayList<>();

      updateTagBox();
      unit.setItems(FXCollections.observableArrayList("g", "kg", "ml", "L", "mg", "tea spoon", "pinch"));


    } catch (SQLException err) {
      err.printStackTrace();
    }
    System.out.println(recipes.size());

  }

/**
 * Updates the tags label to display the names of the selected tags.
 * Retrieves the tag names from the selectedTags list using stream and map operations.
 * Concatenates the tag names into a comma-separated string.
 * Sets the resulting string as the text of the tagsLabel.
 */

  private void updateTagsLabel() {
    List<String> tagNames = selectedTags.stream().map(tagObject::getTag_name).collect(Collectors.toList());
    tagsLabel.setText(String.join(", ", tagNames));
  }
}
