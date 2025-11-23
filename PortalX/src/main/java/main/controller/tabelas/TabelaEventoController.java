package main.controller.tabelas;

import dao.EventoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import javafx.scene.control.*;
import model.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TabelaEventoController implements Initializable {

    @FXML
    public TableView<Evento> tableView;

    @FXML
    public TableColumn<Evento,String> colId;
    public TableColumn<Evento,String> col2;
    public TableColumn<Evento,String> col3;
    public TableColumn<Evento,String> col4;
    public TableColumn<Evento,LocalDate> col5;
    public TableColumn<Evento,LocalDate> col6;

    ObservableList<Evento> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizaTabela();
        tableView.setItems(observableList);
    }

    public void atualizaTabela() {
        EventoDAO eventoDAO = new EventoDAO();

        List<Evento> eventos = eventoDAO.buscarTodos(Evento.class);

        for(Evento e : eventos) {
            observableList.add(e);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
        col5.setCellValueFactory(new PropertyValueFactory<>("DataInicio"));
        col6.setCellValueFactory(new PropertyValueFactory<>("DataFim"));
    }



}
