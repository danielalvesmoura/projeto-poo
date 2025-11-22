package main.controller;

import com.sun.javafx.scene.control.ContextMenuContent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TesteController {

    @FXML
    private VBox root;

    @FXML
    private Label mensagem;

    @FXML
    private TextField campoNome;

    @FXML
    private Pane subjanela;


    public void initialize() {
        mensagem.setText("salve");
    }

    public static boolean subjanelaAberta = false;

    @FXML
    private void clicouBotao() {

        String nome = campoNome.getText();
        mensagem.setText(campoNome.getText());

        if (subjanelaAberta == false) {
            try {
                FXMLLoader subjanelaLoader = new FXMLLoader(getClass().getResource("/fxml/subjanela.fxml"));
                Parent novoItem = subjanelaLoader.load();
                SubjanelaController subjanelaController = subjanelaLoader.getController();


                subjanelaController.mudarMensagem("salve " + nome);

                // posiciona a subjanela
                double centerX = (subjanela.getWidth() - novoItem.prefWidth(-1)) / 2;
                double centerY = (subjanela.getHeight() - novoItem.prefHeight(-1)) / 2;
                novoItem.setLayoutX(centerX);
                novoItem.setLayoutY(centerY);

                subjanela.getChildren().add(novoItem);

                subjanelaAberta = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
