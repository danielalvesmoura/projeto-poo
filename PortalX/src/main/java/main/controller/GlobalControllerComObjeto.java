package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.JavaFX;

public abstract class GlobalControllerComObjeto<A, T> {
    @FXML
    public BorderPane conteudo;
    @FXML
    public BorderPane borderpaneMenor;

    public void setPanes(BorderPane conteudo, BorderPane bp) {
        this.conteudo = conteudo;
        this.borderpaneMenor = bp;
    }


    public void trocaBorderPane(String caminho, T objeto, A objetoA) throws Exception {
        borderpaneMenor.getChildren().clear();

        FXMLLoader appLoader = new FXMLLoader(getClass().getResource(caminho));

        Parent app = appLoader.load();

        Object controller = appLoader.getController();

        colocarT(objeto,controller);
        colocarA(objetoA,controller);
        defineBorderPane(controller);

        borderpaneMenor.setCenter(app);
    }

    public void modal(String caminho, T objeto) throws Exception {
        FXMLLoader appLoader = new FXMLLoader(getClass().getResource(caminho));

        Parent app = appLoader.load();

        Object controller = appLoader.getController();

        colocarT(objeto,controller);
        defineBorderPane(controller);

        Stage modal = new Stage();
        modal.setTitle("Portal X");
        modal.setScene(new Scene(app));

        // Modal bloqueia interação com a janela principal
        modal.initModality(Modality.WINDOW_MODAL);

        // Define que a janela principal é a "dona" do modal
        modal.initOwner(JavaFX.getStage());

        modal.setResizable(true);

        // Abre o modal e bloqueia até fechar
        modal.showAndWait();
    }

    public void trocaTela(String caminho, T objeto) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(caminho));

        Parent pagina = loader.load();

        Object controller = loader.getController();
        colocarT(objeto,controller);

        conteudo.getChildren().clear();

        conteudo.setCenter(pagina);
    }



    protected abstract void colocarT(T objeto, Object controller) throws Exception;
    protected abstract void colocarA(A objetoA, Object controller);
    protected abstract void defineBorderPane(Object controller);
}
