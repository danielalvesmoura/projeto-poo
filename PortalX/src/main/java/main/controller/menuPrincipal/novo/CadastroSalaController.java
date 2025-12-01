package main.controller.menuPrincipal.novo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.controller.Global;
import model.Pessoa;
import model.Sala;
import servico.PessoaServico;
import servico.SalaServico;

import java.io.IOException;

public class CadastroSalaController {

    public Sala salaAberto;
    public JanelaTodasSalasController janelaTodasSalasController;

    public CadastroSalaController(Sala salaAberto) {
        this.salaAberto = salaAberto;
    }

    public CadastroSalaController() {}

    @FXML
    public TextField campoNome;
    @FXML
    public TextField campoLocalizacao;
    @FXML
    public TextField campoCapacidade;

    @FXML
    public Label titulo;


    @FXML
    public void initialize() {
        if(salaAberto != null) {
            titulo.setText(salaAberto.getNome());

            campoNome.setText(salaAberto.getNome());
            campoLocalizacao.setText(salaAberto.getLocalizacao());
            campoCapacidade.setText(Integer.toString(salaAberto.getCapacidade()));

            /*
            if(pessoaAberta instanceof Palestrante) {
                campoTipo.setValue("Palestrante");
            } else {
                campoTipo.setValue("Participante");
            }

             */
        } else {
            titulo.setText("Cadastrar nova sala");
        }

    }

    SalaServico salaServico = new SalaServico();

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

    @FXML
    public void confirmar() throws IOException {

        try {
            if(salaAberto == null) {
                salaServico.cadastrar(campoNome.getText(), Integer.parseInt(campoCapacidade.getText()), campoLocalizacao.getText());
            } else {
                salaServico.alterar(salaAberto, campoNome.getText(), Integer.parseInt(campoCapacidade.getText()), campoLocalizacao.getText());
            }

            janelaTodasSalasController.atualizaTabela();
            fechar();
        } catch (IllegalArgumentException e) {
            Global.mostraErro("A capacidade deve ser um número!");
        }

    }

}
