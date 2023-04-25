module cookbook {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires javafx.graphics;

  exports cookbook;
  opens cookbook to javafx.graphics, javafx.fxml;
}
