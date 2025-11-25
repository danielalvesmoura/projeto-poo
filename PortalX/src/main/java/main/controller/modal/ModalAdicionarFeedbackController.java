package main.controller.modal;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.controller.menuPrincipal.tabelas.TabelaEventoController;
import servico.EventoServico;

public class ModalAdicionarFeedbackController {

    TabelaEventoController tabelaEventoController;

    public void defineTabelaEventoController(TabelaEventoController tabelaEventoController) {
        this.tabelaEventoController = tabelaEventoController;
    }

    @FXML
    private Pane paneModalAdicionar;

    public void fecharModal() {
        ((Pane) paneModalAdicionar.getParent()).getChildren().clear();
    }

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
    @FXML
    private Label mensagem;

    EventoServico eventoServico = new EventoServico();

    public void confirmar() {
        System.out.println(campoNome.getText());
        System.out.println(campoDescricao.getText());
        System.out.println(campoEndereco.getText());
        System.out.println(campoDataInicio.getValue());
        System.out.println(campoDataFim.getValue());

        eventoServico.cadastrar(campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(),campoDataFim.getValue());

        mensagem.setStyle("-fx-text-fill: green;");
        mensagem.setText("Evento cadastrado com sucesso!");

        tabelaEventoController.atualizaTabela();
    }
}
