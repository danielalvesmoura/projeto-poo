package main.controller;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

public class Global {
    public static void mostraErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
