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


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abreJanelaPessoas() {
        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/novo/janelaTodasPessoas.fxml"));

            JanelaTodasPessoasController janelaTodasPessoasController = new JanelaTodasPessoasController(stage);

            tabelaLoader.setController(janelaTodasPessoasController);

            Parent janela = tabelaLoader.load();

            Scene cenaTodasPessoas = new Scene(janela);

            stage.setScene(cenaTodasPessoas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
