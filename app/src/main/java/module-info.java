module cookbook {
  requires transitive javafx.controls;
  requires transitive javafx.fxml;
  requires transitive java.sql;
  requires transitive javafx.graphics;
  requires transitive javafx.base;

  exports cookbook;
  opens cookbook to javafx.graphics;
  exports cookbook.javaFX to javafx.fxml;
}
