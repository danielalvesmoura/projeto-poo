package main.controller.janelaEvento;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import model.Evento;

public class JanelaEventoController {

    @FXML
    public Pane paneRaiz;

    @FXML
    public Pane paneAncoraAba;

    @FXML
    public void initialize() {
        abreInformacoes();
    }

    @FXML
    public void botaoFechar() {
        ((Pane) paneRaiz.getParent()).getChildren().clear();
    }

    public Evento eventoAberto;

    private void abreInformacoes() {
        try {
            FXMLLoader abaInfoLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/abaInfo.fxml"));

            AbaInfoController abaInfoController = new AbaInfoController();
            abaInfoLoader.setController(abaInfoController);

            Parent janela = abaInfoLoader.load();

            //janela.setLayoutY(-100);

            abaInfoController.eventoAberto = eventoAberto;

            paneAncoraAba.getChildren().add(janela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
