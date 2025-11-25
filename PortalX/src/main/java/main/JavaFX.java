package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import java.net.URL;

public class JavaFX extends Application {

    private Stage stage;

    int x = 1200;
    int y = 700;

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Logger.getLogger("").setLevel(Level.OFF);

        this.stage = primaryStage;
        stage.setTitle("Portal X");

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menuPrincipal/secaoDashboard.fxml"));

        Scene scene = new Scene(root);

        URL css = getClass().getResource("/fxml/menuPrincipal/barraLateralInicio.css");
        if(css!=null) scene.getStylesheets().add(css.toExternalForm());
        else System.out.println("nao funfou");

        //scene.getStylesheets().add(getClass().getResource("/fxml/barraLateralInicio.css").toExternalForm());
        stage.setScene(scene);

        stage.setFullScreen(true);

        stage.show();
    }

}
