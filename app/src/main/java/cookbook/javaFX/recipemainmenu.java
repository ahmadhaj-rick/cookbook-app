package cookbook.javaFX;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import cookbook.objectControllers.recipeControler;
import cookbook.objects.ingredientObject;
import cookbook.objects.recipeObject;
import cookbook.objects.tagObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class recipemainmenu implements Initializable{

    @FXML
    public TextField recipeName;
    
    @FXML
    public TextField recipeShortDesc;

    @FXML
    public TextField recipeLongDesc;

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
    public Button addRecipie;

    public List<ingredientObject> ingredients;
    public List<recipeObject> recipes;
    public List<tagObject> tags;

    public void createRecipe(ActionEvent event) throws SQLException, IOException {
        String name = recipeName.getText();
        String sdesc = recipeShortDesc.getText();
        String desc = recipeLongDesc.getText();
        String tname = tagName.getText();
        String ingredient = ingredientName.getText();
        String categorys = categoryName.getText();
        UUID uniqueID = UUID.randomUUID();
        String tagID = uniqueID.toString();
        UUID uniqeID = UUID.randomUUID();
        String ingredientID = uniqeID.toString();
        recipeObject s = new recipeObject(ingredientID, tname, sdesc, categorys, desc, null);
        ingredientObject m = new ingredientObject(ingredientID, ingredient);
        tagObject n = new tagObject(tagID, tname);

        try{
            recipeControler.addRecipe(name, sdesc, categorys, desc);
            s.addIngredient(m);
            s.addTag(n);
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Success!");
            success.setContentText("You successfully created a new recipe!");
            success.show();  
        } catch (SQLException x) {
            Alert failure = new Alert(Alert.AlertType.INFORMATION);
            failure.setTitle("Success!");
            failure.setContentText(x.toString());
            failure.show();
          }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            recipes = recipeControler.getRecpies();
        } catch (SQLException err) {
            err.printStackTrace();
        }
        System.out.println(recipes.size());
        ObservableList<recipeObject> recipelist = FXCollections.observableArrayList(recipes);

    }
}
