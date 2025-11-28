package main.controller.janelaEvento;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import main.controller.menuPrincipal.tabelas.TabelaEventoController;
import model.Evento;
import servico.EventoServico;

import java.time.LocalTime;
import java.util.Objects;

public class AbaInfoController {
    public JanelaEventoController janelaEventoController;
    public TabelaEventoController tabelaEventoController;

    public Evento eventoAberto;

    EventoServico eventoServico = new EventoServico();

    @FXML
    public TextField campoNome;
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
    public TextField campoEndereco;
    @FXML
    public TextField campoCapacidade;

    // DEFINE SE A JANELA FOI ABERTA NO MODO ADIOCIONAR OU ALTERAR
    public String modo = "";

    public void carregaValores() {
        campoNome.setText(eventoAberto.getNome());
        campoDescricao.setText(eventoAberto.getDescricao());
        campoDataInicio.setValue(eventoAberto.getDataInicio());
        campoHoraInicio.setText(eventoAberto.getHoraInicio().toString());
        campoDataFim.setValue(eventoAberto.getDataFim());
        campoHoraFim.setText(eventoAberto.getHoraFim().toString());
        campoEndereco.setText(eventoAberto.getEndereco());
        campoCapacidade.setText(eventoAberto.getCapacidade());
    }

    @FXML
    public Button botaoFechar;

    public void cancelar() {
        janelaEventoController.botaoFechar();
    }

    public void salvar() {
        if(modo == "Adicionar") {
            if(Objects.equals(campoCapacidade.getText(), "")) campoCapacidade.setText("1");
            if(Objects.equals(campoHoraInicio.getText(), "")) campoHoraInicio.setText("00:00");
            if(Objects.equals(campoHoraFim.getText(), "")) campoHoraFim.setText("00:00");

            eventoServico.cadastrar(campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(), campoHoraInicio.getText(),campoDataFim.getValue(), campoHoraFim.getText(), Integer.parseInt(campoCapacidade.getText()));
        } else {
            eventoServico.alterar(eventoAberto,campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(), LocalTime.parse(campoHoraInicio.getText()), campoDataFim.getValue(), LocalTime.parse(campoHoraFim.getText()), Integer.parseInt(campoCapacidade.getText()));
        }

        tabelaEventoController.atualizaTabela();
        janelaEventoController.botaoFechar();
    }

}
