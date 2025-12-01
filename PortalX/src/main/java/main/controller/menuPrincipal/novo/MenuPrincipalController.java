package main.controller.menuPrincipal.novo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.controller.menuPrincipal.tabelas.TabelaPessoasController;

public class MenuPrincipalController {

    public Stage stage;

    public MenuPrincipalController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public BorderPane borderPaneConteudo;

    @FXML
    public PieChart pieChart;

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
            janelaTodasPessoasController.janelaTodasPessoasController = janelaTodasPessoasController;

            tabelaLoader.setController(janelaTodasPessoasController);

            Parent janela = tabelaLoader.load();

            Scene cenaTodasPessoas = new Scene(janela);

            stage.setScene(cenaTodasPessoas);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abreJanelaSalas() {
        try {
            FXMLLoader tabelaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/novo/janelaTodasSalas.fxml"));

            JanelaTodasSalasController janelaTodasSalasController = new JanelaTodasSalasController();

            tabelaLoader.setController(janelaTodasSalasController);

            Parent janela = tabelaLoader.load();

            borderPaneConteudo.setCenter(janela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
