
package com.medexam;

import com.medexam.ui.MainWindow;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App {

    public static void main(String[] args) {
        Platform.startup(() -> {
            MainWindow mainWindow = new MainWindow();

            Scene scene = new Scene(mainWindow, 1100, 680);
            scene.getStylesheets().add(
                App.class.getResource("/styles.css").toExternalForm()
            );

            Stage primaryStage = new Stage();
            primaryStage.setTitle("MedExam — Sistema de Agendamento de Exames");
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(900);
            primaryStage.setMinHeight(580);
            primaryStage.show();
        });
    }
}
