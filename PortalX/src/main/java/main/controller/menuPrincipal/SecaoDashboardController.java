package main.controller.menuPrincipal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;

import javafx.scene.layout.Pane;
import main.controller.menuPrincipal.tabelas.TabelaEventoController;
import main.controller.menuPrincipal.tabelas.TabelaPessoasController;

import java.net.URL;
import java.util.ResourceBundle;

public class SecaoDashboardController implements Initializable {
    @FXML
    public Pane tabelaPane;  // TELA INTEIRA

    @FXML
    public Pane paneTelaInteiraMenuPrincipal;  // TELA INTEIRA QUE VAI ENTRAR O MENU DO EVENTO

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        abreTabelaEventos();
    }

    @FXML
    public void abreTabelaEventos() {
        tabelaPane.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/tableviewEventos.fxml"));

            TabelaEventoController tabelaEventoController = new TabelaEventoController();
            tabelaEventoController.tabelaEventoController = tabelaEventoController;
            tabelaEventoController.paneTelaInteiraMenuPrincipal = paneTelaInteiraMenuPrincipal;
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

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/tableviewPessoas.fxml"));

            TabelaPessoasController tabelaPessoasController = new TabelaPessoasController();
            tabelaPessoasController.tabelaPessoasController = tabelaPessoasController;
            tabelaPessoasController.paneTelaInteiraMenuPrincipal = paneTelaInteiraMenuPrincipal;
            tabelaLoader.setController(tabelaPessoasController);

            Parent tabela = tabelaLoader.load();

            tabelaPane.getChildren().add(tabela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
