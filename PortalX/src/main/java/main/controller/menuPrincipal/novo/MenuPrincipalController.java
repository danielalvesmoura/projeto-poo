package main.controller.menuPrincipal.novo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.controller.menuPrincipal.tabelas.TabelaPessoasController;

public class MenuPrincipalController {

    public Stage stage;

    public MenuPrincipalController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public Pane paneConteudo;

    @FXML
    public PieChart pieChart;

    @FXML
    public void initialize() {
        pieChart.getData().add(new PieChart.Data("Maçã", 30));
    }

    @FXML
    public void abreTabelaEventos() {

        try {
            FXMLLoader janelaTodosEventosLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/novo/janelaTodosEventos.fxml"));

            JanelaTodosEventosController janelaTodosEventosController = new JanelaTodosEventosController(stage);
            janelaTodosEventosLoader.setController(janelaTodosEventosController);

            Parent janela = janelaTodosEventosLoader.load();

            Scene cenaTodosEventos = new Scene(janela);

            stage.setScene(cenaTodosEventos);

            stage.setMaximized(false);
            stage.setMaximized(true);

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
