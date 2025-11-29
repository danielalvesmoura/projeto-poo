package main.controller.janelaEvento.novo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.controller.menuPrincipal.novo.JanelaTodosEventosController;
import model.Evento;

import java.io.IOException;
import java.util.Objects;

public class JanelaEditarEventoController {

    public Stage stage;
    public Evento eventoAberto;
    public JanelaEditarEventoController janelaEditarEventoController;

    public JanelaEditarEventoController(Stage stage, Evento eventoAberto) {
        this.stage = stage;
        this.eventoAberto = eventoAberto;
    }

    public JanelaEditarEventoController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public Label tituloJanelaEvento;
    @FXML
    public VBox vboxConteudo;

    @FXML
    public void fechar() throws IOException {
        FXMLLoader janelaTodosEventosLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/novo/janelaTodosEventos.fxml"));

        JanelaTodosEventosController janelaTodosEventosController = new JanelaTodosEventosController(stage);
        janelaTodosEventosLoader.setController(janelaTodosEventosController);

        Parent janela = janelaTodosEventosLoader.load();

        Scene cenaTodosEventos = new Scene(janela);

        stage.setScene(cenaTodosEventos);


    }

    @FXML
    public void initialize() throws IOException {
        abreAbaDetalhes();
    }

    @FXML
    public BorderPane borderpaneConteudo;


    @FXML
    public void abreAbaDetalhes() throws IOException {
        borderpaneConteudo.getChildren().clear();

        FXMLLoader secaoDetalhesLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/novo/secaoDetalhes.fxml"));

        SecaoDetalhesController secaoDetalhesController = new SecaoDetalhesController(stage,eventoAberto);
        secaoDetalhesLoader.setController(secaoDetalhesController);

        Parent janela = secaoDetalhesLoader.load();

        VBox.setVgrow(janela, Priority.ALWAYS);

        borderpaneConteudo.setCenter(janela);

    }

    @FXML
    public void abreAbaSessoes() throws IOException {
        if(eventoAberto != null) {
            borderpaneConteudo.getChildren().clear();

            FXMLLoader secaoDetalhesLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/novo/secaoSessoes.fxml"));

            SecaoSessoesController secaoSessoesController = new SecaoSessoesController(stage,eventoAberto);
            secaoDetalhesLoader.setController(secaoSessoesController);
            secaoSessoesController.borderpaneConteudo = borderpaneConteudo;
            secaoSessoesController.janelaEditarEventoController = janelaEditarEventoController;

            Parent janela = secaoDetalhesLoader.load();

            VBox.setVgrow(janela, Priority.ALWAYS);

            borderpaneConteudo.setCenter(janela);

        }
    }

    @FXML
    public void abreJanelaInscricoes() {
        if(eventoAberto != null) {

        }
    }
}
