package main.controller.menuPrincipal.novo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Pessoa;
import servico.PessoaServico;

import java.io.IOException;

public class CadastroPessoaController {

    public Stage stage;
    JanelaTodasPessoasController janelaTodasPessoasController;
    public Pessoa pessoaAberta;

    public CadastroPessoaController(Stage stage, JanelaTodasPessoasController janelaTodasPessoasController, Pessoa pessoaAberta) {
        this.stage = stage;
        this.janelaTodasPessoasController = janelaTodasPessoasController;
        this.pessoaAberta = pessoaAberta;
    }

    public CadastroPessoaController(Stage stage, JanelaTodasPessoasController janelaTodasPessoasController) {
        this.stage = stage;
        this.janelaTodasPessoasController = janelaTodasPessoasController;
    }


    @FXML
    public TextField campoNome;
    @FXML
    public TextField campoEmail;
    @FXML
    public TextField campoTelefone;
    @FXML
    public DatePicker campoDataNascimento;
    @FXML
    public ChoiceBox campoTipo;
    @FXML
    public Label titulo;


    @FXML
    public void initialize() {
        if(pessoaAberta != null) {
            titulo.setText(pessoaAberta.getNome());

            campoNome.setText(pessoaAberta.getNome());
            campoEmail.setText(pessoaAberta.getEmail());
            campoTelefone.setText(pessoaAberta.getTelefone());
            campoDataNascimento.setValue(pessoaAberta.getDataNascimento());

            /*
            if(pessoaAberta instanceof Palestrante) {
                campoTipo.setValue("Palestrante");
            } else {
                campoTipo.setValue("Participante");
            }

             */
        } else {
            titulo.setText("Cadastrar nova pessoa");
        }

    }

    PessoaServico pessoaServico = new PessoaServico();

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

        if(pessoaAberta == null) {
            pessoaServico.cadastrar(campoNome.getText(), campoEmail.getText(), campoTelefone.getText(), campoDataNascimento.getValue());
        } else {
            pessoaServico.alterar(pessoaAberta, campoNome.getText(), campoEmail.getText(), campoTelefone.getText(), campoDataNascimento.getValue());
        }

        janelaTodasPessoasController.atualizaTabela();
        fechar();
    }

}
