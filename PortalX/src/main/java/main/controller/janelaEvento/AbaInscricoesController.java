package main.controller.janelaEvento;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.controller.menuPrincipal.tabelas.TabelaEventoController;
import model.Evento;
import servico.EventoServico;

public class AbaInscricoesController {
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

    @FXML
    public Button botaoFechar;

    public void cancelar() {
        janelaEventoController.botaoFechar();
    }

    public void salvar() {
        if(modo == "Adicionar") {
            eventoServico.cadastrar(campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(),campoDataFim.getValue());
        } else {
            eventoServico.alterar(eventoAberto,campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(),campoDataFim.getValue());
        }

        tabelaEventoController.atualizaTabela();
        janelaEventoController.botaoFechar();
    }

}
