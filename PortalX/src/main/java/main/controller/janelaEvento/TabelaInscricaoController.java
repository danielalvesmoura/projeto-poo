package main.controller.janelaEvento;

import dao.FeedbackDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.Feedback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaInscricaoController implements Initializable {
    TabelaInscricaoController tabelaController;

    public void defineTabelaController(TabelaInscricaoController tabelaController) {
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
    public TableColumn<Feedback,String> col5;
    public TableColumn<Feedback,String> col6;

    ObservableList<Feedback> observableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizaTabela();
        tableView.setItems(observableList);
    }

    public void botaoAdicionar() {
        try {
            FXMLLoader modalAdicionarLoader = new FXMLLoader(getClass().getResource("/fxml/modal/modalAdicionarEvento.fxml"));

            modalAdicionarLoader.setController(tabelaController);

            Parent modalAdicionar = modalAdicionarLoader.load();

            modalAdicionar.setLayoutX(-531);
            modalAdicionar.setLayoutY(-230);

            modalPane.getChildren().add(modalAdicionar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmar() {
        atualizaTabela();
    }

    public void atualizaTabela() {

        FeedbackDAO feedbackDAO = new FeedbackDAO();

        List<Feedback> feedbacks = feedbackDAO.buscarTodos(Feedback.class);

        for (Feedback f : feedbacks) {
            observableList.add(f);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Nota"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Comentario"));
        col4.setCellValueFactory(new PropertyValueFactory<>("DataEnvio"));

        col2.setText("Nota");
        col3.setText("Comentário");
        col4.setText("Data de Envio");
        col5.setText("");
        col6.setText("");

        col2.setPrefWidth(50);
        col3.setPrefWidth(1200);
        col4.setPrefWidth(100);
        col5.setPrefWidth(0);
        col6.setPrefWidth(0);
    }

}
