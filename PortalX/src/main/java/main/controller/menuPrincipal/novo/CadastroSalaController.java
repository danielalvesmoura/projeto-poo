package main.controller.menuPrincipal.novo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.Global;
import model.Sala;
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
            if(campoNome.getText().isEmpty()) {
                Global.mostraErro("O nome é obrigatório.");
            } else if(campoLocalizacao.getText().isEmpty()) {
                Global.mostraErro("O email é obrigatório.");
            } else if(campoCapacidade.getText().isEmpty()) {
                Global.mostraErro("A capacidade é obrigatória.");
            } else {
                if(salaAberto == null) {
                    salaServico.cadastrar(campoNome.getText(), Integer.parseInt(campoCapacidade.getText()), campoLocalizacao.getText());
                } else {
                    salaServico.alterar(salaAberto, campoNome.getText(), Integer.parseInt(campoCapacidade.getText()), campoLocalizacao.getText());
                }

                janelaTodasSalasController.atualizaTabela();
                fechar();
            }

        } catch (Exception e) {
            Global.mostraErro("A capacidade deve ser um número!");
        }

    }

}
