package main.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class SubjanelaController {

    @FXML
    public Pane root;

    @FXML
    private Label subjanelaMensagem;

    public void mudarMensagem(String mensagem) {
        subjanelaMensagem.setText(mensagem);
    }


    @FXML
    private void fechar() {
        ((Pane) root.getParent()).getChildren().remove(root);
        TesteController.subjanelaAberta = false;
    }
}
