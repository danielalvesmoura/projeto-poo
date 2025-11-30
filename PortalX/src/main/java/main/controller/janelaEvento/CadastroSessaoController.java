package main.controller.janelaEvento;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.controller.menuPrincipal.tabelas.TabelaPessoasController;
import model.Enum.StatusSessao;
import model.Enum.TipoSessao;
import model.Evento;
import model.Pessoa;
import model.Sessao;

import servico.PessoaServico;
import servico.SessaoServico;

import java.time.LocalTime;

public class CadastroSessaoController {


    @FXML
    public Pane paneRaiz;
    @FXML
    public void fechar() {
        ((Pane) paneRaiz.getParent()).getChildren().clear();
    }

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
    public ChoiceBox choiceTipo;
    @FXML
    public ChoiceBox choiceStatus;

    @FXML
    public void initialize() {
        choiceTipo.getItems().addAll("Palestra","Painel", "Treinamento");
        choiceTipo.setValue("Treinamento");

        choiceStatus.getItems().addAll("Pendente","Confirmado", "Cancelado");
        choiceStatus.setValue("Pendente");
    }

    @FXML
    public void cancelar() {
        fechar();
        janelaEventoController.abreSessoes();
    }

    public TabelaSessaoController tabelaSessaoController;
    public JanelaEventoController janelaEventoController;

    private SessaoServico sessaoServico = new SessaoServico();

    public Sessao sessaoAberta;
    public Evento eventoAberto;

    // DEFINE SE A JANELA FOI ABERTA NO MODO ADIOCIONAR OU ALTERAR
    public String modo = "";

    public void carregaValores() {
        campoTitulo.setText(sessaoAberta.getTitulo());
        campoDescricao.setText(sessaoAberta.getDescricao());
        campoDataInicio.setValue(sessaoAberta.getDataInicio());
        campoHoraInicio.setText(sessaoAberta.getHoraInicio().toString());
        campoDataFim.setValue(sessaoAberta.getDataFim());
        campoHoraFim.setText(sessaoAberta.getHoraFim().toString());
    }

    @FXML
    public void confirmar() {
        TipoSessao tipoSessao;
        if(choiceTipo.getValue() == "Palestra") {
            tipoSessao = TipoSessao.PALESTRA;
        } else if(choiceTipo.getValue() == "Painel") {
            tipoSessao = TipoSessao.PAINEL;
        } else {
            tipoSessao = TipoSessao.TREINAMENTO;
        }

        StatusSessao statusSessao;
        if(choiceStatus.getValue() == "Pendente") {
            statusSessao = StatusSessao.PENDENTE;
        } else if(choiceStatus.getValue() == "Confirmado") {
            statusSessao = StatusSessao.CONFIRMADO;
        } else {
            statusSessao = StatusSessao.CANCELADO;
        }

        //LocalTime horaInicio = LocalTime.parse(campoHoraInicio.getText()+":00");
        //LocalTime horaFim = LocalTime.parse(campoHoraFim.getText()+":00");

        LocalTime horaInicio = LocalTime.parse("01:00");
        LocalTime horaFim = LocalTime.parse("01:00");

        if(modo == "Adicionar") {
            sessaoServico.cadastrar(eventoAberto, campoTitulo.getText(), campoDescricao.getText(), tipoSessao, campoDataInicio.getValue(), horaInicio, campoDataFim.getValue(), horaFim);
        } else {
            //sessaoServico.alterar(sessaoAberta, campoTitulo.getText(), campoDescricao.getText(), tipoSessao, campoDataInicio.getValue(), horaInicio, campoDataFim.getValue(), horaFim, statusSessao);
        }

        tabelaSessaoController.atualizaTabela();
        fechar();
        janelaEventoController.abreSessoes();
    }
}
