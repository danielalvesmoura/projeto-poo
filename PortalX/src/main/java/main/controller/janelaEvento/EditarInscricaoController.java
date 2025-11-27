package main.controller.janelaEvento;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Enum.StatusInscricao;
import model.Enum.StatusSessao;
import model.Enum.TipoIngresso;
import model.Enum.TipoSessao;
import model.Evento;
import model.Inscricao;
import model.Sessao;
import servico.InscricaoServico;
import servico.SessaoServico;

import java.time.LocalTime;

public class EditarInscricaoController {


    @FXML
    public Pane paneRaiz;
    @FXML
    public void fechar() {
        ((Pane) paneRaiz.getParent()).getChildren().clear();
    }

    @FXML
    public DatePicker campoDataCricao;
    @FXML
    public ChoiceBox choiceTipoInscricao;
    @FXML
    public ChoiceBox choiceStatus;

    @FXML
    public void initialize() {
        choiceTipoInscricao.getItems().addAll("Participante","Palestrante");
        choiceTipoInscricao.setValue("Participante");

        choiceStatus.getItems().addAll("Pendente","Confirmada", "Cancelada");
        choiceStatus.setValue("Pendente");
    }

    @FXML
    public void cancelar() {
        fechar();
        janelaEventoController.abreInscricoes();
    }

    public TabelaInscricaoController tabelaInscricaoController;
    public JanelaEventoController janelaEventoController;

    private InscricaoServico inscricaoServico = new InscricaoServico();

    public Evento eventoAberto;
    public Inscricao inscricao;

    @FXML
    public void confirmar() {
        TipoIngresso tipoIngresso;
        if(choiceTipoInscricao.getValue() == "Participante") {
            tipoIngresso = TipoIngresso.PARTICIPANTE;
        } else{
            tipoIngresso = TipoIngresso.PALESTRANTE;
        }

        StatusInscricao statusInscricao;
        if(choiceStatus.getValue() == "Pendente") {
            statusInscricao = StatusInscricao.PENDENTE;
        } else if(choiceStatus.getValue() == "Confirmado") {
            statusInscricao = StatusInscricao.CONFIRMADA;
        } else {
            statusInscricao = StatusInscricao.CANCELADA;
        }

        inscricaoServico.alterar(inscricao,statusInscricao,tipoIngresso);

        tabelaInscricaoController.atualizaTabela();
        fechar();
        janelaEventoController.abreInscricoes();
    }
}
