package cookbook.javaFX;

import cookbook.objectControllers.userController;
import cookbook.objects.QuanitityIngredients;
import cookbook.objects.userObject;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

  public class ShoppingListController implements Initializable {

    @FXML
    private ListView<QuanitityIngredients> ingView;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button modifyBtn;
    @FXML
    private Button amounttUp;
    @FXML
    private Button amounttDown;
    @FXML
    private Label currentIng;
    @FXML
    private Label amount_text;

    private LocalDate startDateglobal;

    private ObservableList<QuanitityIngredients> ingredients = FXCollections.observableArrayList();
    private ObservableList<QuanitityIngredients> x = FXCollections.observableArrayList();

    //Clearing everything
    private void clear() {
      ingView.getItems().clear();
      ingredients.clear();
    }

    public String stringRep() {
      StringBuilder s = new StringBuilder();
      for (QuanitityIngredients Quantity : ingView.getItems()) {
        s.append(Quantity.toData() + "\n");
      }
      String outstring = s.toString();
      return outstring;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      clear();
      ingView.setCellFactory(ingr -> new ShoppingListViewCell());
      ingView.setItems(ingredients);

      ingView.getSelectionModel().selectedItemProperty().addListener(
              new ChangeListener<QuanitityIngredients>() {
                @Override
                public void changed(ObservableValue<? extends QuanitityIngredients> ob, QuanitityIngredients oldQe, QuanitityIngredients newQe) {
                  selectQe(newQe);
                }
              });
    }

    public void getShoppingList(ObservableList<QuanitityIngredients> shoppingList, LocalDate ld) {
      startDateglobal = ld;
      List<QuanitityIngredients> shp = read();
      if ( shp == null) {
        ingredients.addAll(shoppingList);
      } else {
        ingredients.addAll(shp);
      }

    }

    public void selectQe(QuanitityIngredients quantity) {
      if (quantity != null){
        amount_text.setText(String.valueOf(quantity.getAmount()));
        currentIng.setText(quantity.getName());
      } else {
        return;
      }
    }

    @FXML
    public void onModifyBtn(ActionEvent event) {
      QuanitityIngredients quantity = ingView.getSelectionModel().getSelectedItem();
      if (quantity == null) {
        return;
      } else {
        qe.setAmount(Float.valueOf(amount_text.getText()));
        ingView.setItems(x);
        ingView.setItems(ingredients);
        save();
      }
    }

    @FXML
    public void onDeleteBtn (ActionEvent event) {
      QuanitityIngredients qe = ingView.getSelectionModel().getSelectedItem();
      if (qe == null) {
        return;
      } else {
        ingredients.remove(qe);
        ingView.setItems(x);
        ingView.setItems(ingredients);
        save();
      }
    }


  }
