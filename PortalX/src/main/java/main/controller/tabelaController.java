package main.controller;

import dao.EventoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import main.controller.Produto;

import java.util.List;

import javafx.scene.control.*;
import model.Evento;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class tabelaController implements Initializable {

    @FXML
    public TableView<Evento> tabelaEventos;

    @FXML
    public TableColumn<Evento,String> colId;
    public TableColumn<Evento,String> colNome;
    public TableColumn<Evento,String> colDescricao;
    public TableColumn<Evento,String> colEndereco;
    public TableColumn<Evento,LocalDate> colDataInicio;
    public TableColumn<Evento,LocalDate> colDataFim;

    ObservableList<Evento> observableList = FXCollections.observableArrayList(
            new Evento("iftech","coisas acontecem","casa do caralho", LocalDate.of(2025,11,22),LocalDate.of(2025,11,25))
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EventoDAO eventoDAO = new EventoDAO();

        List<Evento> eventos = eventoDAO.buscarTodos(Evento.class);

        for(Evento e : eventos) {
            observableList.add(e);
        }


        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
        colDataInicio.setCellValueFactory(new PropertyValueFactory<>("DataInicio"));
        colDataFim.setCellValueFactory(new PropertyValueFactory<>("DataFim"));
        tabelaEventos.setItems(observableList);
    }



}
