package main.controller.janelaEvento.novo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.controller.GlobalController;
import main.controller.menuPrincipal.novo.JanelaTodosEventosController;
import model.Evento;

import java.io.IOException;

public class JanelaEditarEventoController extends GlobalController<Evento, Evento, JanelaEditarEventoController> {

    @Override
    protected void colocarT(Evento evento, Object controller) throws Exception {
        if(controller instanceof SecaoDetalhesController cD) {
            cD.eventoAberto = evento;
            cD.posCarregamento();
        }
        if(controller instanceof SecaoSessoesController cS) {
            cS.eventoAberto = evento;
            cS.posCarregamento();
        }
    }
    @Override
    protected void colocarA(Evento evento, Object controller) {}
    @Override
    protected void defineBorderPane(Object controller) {
        if (controller instanceof SecaoDetalhesController c) {
            c.setConteudo(super.conteudo);
            c.setBorderpaneMenor(super.borderpaneMenor);
        }
        if (controller instanceof SecaoSessoesController c) {
            c.setConteudo(super.conteudo);
            c.setBorderpaneMenor(super.borderpaneMenor);
        }
    };


    public Evento eventoAberto;

    @FXML
    public Label tituloJanelaEvento;
    @FXML
    public VBox vboxConteudo;

    @FXML
    public void fechar() throws Exception {
        trocaTela("/fxml/menuPrincipal/novo/janelaTodosEventos.fxml");
    }

    @FXML
    public Button botaoSessoes;
    @FXML
    public Button botaoInscricoes;

    @FXML
    private BorderPane conteudo;          // ← INJETADO DO FXML DO PAI
    @FXML
    private BorderPane borderpaneMenor;   // ← também do pai

    public void posCarregamento() throws Exception {
        setConteudo(conteudo);
        setBorderpaneMenor(borderpaneMenor);
        abreAbaDetalhes();

        if(eventoAberto == null) {
            botaoSessoes.setVisible(false);
            botaoInscricoes.setVisible(false);
            tituloJanelaEvento.setText("Cadastrar novo evento");
        } else {
            tituloJanelaEvento.setText(eventoAberto.getNome());
        }
    }


    @FXML
    public void abreAbaDetalhes() throws Exception {
        trocaBorderPane("/fxml/janelaEvento/novo/secaoDetalhes.fxml",eventoAberto, null);
    }

    @FXML
    public void abreAbaSessoes() throws Exception {
        if(eventoAberto != null) {

            trocaBorderPane("/fxml/janelaEvento/novo/secaoSessoes.fxml", eventoAberto, null);

        }
    }

    @FXML
    public void abreJanelaInscricoes() throws Exception {
        if(eventoAberto != null) {
            trocaTela("/fxml/janelaEvento/novo/janelaTodasInscricoes.fxml",eventoAberto);
        }
    }
}
