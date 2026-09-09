module com.example {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires javafx.graphics;

    opens com.example to javafx.fxml;
    exports com.example;
}
