package cookbook.javaFX;

import cookbook.objects.QuanitityIngredients;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ShoppingListViewCell extends ListCell<QuanitityIngredients> {
  @FXML
  private Label ingrString;

  @FXML
  private GridPane gridPane;

  private FXMLLoader loader;

  @Override
  protected void updateItem(QuanitityIngredients ingredient_obj, boolean empty) {
    super.updateItem(ingredient_obj, empty);

    if (empty || ingredient_obj == null) {
      ;
    } else {
      if (loader == null) {
        loader = new FXMLLoader(getClass().getResource("shoppingListCell.fxml"));
        loader.setController(this);

        try {
          loader.load();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      ingrString.setText(String.valueOf(ingredient_obj.toString()));
    }
    setText(null);
    setGraphic(gridPane);
  }
}