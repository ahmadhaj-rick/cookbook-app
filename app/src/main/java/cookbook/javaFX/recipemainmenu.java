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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class recipemainmenu implements Initializable{
  
  @FXML
  public TextField recipeName;
  
  @FXML
  public TextArea recipeShortDesc;
  
  @FXML
  public TextArea recipeLongDesc;
  
  @FXML
  public ComboBox<String> tagsDropdown;
  
  @FXML
  public ComboBox<recipeObject> categoryBox;
  
  @FXML
  public TextField tagName;
  
  @FXML
  public Button addTag;
  
  @FXML
  public TextField ingredientName;
  
  @FXML
  public TextField categoryName;
  
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
  
  public List<recipeObject> recipes;

  public List<ingredientObject> ingredients;
  public List<QuanitityIngredients> selectedIngredients;

  public List<tagObject> tags;
  public List<tagObject> selectedTags;

  
  public void createRecipe(ActionEvent event) throws SQLException, IOException {
    //For Recipe
    String recipe_Name = recipeName.getText();
    String shortDescription = recipeShortDesc.getText();
    String longDescription = recipeLongDesc.getText();
    UUID uniqueRecipie = UUID.randomUUID();
    String recipeID = uniqueRecipie.toString();
    
   
    try{
      recipeControler.addRecipe(recipeID, recipe_Name, shortDescription, longDescription);
      recipeObject createdRecipe = new recipeObject(recipeID, recipe_Name, shortDescription, longDescription, false);
      
      //Two Loops that add all the selected ingredients into the recipe.
      for (QuanitityIngredients ingredient : selectedIngredients) {
        createdRecipe.addIngredient(ingredient);
        ingredientControler.addIngredientToRecipe(recipeID, ingredient.ingredientID(), ingredient.getUnit(), ingredient.getAmount());
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
  
  public void backButton(ActionEvent event) throws SQLException, IOException {
    URL url = new File("src/main/java/cookbook/resources/mainmenu.fxml").toURI().toURL();
    FXMLLoader loader = new FXMLLoader(url);
    Parent root = loader.load();
    Scene loginScene = new Scene(root);
    
    Stage mainStage = (Stage) back.getScene().getWindow();
    mainStage.setScene(loginScene);
    mainStage.show();
    mainStage.setHeight(700);
    mainStage.setWidth(1000);
    mainStage.setResizable(true);
    mainStage.centerOnScreen();
  }

  /**
   * Method for adding the tags to the temporary list.
   * We will add all of the tags within that list to the recipe later on.
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
  }
  
  /**
   * The add ingredient button, when button is pressed, add the ingredient to the recipe.
   */

  public void addIngredientToList(ActionEvent event) throws SQLException, IOException {
    try {
      String ingredient_Name = ingredientName.getText();
      UUID uniqueID = UUID.randomUUID();
      String uniqueIngredientID = uniqueID.toString();
      String selectedUnit = unit.getSelectionModel().getSelectedItem();
      String a = amount.getText();
      float selectedAmount = Float.parseFloat(a);

      //add the ingredient to the database aswell as creating an object.
      ingredientControler.addIngredient(uniqueIngredientID, ingredient_Name);
      ingredientObject newIngredientObject = new ingredientObject(uniqueIngredientID, ingredient_Name);
      //add the ingredient to our QuantityIngredient object and then add the object to the list.
      QuanitityIngredients newQuanitityIngredients = new QuanitityIngredients(selectedUnit, selectedAmount, newIngredientObject);
      selectedIngredients.add(newQuanitityIngredients);

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
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      recipes = recipeControler.getRecpies();
      selectedTags = new ArrayList<>();
      selectedIngredients = new ArrayList<>();
      tags = new ArrayList<>();
      
      updateTagBox();
      unit.setItems(FXCollections.observableArrayList(null, "g", "kg", "ml", "L", "mg", "tea spoon", "pinch"));

    } catch (SQLException err) {
      err.printStackTrace();
    }
    System.out.println(recipes.size());

    
  }
}
