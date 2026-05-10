package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        var label = new Label("Hello, JavaFX!");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Calling launch from a method that doesn't extend Application 
        // helps avoid certain LinkageErrors in some IDE/Runtime configurations.
        Launcher.main(args);
    }
}

class Launcher {
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}