package com.medexam;

import com.medexam.ui.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        MainWindow mainWindow = new MainWindow();

        Scene scene = new Scene(mainWindow, 1100, 680);
        URL stylesheet = getClass().getResource("/styles.css");
        if (stylesheet == null) {
            Path sourceStylesheet = Path.of("src", "main", "resources", "styles.css");
            if (!Files.exists(sourceStylesheet)) {
                sourceStylesheet = Path.of("MedExamApp", "src", "main", "resources", "styles.css");
            }
            if (Files.exists(sourceStylesheet)) {
                try {
                    stylesheet = sourceStylesheet.toUri().toURL();
                } catch (java.net.MalformedURLException ignored) {
                }
            }
        }
        if (stylesheet != null) {
            scene.getStylesheets().add(stylesheet.toExternalForm());
        }

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
