package main.controller.menuPrincipal.novo;

import com.mysql.cj.conf.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.Global;
import model.Pessoa;
import servico.PessoaServico;

import java.io.IOException;
import java.time.LocalDate;

public class CadastroPessoaController {

    public Pessoa pessoaAberta;

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

    public void posCarregamento() {
        if(pessoaAberta != null) {
            titulo.setText(pessoaAberta.getNome());

            campoNome.setText(pessoaAberta.getNome());
            campoEmail.setText(pessoaAberta.getEmail());
            campoTelefone.setText(pessoaAberta.getTelefone());
            campoDataNascimento.setValue(pessoaAberta.getDataNascimento());

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

    public SimpleBooleanProperty confirmou = new SimpleBooleanProperty(false);

    @FXML
    public void confirmar() throws IOException {

        if(campoNome.getText().isEmpty()) {
            Global.mostraErro("O nome é obrigatório.");
        } else if(campoEmail.getText().isEmpty()) {
            Global.mostraErro("O email é obrigatório.");
        } else if(campoTelefone.getText().isEmpty()) {
            Global.mostraErro("O telefone é obrigatório.");
        } else {

            try {
                LocalDate.parse(campoDataNascimento.getValue().toString());

                if(pessoaAberta == null) {
                    System.out.println("cadastrando " + campoNome.getText());
                    pessoaServico.cadastrar(campoNome.getText(), campoEmail.getText(), campoTelefone.getText(), campoDataNascimento.getValue());
                } else {
                    System.out.println("alterando " + pessoaAberta.getNome() + " para " + campoNome.getText());
                    pessoaServico.alterar(pessoaAberta, campoNome.getText(), campoEmail.getText(), campoTelefone.getText(), campoDataNascimento.getValue());
                }

                confirmou.set(true);

                fechar();

            } catch (Exception e) {
                Global.mostraErro("Data de nascimento inválida");
            }

        }

    }

}
