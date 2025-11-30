package main.controller.menuPrincipal;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.controller.menuPrincipal.tabelas.TabelaPessoasController;
import model.Pessoa;
import servico.PalestranteServico;
import servico.ParticipanteServico;
import servico.PessoaServico;

public class CadastroPessoaController {


    @FXML
    public Pane paneRaiz;
    @FXML
    public void fechar() {
        ((Pane) paneRaiz.getParent()).getChildren().clear();
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
    public ChoiceBox choiceTipo;

    @FXML
    public void initialize() {
        choiceTipo.getItems().addAll("Participante","Palestrante");
        choiceTipo.setValue("Participante");
    }

    public void cancelar() {
        fechar();
    }

    public TabelaPessoasController tabelaPessoasController;

    PalestranteServico palestranteServico = new PalestranteServico();
    ParticipanteServico participanteServico = new ParticipanteServico();
    PessoaServico pessoaServico = new PessoaServico();

    public Pessoa pessoaAberta;

    // DEFINE SE A JANELA FOI ABERTA NO MODO ADIOCIONAR OU ALTERAR
    public String modo = "";


    public void salvar() {
        if(modo == "Alterar") {
            //if(choiceTipo.getValue() == "Participante") {
            //    participanteServico.cadastrar(campoNome.getText(), campoEmail.getText(), campoTelefone.getText(), campoDataNascimento.getValue());
            //} else {
            //    palestranteServico.cadastrar(campoNome.getText(), campoEmail.getText(), campoTelefone.getText(), campoDataNascimento.getValue());
            //}

            pessoaServico.alterar(pessoaAberta, campoNome.getText(), campoEmail.getText(), campoTelefone.getText(), campoDataNascimento.getValue());
            System.out.println("alterado");
            System.out.println(campoNome.getText());
            System.out.println(campoEmail.getText());
            System.out.println(campoTelefone.getText());
            System.out.println(campoDataNascimento.getValue());

        } else {
            if(choiceTipo.getValue() == "Participante") {
                //participanteServico.cadastrar(campoNome.getText(), campoEmail.getText(), campoTelefone.getText(), campoDataNascimento.getValue());
            } else {
               //palestranteServico.cadastrar(campoNome.getText(), campoEmail.getText(), campoTelefone.getText(), campoDataNascimento.getValue());
            }
            System.out.println("cadastrado");
            System.out.println(campoNome.getText());
            System.out.println(campoEmail.getText());
            System.out.println(campoTelefone.getText());
            System.out.println(campoDataNascimento.getValue());
        }

        tabelaPessoasController.atualizaTabela();
        fechar();
    }
}
