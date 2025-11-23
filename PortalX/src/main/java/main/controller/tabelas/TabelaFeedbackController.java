package main.controller.tabelas;

import dao.FeedbackDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaFeedbackController implements Initializable {

    @FXML
    public TableView<Feedback> tableView;

    @FXML
    public TableColumn<Feedback,String> colId;
    public TableColumn<Feedback,String> col2;
    public TableColumn<Feedback,String> col3;
    public TableColumn<Feedback,String> col4;

    ObservableList<Feedback> observableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizaTabela();
        tableView.setItems(observableList);
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
    }

}
