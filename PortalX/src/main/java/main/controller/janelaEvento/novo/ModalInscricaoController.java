package main.controller.janelaEvento.novo;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Enum.StatusInscricao;
import model.Enum.TipoIngresso;
import model.Inscricao;
import servico.InscricaoServico;

import java.io.IOException;

public class ModalInscricaoController {

    public Inscricao inscricaoAberta;

    @FXML
    public ChoiceBox campoTipo;
    @FXML
    public ChoiceBox campoStatus;

    public void posCarregamento() {
        campoTipo.getItems().addAll("Participante","Palestrante");
        campoStatus.getItems().addAll("Pendente","Confirmada","Cancelada");

        campoTipo.setValue(inscricaoAberta.getTipoIngresso());
        campoStatus.setValue(inscricaoAberta.getStatus());
    }

    InscricaoServico inscricaoServico = new InscricaoServico();

    @FXML
    private Button botaoCancelar;

    public void fechar() {
        Stage stage = (Stage) botaoCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelar() {
        fechar();
    }

    public SimpleBooleanProperty confirmou = new SimpleBooleanProperty(false);

    @FXML
    public void confirmar() throws IOException {
        TipoIngresso tipoIngresso;
        if(campoTipo.getValue().toString().equals("Participante")) {
            tipoIngresso = TipoIngresso.PARTICIPANTE;
        } else {
            tipoIngresso = TipoIngresso.PALESTRANTE;
        }

        StatusInscricao statusInscricao;
        if(campoStatus.getValue().toString().equals("Pendente")) {
            statusInscricao = StatusInscricao.PENDENTE;
        } else if(campoStatus.getValue().toString().equals("Confirmada")) {
            statusInscricao = StatusInscricao.CONFIRMADA;
        } else {
            statusInscricao = StatusInscricao.CANCELADA;
        }

        inscricaoServico.alterar(inscricaoAberta,statusInscricao,tipoIngresso);

        confirmou.set(true);

        fechar();
    }

}
