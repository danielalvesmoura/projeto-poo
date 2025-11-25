package main.controller.menuPrincipal.tabelas;

import dao.FeedbackDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.*;
import servico.FeedbackServico;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaFeedbackController implements Initializable {
    TabelaFeedbackController tabelaController;

    public void defineTabelaController(TabelaFeedbackController tabelaController) {
        this.tabelaController = tabelaController;
    }

    @FXML
    public Pane modalPane;

    @FXML
    public TableView<Feedback> tableView;

    @FXML
    public TableColumn<Feedback,String> colId;
    public TableColumn<Feedback,String> col2;
    public TableColumn<Feedback,String> col3;
    public TableColumn<Feedback,String> col4;
    public TableColumn<Feedback,Void> col5;
    public TableColumn<Feedback,String> col6;

    ObservableList<Feedback> observableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizaTabela();
        tableView.setItems(observableList);
    }

    public void botaoAdicionar() {
        try {
            FXMLLoader modalAdicionarLoader = new FXMLLoader(getClass().getResource("/fxml/modal/modalAdicionarFeedback.fxml"));

            modalAdicionarLoader.setController(tabelaController);

            Parent modalAdicionar = modalAdicionarLoader.load();

            choiceNota.getItems().addAll(1,2,3,4,5,6,7,8,9,10);

            modalAdicionar.setLayoutX(-531);
            modalAdicionar.setLayoutY(-230);

            modalPane.getChildren().add(modalAdicionar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizaTabela() {

        observableList.clear();

        FeedbackDAO feedbackDAO = new FeedbackDAO();

        List<Feedback> feedbacks = feedbackDAO.buscarTodos(Feedback.class);

        for (Feedback f : feedbacks) {
            observableList.add(f);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Nota"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Comentario"));
        col4.setCellValueFactory(new PropertyValueFactory<>("DataEnvio"));

        col5.setCellFactory(col -> new TableCell<Feedback, Void>() {

            private final Button botaoRemover = new Button("Remover");

            {
                botaoRemover.setOnAction(event -> {
                    Feedback f = getTableView().getItems().get(getIndex());
                    feedbackServico.remover(f);
                    atualizaTabela();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(botaoRemover);
                }
            }
        });

        col2.setText("Nota");
        col3.setText("Comentário");
        col4.setText("Data de Envio");
        col5.setText("");
        col6.setText("");

        col2.setPrefWidth(50);
        col3.setPrefWidth(1100);
        col4.setPrefWidth(100);
        col6.setPrefWidth(0);
    }


    // MODAL ADICIONAR

    @FXML
    private Pane paneModalAdicionar;

    public void fecharModal() {
        ((Pane) paneModalAdicionar.getParent()).getChildren().clear();
    }

    @FXML
    private ChoiceBox<Integer> choiceNota;
    @FXML
    private TextArea campoComentario;
    @FXML
    private Label mensagem;

    FeedbackServico feedbackServico = new FeedbackServico();

    public void confirmar() {

        feedbackServico.cadastrar(choiceNota.getValue(), campoComentario.getText(), LocalDate.now());

        mensagem.setStyle("-fx-text-fill: green;");
        mensagem.setText("Feedback cadastrado com sucesso!");

        tabelaController.atualizaTabela();
    }

}
