package main.controller.janelaEvento;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Evento;
import servico.EventoServico;

public class AbaInfoController {
    public Evento eventoAberto;

    EventoServico eventoServico = new EventoServico();

    @FXML
    private TextField campoNome;
    @FXML
    private TextArea campoDescricao;
    @FXML
    private TextField campoEndereco;
    @FXML
    private DatePicker campoDataInicio;
    @FXML
    private DatePicker campoDataFim;

    public void salvar() {
        eventoServico.alterar(eventoAberto,campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(),campoDataFim.getValue());
    }
}
