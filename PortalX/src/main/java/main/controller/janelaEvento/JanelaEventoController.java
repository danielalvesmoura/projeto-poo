package main.controller.janelaEvento;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class JanelaEventoController {

    @FXML
    public Pane paneRaiz;

    @FXML
    public void botaoFechar() {
        ((Pane) paneRaiz.getParent()).getChildren().clear();

    }
}
