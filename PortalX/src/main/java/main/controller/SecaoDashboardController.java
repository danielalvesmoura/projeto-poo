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
        abreTabelaEventos();
    }

    public void abreTabelaFeedback() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));

            TabelaFeedbackController tabelaFeedbackController = new TabelaFeedbackController();
            tabelaFeedbackController.defineTabelaController(tabelaFeedbackController);
            tabelaLoader.setController(tabelaFeedbackController);

            Parent tabela = tabelaLoader.load();

            //tabela.setLayoutY(-500);
            //tabela.setLayoutX(-550);

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
            tabelaEventoController.defineTabelaEventoController(tabelaEventoController);
            tabelaLoader.setController(tabelaEventoController);

            Parent tabela = tabelaLoader.load();

            tabela.setLayoutY(-100);

            tabelaPane.getChildren().add(tabela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abreTabelaInscricao() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));

            TabelaInscricaoController tabelaInscricaoController = new TabelaInscricaoController();
            tabelaInscricaoController.defineTabelaController(tabelaInscricaoController);
            tabelaLoader.setController(tabelaInscricaoController);

            Parent tabela = tabelaLoader.load();

            tabela.setLayoutY(-100);

            tabelaPane.getChildren().add(tabela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abreTabelaPalestrante() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));

            TabelaPalestranteController tabelaPalestranteController = new TabelaPalestranteController();
            tabelaPalestranteController.defineTabelaController(tabelaPalestranteController);
            tabelaLoader.setController(tabelaPalestranteController);

            Parent tabela = tabelaLoader.load();

            tabela.setLayoutY(-100);

            tabelaPane.getChildren().add(tabela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abreTabelaParticipante() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));

            TabelaParticipanteController tabelaParticipanteController = new TabelaParticipanteController();
            tabelaParticipanteController.defineTabelaController(tabelaParticipanteController);
            tabelaLoader.setController(tabelaParticipanteController);

            Parent tabela = tabelaLoader.load();

            tabela.setLayoutY(-100);

            tabelaPane.getChildren().add(tabela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abreTabelaSala() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));

            TabelaSalaController tabelaSalaController = new TabelaSalaController();
            tabelaSalaController.defineTabelaController(tabelaSalaController);
            tabelaLoader.setController(tabelaSalaController);

            Parent tabela = tabelaLoader.load();

            tabela.setLayoutY(-100);

            tabelaPane.getChildren().add(tabela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abreTabelaSessao() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/tableview.fxml"));

            TabelaSessaoController tabelaSessaoController = new TabelaSessaoController();
            tabelaSessaoController.defineTabelaController(tabelaSessaoController);
            tabelaLoader.setController(tabelaSessaoController);

            Parent tabela = tabelaLoader.load();

            tabela.setLayoutY(-100);

            tabelaPane.getChildren().add(tabela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
