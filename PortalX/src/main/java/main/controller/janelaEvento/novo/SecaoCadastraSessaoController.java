package main.controller.janelaEvento.novo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.controller.menuPrincipal.novo.JanelaTodosEventosController;
import model.Enum.StatusSessao;
import model.Enum.TipoSessao;
import model.Evento;
import model.Sessao;
import servico.EventoServico;
import servico.SessaoServico;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;

public class SecaoCadastraSessaoController {

    public Stage stage;
    public Evento eventoAberto;
    public Sessao sessaoAberta;

    public SecaoCadastraSessaoController(Stage stage, Evento eventoAberto, Sessao sessaoAberta) {
        this.stage = stage;
        this.eventoAberto = eventoAberto;
    }

    public SecaoCadastraSessaoController(Stage stage, Evento eventoAberto) {
        this.stage = stage;
        this.eventoAberto = eventoAberto;
    }


    EventoServico eventoServico = new EventoServico();

    @FXML
    public TextField campoTitulo;
    @FXML
    public TextArea campoDescricao;
    @FXML
    public DatePicker campoDataInicio;
    @FXML
    public TextField campoHoraInicio;
    @FXML
    public DatePicker campoDataFim;
    @FXML
    public TextField campoHoraFim;
    @FXML
    public ChoiceBox campoTipo;
    @FXML
    public ChoiceBox campoStatus;

    @FXML
    public void initialize() {
        campoTipo.getItems().addAll("Palestra","Painel", "Treinamento");
        campoTipo.setValue("Treinamento");

        campoStatus.getItems().addAll("Pendente","Confirmado", "Cancelado");
        campoStatus.setValue("Pendente");

        if(sessaoAberta != null) {
            campoTitulo.setText(sessaoAberta.getTitulo());
            campoDescricao.setText(sessaoAberta.getDescricao());
            campoDataInicio.setValue(sessaoAberta.getDataInicio());
            campoHoraInicio.setText(sessaoAberta.getHoraInicio().toString());
            campoDataFim.setValue(sessaoAberta.getDataFim());
            campoHoraFim.setText(sessaoAberta.getHoraFim().toString());
            campoTipo.setValue(sessaoAberta.getTipo());
            campoStatus.setValue(sessaoAberta.getStatus());
        }

    }

    SessaoServico sessaoServico = new SessaoServico();

    @FXML
    public void salvar() throws IOException {
        TipoSessao tipoSessao;
        if(campoTipo.getValue() == "Palestra") {
            tipoSessao = TipoSessao.PALESTRA;
        } else if(campoTipo.getValue() == "Painel") {
            tipoSessao = TipoSessao.PAINEL;
        } else {
            tipoSessao = TipoSessao.TREINAMENTO;
        }

        StatusSessao statusSessao;
        if(campoStatus.getValue() == "Pendente") {
            statusSessao = StatusSessao.PENDENTE;
        } else if(campoStatus.getValue() == "Confirmado") {
            statusSessao = StatusSessao.CONFIRMADO;
        } else {
            statusSessao = StatusSessao.CANCELADO;
        }

        LocalTime horaInicio = LocalTime.parse("01:00");
        LocalTime horaFim = LocalTime.parse("01:00");

        if(sessaoAberta == null) {
            sessaoServico.cadastrar(eventoAberto, campoTitulo.getText(), campoDescricao.getText(), tipoSessao, campoDataInicio.getValue(), horaInicio, campoDataFim.getValue(), horaFim);
        } else {
            sessaoServico.alterar(sessaoAberta, campoTitulo.getText(), campoDescricao.getText(), tipoSessao, campoDataInicio.getValue(), horaInicio, campoDataFim.getValue(), horaFim, statusSessao);
        }

        janelaEditarEventoController.abreAbaSessoes();
    }

    JanelaEditarEventoController janelaEditarEventoController;

}
