package main.controller.menuPrincipal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.layout.Pane;
import main.controller.menuPrincipal.tabelas.TabelaEventoController;
import main.controller.menuPrincipal.tabelas.TabelaPessoasController;
import main.controller.menuPrincipal.tabelas.TabelaSalaController;

import java.net.URL;
import java.util.ResourceBundle;

public class SecaoDashboardController implements Initializable {
    @FXML
    public Pane tabelaPane;  // TELA INTEIRA

    @FXML
    public Pane paneAncoraTabela;  // TELA PARCIAL

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        abreTabelaEventos();
    }

    @FXML
    public void abreTabelaEventos() {
        tabelaPane.getChildren().clear();
        paneAncoraTabela.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/tableviewEventos.fxml"));

            TabelaEventoController tabelaEventoController = new TabelaEventoController();
            tabelaEventoController.defineTabelaEventoController(tabelaEventoController);
            tabelaLoader.setController(tabelaEventoController);

            Parent tabela = tabelaLoader.load();

            tabelaPane.getChildren().add(tabela);

            System.out.println("abretabelaeventos");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrePessoas() {
        tabelaPane.getChildren().clear();
        paneAncoraTabela.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/tableviewPessoas.fxml"));

            TabelaPessoasController tabelaPessoasController = new TabelaPessoasController();
            tabelaPessoasController.tabelaPessoasController = tabelaPessoasController;
            tabelaLoader.setController(tabelaPessoasController);

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
