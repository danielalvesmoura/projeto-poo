package main.controller.menuPrincipal.novo;

import com.mysql.cj.conf.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.controller.GlobalController;
import util.Global;
import model.Sala;
import servico.SalaServico;

import java.io.IOException;

public class CadastroSalaController extends GlobalController {

    @Override
    protected void colocarT(Object objeto, Object controller) throws Exception {
        if(controller instanceof JanelaTodasSalasController c) {
            c.posCarregamento();
        }
    }
    @Override
    protected void colocarA(Object objetoA, Object controller) {}
    @Override
    protected void defineBorderPane(Object controller) { }

    public Sala salaAberto;

    @FXML
    public TextField campoNome;
    @FXML
    public TextField campoLocalizacao;
    @FXML
    public TextField campoCapacidade;

    @FXML
    public Label titulo;


    public void posCarregamento() {
        if(salaAberto != null) {
            titulo.setText(salaAberto.getNome());

            campoNome.setText(salaAberto.getNome());
            campoLocalizacao.setText(salaAberto.getLocalizacao());
            campoCapacidade.setText(Integer.toString(salaAberto.getCapacidade()));

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


    public SimpleBooleanProperty confirmou = new SimpleBooleanProperty(false);


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

                confirmou.set(true);

                fechar();
            }

        } catch (Exception e) {
            Global.mostraErro("A capacidade deve ser um número!");
        }

    }


}
