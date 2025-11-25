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

    public void abreTabelaEventos() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/tableviewEventos.fxml"));

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

    public void abreTabelaPessoas() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/tableviewPessoas.fxml"));

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

    public void abreTabelaSala() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/tableviewEventos.fxml"));

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

}
