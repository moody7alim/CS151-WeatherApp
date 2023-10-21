module com.example.ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.ui to javafx.fxml;
    exports com.example.ui;
}