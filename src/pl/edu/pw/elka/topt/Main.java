package pl.edu.pw.elka.topt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage.setTitle("TOPT-Projekt5");
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setMinHeight(400);
        stage.setMinWidth(800);
        stage.setFullScreen(true);
        stage.show();
    }
}
