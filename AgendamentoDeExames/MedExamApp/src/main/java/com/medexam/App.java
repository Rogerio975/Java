package com.medexam;

import com.medexam.ui.MainWindow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow();

        Scene scene = new Scene(mainWindow, 1100, 680);
        scene.getStylesheets().add(
            getClass().getResource("/styles.css").toExternalForm()
        );

        primaryStage.setTitle("MedExam — Sistema de Agendamento de Exames");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(580);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
