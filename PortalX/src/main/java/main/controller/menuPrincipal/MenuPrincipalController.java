package main.controller.menuPrincipal;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.controller.menuPrincipal.tabelas.TabelaPessoasController;

public class MenuPrincipalController {
    @FXML
    public Pane paneConteudo;

    public Stage stage;

    @FXML
    public PieChart pieChart;

    @FXML
    public void initialize() {
        pieChart.getData().add(new PieChart.Data("Maçã", 30));
    }

    @FXML
    public void abreTabelaEventos() {

        try {
            FXMLLoader janelaTodosEventosLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/janelaTodosEventos.fxml"));

            JanelaTodosEventosController janelaTodosEventosController = new JanelaTodosEventosController();
            //TabelaEventoController tabelaEventoController = new TabelaEventoController();
            //tabelaEventoController.tabelaEventoController = tabelaEventoController;
            janelaTodosEventosLoader.setController(janelaTodosEventosController);

            janelaTodosEventosController.stage = stage;

            Parent janela = janelaTodosEventosLoader.load();

            Scene cenaTodosEventos = new Scene(janela);

            stage.setScene(cenaTodosEventos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrePessoas() {
        paneConteudo.getChildren().clear();

        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/tableviewPessoas.fxml"));

            TabelaPessoasController tabelaPessoasController = new TabelaPessoasController();
            tabelaPessoasController.tabelaPessoasController = tabelaPessoasController;
            //tabelaPessoasController.paneTelaInteiraMenuPrincipal = paneTelaInteiraMenuPrincipal;
            tabelaLoader.setController(tabelaPessoasController);

            Parent tabela = tabelaLoader.load();

            paneConteudo.getChildren().add(tabela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
