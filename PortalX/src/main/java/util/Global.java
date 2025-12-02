package util;
import java.util.List;

import com.github.javafaker.Faker;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.util.Locale;

public class Global {
    public static Faker faker = new Faker(new Locale("pt-BR"));

    public static void mostraErro(String mensagem) {
        if (!javafx.application.Platform.isFxApplicationThread()) {
            System.err.println("[ERRO] " + mensagem);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }

    public static void mostraMensagem(String titulo,String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.showAndWait();
    }
}
