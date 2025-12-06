package main.controller.menuPrincipal.novo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.controller.GlobalController;

public class MenuPrincipalController extends GlobalController {

    public void passarStage(Stage stage) {
        super.stage = stage;
    }

    @Override
    protected void colocarT(Object objeto, Object controller) {}
    @Override
    protected void colocarA(Object objetoA, Object controller) {}
    @Override
    protected void defineBorderPane(Object controller) {};

    @FXML
    public void initialize() {
        super.conteudo.setStyle("/fxml/estilos/a.css");
    }

    @FXML
    public void abreTabelaEventos() throws Exception {
        trocaTela("/fxml/menuPrincipal/novo/janelaTodosEventos.fxml");
    }

    public void abreJanelaPessoas() throws Exception {
        trocaTela("/fxml/menuPrincipal/novo/janelaTodasPessoas.fxml");
    }

    @FXML
    public void abreJanelaSalas() throws Exception {
        trocaTela("/fxml/menuPrincipal/novo/janelaTodasSalas.fxml");
    }

}
