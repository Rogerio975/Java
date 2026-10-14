module com.medexam {
    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;
    exports com.medexam;
    exports com.medexam.ui;
    exports com.medexam.model;
    exports com.medexam.service;
}
