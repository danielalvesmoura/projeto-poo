package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

public class JavaFX extends Application {

    private Stage stage;

    int x = 1200;
    int y = 700;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        stage.setTitle("Portal X");

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/secaoDashboard.fxml"));

        Scene scene = new Scene(root);

        URL css = getClass().getResource("/fxml/style.css");
        if(css!=null) scene.getStylesheets().add(css.toExternalForm());
        else System.out.println("nao funfou");

        //scene.getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        stage.setScene(scene);

        stage.setFullScreen(true);

        stage.show();
    }

}
