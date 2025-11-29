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

    public JanelaEditarEventoController(Stage stage, Evento eventoAberto, String abaAberta) {
        this.stage = stage;
        this.eventoAberto = eventoAberto;
        this.abaAberta = abaAberta;
    }

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

        stage.setMaximized(false);
        stage.setMaximized(true);
    }

    @FXML
    public void initialize() throws IOException {
        if(abaAberta == null) {
            abreAbaDetalhes();
        } else {
            abreAbaSessoes();
        }
    }

    private String abaAberta;
    private Parent janela;

    @FXML
    public void abreAbaDetalhes() throws IOException {
        if(!Objects.equals(abaAberta, "Detalhes")) {
            abaAberta = "Detalhes";
            vboxConteudo.getChildren().remove(janela);

            FXMLLoader secaoDetalhesLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/novo/secaoDetalhes.fxml"));

            SecaoDetalhesController secaoDetalhesController = new SecaoDetalhesController(stage,eventoAberto);
            secaoDetalhesLoader.setController(secaoDetalhesController);

            janela = secaoDetalhesLoader.load();

            VBox.setVgrow(janela, Priority.ALWAYS);

            vboxConteudo.getChildren().add(janela);
        }
    }

    @FXML
    public void abreAbaSessoes() throws IOException {
        if(eventoAberto != null && !Objects.equals(abaAberta, "Sessoes")) {
            abaAberta = "Sessoes";
            vboxConteudo.getChildren().remove(janela);

            FXMLLoader secaoDetalhesLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/novo/secaoSessoes.fxml"));

            SecaoSessoesController secaoSessoesController = new SecaoSessoesController(stage,eventoAberto);
            secaoDetalhesLoader.setController(secaoSessoesController);
            secaoSessoesController.vboxConteudo = vboxConteudo;
            secaoSessoesController.janelaEditarEventoController = janelaEditarEventoController;

            janela = secaoDetalhesLoader.load();

            secaoSessoesController.janela = janela;

            VBox.setVgrow(janela, Priority.ALWAYS);

            vboxConteudo.getChildren().add(janela);

        }
    }

    @FXML
    public void abreJanelaInscricoes() {
        if(eventoAberto != null) {

        }
    }
}
