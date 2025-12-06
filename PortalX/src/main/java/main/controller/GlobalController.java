package main.controller;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.JavaFX;
import model.Inscricao;

import java.time.LocalDate;

public abstract class GlobalController<A, T> extends GlobalControllerComObjeto<A, T> {

    public String cssCelulaVerde =
            "-fx-background-color: rgba(0, 255, 8, 0.2); " +
            "-fx-background-radius: 40; " +
            "-fx-text-fill: rgba(107, 255, 111, 1); " +
            "-fx-font-weight: bold;" +
            "-fx-alignment: CENTER;";

    public void trocaTela(String caminho) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));

        Parent pagina = loader.load();

        conteudo.getChildren().clear();

        conteudo.setCenter(pagina);
    }

    public void modal(String caminho) throws Exception {
        FXMLLoader appLoader = new FXMLLoader(getClass().getResource(caminho));

        Parent app = appLoader.load();

        Object controller = appLoader.getController();

        colocarT(null,controller);

        Stage modal = new Stage();
        modal.setTitle("Portal X");
        modal.setScene(new Scene(app));

        // Modal bloqueia interação com a janela principal
        modal.initModality(Modality.WINDOW_MODAL);

        // Define que a janela principal é a "dona" do modal
        modal.initOwner(JavaFX.getStage());

        modal.setResizable(true);

        // Abre o modal e bloqueia até fechar
        modal.showAndWait();
    }



}
