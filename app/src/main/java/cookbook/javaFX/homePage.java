package cookbook.javaFX;

import cookbook.objectControllers.recipeControler;
import cookbook.objectControllers.userController;
import cookbook.objects.QuanitityIngredients;
import cookbook.objects.ingredientObject;
import cookbook.objects.recipeObject;
import cookbook.objects.userObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import cookbook.objects.tagObject;
import cookbook.objectControllers.CommentController;
import cookbook.objectControllers.ScheduledRecipeController;
import cookbook.objectControllers.userController;
import cookbook.objects.*;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

/**
 * Represents the home page controller.
 */

public class homePage implements Initializable {
  
  @FXML
  public TextField search;
  @FXML
  public Text IngField;
  @FXML
  public Label portionsLabel;
  @FXML
  public Button addToFavorite;
  @FXML
  public TableView<recipeObject> recipeLists;
  @FXML
  public CheckBox favoritecheck;
  @FXML
  private Text tagField;
  @FXML
  public Button back;
  @FXML
  public Label recipeName;
  @FXML
  public Text Shortdesc;
  @FXML
  public Text Longdesc;
  @FXML
  public TextField commentField;
  @FXML
  public ListView<CommentObject> allComments;

  
  @FXML
  private DatePicker myDatePicker;
  
  
  public List<recipeObject> recipes;
  
  int portions = 1;
  
  ObservableList<CommentObject> recipeCommentObjects = FXCollections.observableArrayList();

/**
 * Initializes the controller and sets up the initial state of the home page.
 * Retrieves recipes and populates the recipe list view.
 * @param location The location used to resolve relative paths.
 * @param resources The resources used to localize the root object.
 */
  
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    try {
      recipes = recipeControler.getRecpies();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    ObservableList<recipeObject> recipeList = FXCollections.observableArrayList(recipes);
    
    recipeLists.getColumns().clear();
    
    TableColumn<recipeObject, String> recipeNameColumn = new TableColumn<>("Name");
    recipeNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    
    recipeLists.getColumns().add(recipeNameColumn);
    recipeLists.getItems().clear();
    recipeLists.setItems(recipeList);
    
    recipeLists.setRowFactory(tv -> {
      TableRow<recipeObject> row = new TableRow<>();
      Tooltip tooltip = new Tooltip();
      
      row.hoverProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue && !row.isEmpty()) {
          recipeObject recipe = row.getItem();
          tooltip.setText(recipe.getDescription()); // Set the tooltip text to the recipe's description
          Tooltip.install(row, tooltip);
        }
      });
      
      return row;
    });

/**
 * Sets an event handler for mouse clicks on the recipe list view.
 * When a recipe is selected, it retrieves and displays detailed information about the selected recipe,
 * including ingredients, tags, descriptions, instructions, and comments.
 * @param event The mouse click event.
 */
    
    recipeLists.setOnMouseClicked(event -> {
      if (event.getClickCount() > 0) {
        recipeObject selectedRecipeObject = recipeLists.getSelectionModel().getSelectedItem();
        System.out.println("We out here ");
        System.out.println(selectedRecipeObject.getName());
        if (selectedRecipeObject != null) {
          System.out.println("We inside ");
          List<QuanitityIngredients> ingredientObjects = selectedRecipeObject.getIngredientsList();
          List<tagObject> tagObjects = selectedRecipeObject.getTagList();
          System.out.println(ingredientObjects.size() + "inggg");
          System.out.println("We inside 3");
          StringBuilder sb = new StringBuilder(); // ingredients
          StringBuilder sb2 = new StringBuilder(); // tags
          for (QuanitityIngredients ingredient : ingredientObjects) {
            sb.append(ingredient.getAmount() + ingredient.getUnit() + " " + ingredient.getName()).append(", \n");
            System.out.println(sb);
          }
          if (sb.length() > 2) {
            sb.setLength(sb.length() - 2);
          }
          for (tagObject tag : tagObjects) {
            sb2.append(tag.getTag_name()).append(", ");
          }
          tagField.setText(sb2.toString());
          
          IngField.setText(sb.toString());
          
          Shortdesc.setText(selectedRecipeObject.getDescription());
          Longdesc.setText(selectedRecipeObject.getInstructions());
          
          recipeName.setText(selectedRecipeObject.getName());

        recipeCommentObjects.clear();
        allComments.setCellFactory(commentList -> new CommentCell());
        allComments.setItems(recipeCommentObjects);
        recipeCommentObjects.addAll(selectedRecipeObject.getComments());
          
          
        }
      }
    });
    
    // Add a listener to the TableView selection model
    recipeLists.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      portions = 1; // Set the portions back to 1
      updateIngredientsText(); // Update the displayed ingredients
    });
  }

/**
 * Handles the event when the add comment button is clicked.
 * If a recipe is selected, it adds a new comment to the selected recipe.
 * The comment is added to the database and the comment objects are updated.
 * If no recipe is selected, it displays an error message.
 * @param event The action event triggered by clicking the add comment button.
 * @throws SQLException If an SQL exception occurs while adding the comment to the database.
 */
  
   @FXML
  public void addComment(ActionEvent event) throws SQLException {
    if (recipeLists.getSelectionModel().getSelectedItem() != null) {
      recipeObject recipe = recipeLists.getSelectionModel().getSelectedItem();
      UUID id = UUID.randomUUID();
      String commentID = id.toString();

      //Add to database and create objects.
      CommentObject comment = new CommentObject(commentID, commentField.getText(), userController.loggedInUser.getId(), recipe.getId());
      recipeCommentObjects.add(comment);
      recipe.addComment(comment);
      CommentController.addComment(commentID, commentField.getText(), userController.loggedInUser.getId(), recipeLists.getSelectionModel().getSelectedItem().getId());
      commentField.clear();

      Alert success = new Alert(Alert.AlertType.INFORMATION);
      success.setTitle("Success!");
      success.setContentText("You added a new comment.");
      success.show();
    } else if (recipeLists.getSelectionModel().getSelectedItem() == null) {
      commentField.clear();
      Alert failure = new Alert(Alert.AlertType.INFORMATION);
      failure.setTitle("Failure");
      failure.setContentText("Select a recipe first.");
      failure.show();
    }
  }

/**
 * Handles the event when the delete comment button is clicked.
 * If a recipe is selected and a comment is selected in the comment list,
 * it deletes the selected comment from the database and updates the comment objects.
 * Only the user who posted the comment or an admin can delete a comment.
 * If no recipe is selected or no comment is selected, it displays an error message.
 * @throws SQLException If an SQL exception occurs while deleting the comment from the database.
 */

  @FXML
  public void deleteComment() throws SQLException {
    if (recipeLists.getSelectionModel().getSelectedItem() != null) {
      recipeObject selectedRecipe = recipeLists.getSelectionModel().getSelectedItem();
      userObject currUser = userController.loggedInUser;
      if (allComments.getSelectionModel().getSelectedItem() != null) {
        CommentObject comment = allComments.getSelectionModel().getSelectedItem();
        if (currUser.getId().equals(comment.getUserId()) || currUser.getAdminPrivelages().equals(true)) {
          CommentController.deleteComment(comment.getUserId(), comment.getID());
          recipeCommentObjects.remove(comment);
          selectedRecipe.getComments().remove(comment);
          // Update the ListView here
          allComments.setItems(recipeCommentObjects);
          Alert success = new Alert(Alert.AlertType.INFORMATION);
          success.setTitle("Success!");
          success.setContentText("You deleted a comment.");
          success.show();
        } else {
          Alert fail = new Alert(Alert.AlertType.INFORMATION);
          fail.setTitle("Error!");
          fail.setContentText("You cannot delete a comment which isnt yours.");
          fail.show();
        }
      }
    }
  }

  @FXML
  public void editComment() throws SQLException {
    if (recipeLists.getSelectionModel().getSelectedItem() != null) {
      CommentObject comment = allComments.getSelectionModel().getSelectedItem();
      userObject currUser = userController.loggedInUser;
      if (comment != null) {
        if (currUser.getId().equals(comment.getUserId()) || currUser.getAdminPrivelages().equals(true)) {
          CommentController.editComment(comment.getID(), commentField.getText());
          comment.updateText(commentField.getText());
          Alert success = new Alert(Alert.AlertType.INFORMATION);
          success.setTitle("Success!");
          success.setContentText("You edited a comment.");
          success.show();
        } else {
          Alert fail = new Alert(Alert.AlertType.INFORMATION);
          fail.setTitle("Error!");
          fail.setContentText("You cannot edit a comment which isnt yours.");
          fail.show();
        }
      }
    }
  }

/**
 * Performs a search based on the entered search text.
 * Retrieves the list of recipes and filters them based on the search criteria.
 * The search criteria are matched against recipe ingredients and tags.
 * The filtered list of recipes is then set as the items of the TableView.
 * @throws SQLException If an SQL exception occurs while retrieving recipes.
 * @throws IOException  If an I/O exception occurs.
 */
  
  public void searchMethod() throws SQLException, IOException {
    String searchTxt = search.getText();
    recipeControler controller = new recipeControler(); // create an instance of recipeControler
    // Saving Arrays
    List<recipeObject> recipes = controller.getRecpies(); // Store recipes
    List<recipeObject> filteredRecipes = new ArrayList<>(); // filtered recipes
    
    
    String[] searchWord =searchTxt.split(",");
    
    for (recipeObject recipe : recipes) {
      List<QuanitityIngredients> ingredients = recipe.getIngredientsList();
      List<tagObject> tags = recipe.getTagList();
      boolean tagMatch = false;
      boolean ingMatch = false;
      
      for (String word : searchWord) {
        word = word.trim().toLowerCase();
        
        for (QuanitityIngredients ing : ingredients) {
          // check if the ingredient name contains the search string
          if (ing.getName().toLowerCase().contains(word)){
            ingMatch = true;
            break;
          }
        }
        
        for (tagObject tag : tags) {
          // check if the recipe has the tag.
          if (tag.getTag_name().toLowerCase().contains(word)) {
            tagMatch = true;
            break;
          }
        }
        
      }
      if (tagMatch || ingMatch) {
        filteredRecipes.add(recipe);
      }
    }
    // set the items of the TableView to the filtered list of recipes
    ObservableList<recipeObject> observableFilteredRecipes = FXCollections.observableArrayList(filteredRecipes);
    recipeLists.setItems(observableFilteredRecipes);
  }

/**
 * Updates the favorite status of the selected recipe.
 * Retrieves the selected recipe from the TableView and calls the recipeControler
 * to update its favorite status. Displays a confirmation message based on
 * the updated favorite status.
 * @throws SQLException If an SQL exception occurs while updating the favorite status.
 */
  
  public void updateFavorite() throws SQLException {
    try {
      recipeControler recipeController = new recipeControler();
      recipeObject selectedRecipe = recipeLists.getSelectionModel().getSelectedItem();
      recipeController.updateFavoriteStatus(selectedRecipe);
      System.out.println(selectedRecipe.getStar());
      
      if (selectedRecipe.getStar() == true) {
        Alert addedToFav = new Alert(AlertType.CONFIRMATION);
        addedToFav.setTitle("Added to Fav!!");
        addedToFav.setContentText("You have added this recipe to Favorite!");
        addedToFav.show();
      } else {
        Alert addedToFav = new Alert(AlertType.CONFIRMATION);
        addedToFav.setTitle("removed from Fav!!");
        addedToFav.setContentText("You have removed this recipe from Favorite!");
        addedToFav.show();
      }
      
    } catch (Exception e) {
      System.out.println(e + "fav method problem");
    }
    
    
  }
  
  
  /*public void favoriteRecipeList() throws SQLException {
    List<recipeObject> faveList = recipeControler.favoriteObjects();
    ObservableList<recipeObject> observableFavList = FXCollections.observableArrayList(faveList);
    recipeLists.setItems(observableFavList);
    
  }*/

/**
 * Retrieves the filtered list of recipes based on the favorite checkbox selection.
 * If the favorite checkbox is selected, it retrieves the favorite recipes using the recipeControler,
 * updates the TableView with the favorite recipes. If the checkbox is not selected,
 * it retrieves the normal recipes using the recipeControler and updates the TableView.
 * @param event The ActionEvent triggered by the favorite checkbox selection.
 * @throws SQLException If an SQL exception occurs while retrieving the recipes.
 */
  
  public void getFilteredRecipes(ActionEvent event) throws SQLException {
    CheckBox favoritecheck = (CheckBox) event.getSource();
    if (favoritecheck.isSelected()) {
      List<recipeObject> faveList = recipeControler.favoriteObjects();
      ObservableList<recipeObject> observableFavList = FXCollections.observableArrayList(faveList);
      recipeLists.setItems(observableFavList);
    } else {
      List<recipeObject> normalList = recipeControler.getRecpies();
      ObservableList<recipeObject> observablenormList = FXCollections.observableArrayList(normalList);
      recipeLists.setItems(observablenormList);
    }
  }
  
  // adjust the number of persons a recipe is
  private void updateIngredientsText() {
    StringBuilder ingredientsString = new StringBuilder();
    recipeObject recp = recipeLists.getSelectionModel().getSelectedItem();
    for (QuanitityIngredients quantifiedIngredient : recp.getIngredientsList()) {
      float ingredientAmount = quantifiedIngredient.getAmount() * portions;
      String ingredientUnit = quantifiedIngredient.getUnit() != null ? " " + quantifiedIngredient.getUnit() : "";
      String ingredientName = quantifiedIngredient.getIngredient().getName();
      ingredientsString.append(String.format("%s%s %s\n", ingredientAmount, ingredientUnit, ingredientName));
    }
    IngField.setText(ingredientsString.toString());
    portionsLabel.setText(String.valueOf(portions));
  }

/**
 * Decreases the number of portions and updates the displayed ingredients.
 * If the current number of portions is greater than 1, it decrements the 'portions' variable by 1 and calls the 'updateIngredientsText()' method.
 *
 * @param event The action event that triggered the method.
 */

  @FXML
  void onDecreasePortions(ActionEvent event) {
    if (portions > 1) {
      portions--;
      updateIngredientsText();
    }
  }

/**
 * Increases the number of portions and updates the displayed ingredients.
 * It increments the 'portions' variable by 1 and calls the 'updateIngredientsText()' method.
 *
 * @param event The action event that triggered the method.
 */
  
  @FXML
  void onIncreasePortions(ActionEvent event) {
    portions++;
    updateIngredientsText();
  }

/**
 * Handles the action when the back button is clicked.
 * It loads the mainmenu.fxml file, sets it as the root of the scene, and displays the scene in the main stage.
 * It also sets the stage dimensions, title, and centers it on the screen.
 * Additionally, it retrieves the logged-in user's name and includes it in the title.
 *
 * @param event The action event that triggered the method.
 * @throws SQLException If there is an SQL-related error.
 * @throws IOException  If there is an I/O error.
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
  
  // weeklyList DatePicker
  @FXML
  void datePicker(ActionEvent event) {
    LocalDate localDate = myDatePicker.getValue();
    localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }
  
  // Add date to the week-list table
  @FXML
  void addToWeekList(ActionEvent event) {
    recipeObject recipe = recipeLists.getSelectionModel().getSelectedItem();

    try {
      if (recipe == null) {
        throw new NullPointerException(); // Throw exception if no recipe is selected
      }

      LocalDate localDate = myDatePicker.getValue();
      Timestamp timestamp = Timestamp.valueOf(localDate.atTime(LocalTime.MIDNIGHT));
      System.out.println("Me as a button, I function lol"); //2019-05-16 00:00:00.0
      System.out.println(timestamp);

      userObject loggedInUser = userController.loggedInUser;
      recipeControler.adddate(recipe.getId(), timestamp, loggedInUser.getId());

      // Show success message
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Success");
      alert.setContentText("The recipe has been added to your weekly list.");
      alert.show();

    } catch (NullPointerException e) {
      // Show error message for no recipe selected
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Error");
      alert.setContentText("Please select a recipe.");
      alert.show();
    } catch (Exception e) {
      // Show generic error message
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Error");
      alert.setContentText("An error occurred while adding the recipe to your weekly list.");
      alert.show();
    }
  }

/**
 * Handles the action when the send message button is clicked.
 * Retrieves the selected recipe from the recipeLists and performs the following actions if a recipe is selected:
 * - Loads the sendMessage.fxml file, sets it as the root of a new scene, and displays the scene in a new stage.
 * - Passes the recipe ID and name to the SendMessages controller using its methods.
 * - Creates a new stage for the sendMessage scene and sets the existing stage as its owner.
 * - Sets the title of the new stage based on the logged-in user's name.
 * - Shows the new stage and displays an error message if no recipe is selected.
 *
 * @param event The action event that triggered the method.
 * @throws SQLException If there is an SQL-related error.
 * @throws IOException  If there is an I/O error.
 */

  public void sendMessage(ActionEvent event) throws SQLException, IOException {
    recipeObject recp = recipeLists.getSelectionModel().getSelectedItem();
    if (recipeLists.getSelectionModel().getSelectedItem() != null){

      URL url = new File("src/main/java/cookbook/resources/sendMessage.fxml").toURI().toURL();
      FXMLLoader loader = new FXMLLoader(url);
      Parent root = loader.load();
      Scene sendMessage = new Scene(root);

      // pass the id
      SendMessages setController = loader.getController();
      setController.passInformation(recp.getId());

      // pass the name
      SendMessages setControllerTwo = loader.getController();
      setControllerTwo.passNameInformation(recp.getName());

      Stage newStage = new Stage();
      newStage.setScene(sendMessage);

      // Get the reference to the existing stage
      Stage existingStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

      // Set the existing stage as the owner of the new stage
      newStage.initOwner(existingStage);
      newStage.getIcons().add(new Image(new FileInputStream("src/main/java/cookbook/resources/images/sendMessage.png")));

      userController user = new userController();
      String name = user.loggedInUser.getName();
      newStage.setTitle("You want to share something what a great idea " + name );

      // Show the new stage
      newStage.show();

    } else {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Error");
      alert.setContentText("Please select a recipe");
      alert.show();
    }

  }


}

