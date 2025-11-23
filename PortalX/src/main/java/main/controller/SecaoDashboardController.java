package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.layout.Pane;
import main.controller.tabelas.*;

import java.net.URL;
import java.util.ResourceBundle;

public class SecaoDashboardController implements Initializable {
    @FXML
    public Pane tabelaPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        abreTabelaFeedback();
    }

    public void abreTabelaFeedback() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));

            TabelaFeedbackController tabelaFeedbackController = new TabelaFeedbackController();
            tabelaLoader.setController(tabelaFeedbackController);

            Parent tabela = tabelaLoader.load();

            tabelaPane.getChildren().add(tabela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abreTabelaEventos() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));

            TabelaEventoController tabelaEventoController = new TabelaEventoController();
            tabelaLoader.setController(tabelaEventoController);

            Parent tabela = tabelaLoader.load();

            tabelaPane.getChildren().add(tabela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
