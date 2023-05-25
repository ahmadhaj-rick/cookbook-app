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

      // Clear the ingredients list
      ingredients.clear();

      // Iterate over the shoppingList and add ingredients to the ingredients list,
      // checking for duplicates and keeping only one occurrence
      for (QuanitityIngredients ingredient : shoppingList) {
        boolean isDuplicate = false;
        for (QuanitityIngredients existingIngredient : ingredients) {
          if (ingredient.getName().equals(existingIngredient.getName())) {
            isDuplicate = true;
            break;
          }
        }
        if (!isDuplicate) {
          ingredients.add(ingredient);
        }
      }

      // Update the ingView with the updated ingredients list
      ingView.setItems(ingredients);
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
        quantity.setAmount(Float.valueOf(amount_text.getText()));
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

    @FXML
    public void onUpButton(ActionEvent event) {
      QuanitityIngredients qe = ingView.getSelectionModel().getSelectedItem();
      if (qe == null) {
        return;
      } else {
        Float currentAmt = Float.valueOf(amount_text.getText());
        String newAmt = String.valueOf(currentAmt+1);
        amount_text.setText(newAmt);
      }
    }

    @FXML
    public void onDownButton(ActionEvent event) {
      QuanitityIngredients qe = ingView.getSelectionModel().getSelectedItem();
      if (qe == null) {
        return;
      } else {
        Float currentAmt = Float.valueOf(amount_text.getText());
        if (currentAmt > 0) {
          String newAmt = String.valueOf(currentAmt-1);
          amount_text.setText(newAmt);
        } else {
          amount_text.setText("0");
        }
      }
    }

    public void save() {
      String pathdate = startDateglobal.toString();
      userObject user = userController.loggedInUser;
      String userId = user.getId();
      String basePath = "generatedDinnerList";
      String folderPath = basePath + "/" + userId;
      String fullPath = folderPath + "/" + pathdate + ".data";

      try {
        File folder = new File(folderPath);
        if (!folder.exists()) {
          folder.mkdirs();
        }

        File file = new File(fullPath);
        file.createNewFile();

        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        BufferedWriter bwriter = new BufferedWriter(out);
        bwriter.write(stringRep());
        bwriter.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    public List<QuanitityIngredients> read() {
      String datePath = startDateglobal.toString();
      userObject user = userController.loggedInUser;
      String userId = user.getId();
      String basePath = "generatedDinnerList";
      String fullPath = basePath + "/" + userId + "/" + datePath + ".data";

      List<QuanitityIngredients> x = new ArrayList<>();

      try {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fullPath);
        if (inputStream != null) {
          Scanner scanner = new Scanner(inputStream, "utf-8");

          if (!scanner.hasNextLine()) {
            scanner.close();
            return null;
          }

          String line = scanner.nextLine();
          String[] data = line.split(":");
          if (!data[0].equals("INGREDIENT")) {
            scanner.close();
            return null;
          }

          QuanitityIngredients ingredient = new QuanitityIngredients(data[2], Float.valueOf(data[1]), data[3]);
          x.add(ingredient);

          while (scanner.hasNext()) {
            String ingredientLine = scanner.nextLine();
            data = ingredientLine.split(":");

            if (data[0].equals("INGREDIENT")) {
              ingredient = new QuanitityIngredients(data[2], Float.valueOf(data[1]), data[3]);
              x.add(ingredient);
            }
          }
          scanner.close();
          return x;
        } else {
          return null;
        }
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }
  }
