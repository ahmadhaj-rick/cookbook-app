package cookbook.javaFX;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import cookbook.objectControllers.recipeControler;
import cookbook.objectControllers.tagController;
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
  public ComboBox<tagObject> tagsDropdown;
  
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
  
  public List<ingredientObject> ingredients;
  public List<ingredientObject> selectedIngredients;
  public List<recipeObject> recipes;
  public List<tagObject> tags;
  public List<tagObject> selectedTags;
  
  public void createRecipe(ActionEvent event) throws SQLException, IOException {
    //For Recipe
    String recipe_Name = recipeName.getText();
    String shortDescription = recipeShortDesc.getText();
    String longDescription = recipeLongDesc.getText();
    String categorys = categoryName.getText();
    UUID uniqueRecipie = UUID.randomUUID();
    String recipeID = uniqueRecipie.toString();
    
    
    recipeObject createdRecipe = new recipeObject(recipeID, recipe_Name, shortDescription, "categorys", longDescription, false);
    try{
      recipeControler.addRecipe(recipe_Name, shortDescription, categorys, longDescription);
      
      //Two Loops that add all the selected ingredients into the recipe.
      for (ingredientObject ingredient : selectedIngredients) {
        createdRecipe.addIngredient(ingredient);
      }
      
      for (tagObject tag : selectedTags) {
        createdRecipe.addTag(tag);
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
  
  public void addTagToList(ActionEvent event) throws SQLException, IOException {
    if (tagsDropdown.getSelectionModel().getSelectedItem() == null) {
      String tag_Name = tagName.getText();
      UUID uniqueID = UUID.randomUUID();
      String tagID = uniqueID.toString();
      tagObject newTag = new tagObject(tagID, tag_Name);
      selectedTags.add(newTag);
      Alert success = new Alert(Alert.AlertType.INFORMATION);
      success.setTitle("Success!");
      success.setContentText("You successfully created a new tag!");
      success.show();
    } else if (tagsDropdown.getSelectionModel().getSelectedItem() != null) {
      String tag_name = tagsDropdown.getSelectionModel().getSelectedItem().getTag_name();
      UUID uniqueID = UUID.randomUUID();
      String tagID = uniqueID.toString();
      tagObject newTag = new tagObject(tagID, tag_name);
      selectedTags.add(newTag);
    }
  }
  
  public void addIngredientToList(ActionEvent event) throws SQLException, IOException {
    String ingredient_Name = ingredientName.getText();
    UUID uniqueID = UUID.randomUUID();
    String uniqueIngredientID = uniqueID.toString();
    ingredientObject newIngredientObject = new ingredientObject(uniqueIngredientID, ingredient_Name);
    selectedIngredients.add(newIngredientObject);
  }
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      recipes = recipeControler.getRecpies();
      selectedTags = new ArrayList<>();
      selectedIngredients = new ArrayList<>();
      // TO BE FIXED
      //categoryBox.getItems().addAll();
      //tags = tagController.getTags();
      
    } catch (SQLException err) {
      err.printStackTrace();
    }
    System.out.println(recipes.size());
    ObservableList<recipeObject> recipelist = FXCollections.observableArrayList(recipes);
    
  }
}
