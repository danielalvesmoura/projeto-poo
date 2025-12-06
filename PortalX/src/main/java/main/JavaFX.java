package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import main.controller.menuPrincipal.novo.MenuPrincipalController;

import java.io.IOException;

public class JavaFX extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {

        this.stage = primaryStage;
        stage.setTitle("Portal X");

        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/novo/menuPrincipal.fxml"));
        //FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/fxml/teste.fxml"));

        Parent app = appLoader.load();

        Scene scene = new Scene(app);

        stage.setScene(scene);
        stage.setFullScreen(true);

        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}
