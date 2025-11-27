package main.controller.janelaEvento;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.controller.menuPrincipal.tabelas.TabelaEventoController;
import model.Evento;

public class JanelaEventoController {

    public JanelaEventoController janelaEventoController;
    public TabelaEventoController tabelaEventoController;

    @FXML
    public Label tituloJanelaEvento;

    @FXML
    public Pane paneRaiz;
    @FXML
    public Pane paneAncoraAba;
    @FXML
    public Pane paneJanelaParcial;

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
            paneJanelaParcial.getChildren().clear();

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
            paneJanelaParcial.getChildren().clear();

            FXMLLoader tabelaSessaoLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/tableviewSessao.fxml"));
            TabelaSessaoController tabelaSessaoController = new TabelaSessaoController();

            tabelaSessaoLoader.setController(tabelaSessaoController);

            Parent janela = tabelaSessaoLoader.load();

            tabelaSessaoController.paneAncoraAba = paneAncoraAba;
            tabelaSessaoController.tabelaController = tabelaSessaoController;
            tabelaSessaoController.janelaEventoController = janelaEventoController;
            tabelaSessaoController.eventoAberto = eventoAberto;
            tabelaSessaoController.initializeManual();

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
