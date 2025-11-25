package main.controller.janelaEvento;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import main.controller.tabelas.TabelaEventoController;
import model.Evento;

public class JanelaEventoController {

    public JanelaEventoController janelaEventoController;
    public TabelaEventoController tabelaEventoController;

    @FXML
    public Pane paneRaiz;

    @FXML
    public Pane paneAncoraAba;

    @FXML
    public void initialize() {
        abreDetalhes();
    }

    @FXML
    public void botaoFechar() {
        ((Pane) paneRaiz.getParent()).getChildren().clear();
    }

    public Evento eventoAberto;

    // DEFINE SE A JANELA FOI ABERTA NO MODO ADIOCIONAR OU ALTERAR
    public String modo;

    AbaInfoController abaInfoController = new AbaInfoController();

    @FXML
    public void salvar() {
        abaInfoController.salvar();
    }

    @FXML
    public void cancelar() {
        abaInfoController.cancelar();
    }

    @FXML
    public void abreDetalhes() {
        try {
            paneAncoraAba.getChildren().clear();

            FXMLLoader abaInfoLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/abaInfo.fxml"));

            //AbaInfoController abaInfoController = new AbaInfoController();
            abaInfoController.janelaEventoController = janelaEventoController;
            abaInfoController.tabelaEventoController = tabelaEventoController;

            abaInfoLoader.setController(abaInfoController);

            Parent janela = abaInfoLoader.load();

            //janela.setLayoutY(-100);

            if(eventoAberto != null) {
                abaInfoController.eventoAberto = eventoAberto;
                abaInfoController.modo = "Alterar";
                System.out.println("evento não e nulo");
            } else {
                abaInfoController.modo = "Adicionar";
                System.out.println("evento e nulo");
            }

            paneAncoraAba.getChildren().add(janela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abreSessoes() {
        try {
            paneAncoraAba.getChildren().clear();

            FXMLLoader abaSessoesLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/abaSessoes.fxml"));

            AbaSessoesController abaSessoesController = new AbaSessoesController();
            abaSessoesLoader.setController(abaSessoesController);

            Parent janela = abaSessoesLoader.load();

            paneAncoraAba.getChildren().add(janela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abreInscricoes() {
        try {
            paneAncoraAba.getChildren().clear();

            FXMLLoader abaInscricoesLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/abaInscricoes.fxml"));

            AbaInscricoesController abaInscricoesController = new AbaInscricoesController();
            abaInscricoesLoader.setController(abaInscricoesController);

            Parent janela = abaInscricoesLoader.load();

            paneAncoraAba.getChildren().add(janela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
