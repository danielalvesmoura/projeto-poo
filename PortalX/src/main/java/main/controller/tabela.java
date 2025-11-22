package main.controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import main.controller.Produto;

import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class tabela implements Initializable {

    @FXML
    public TableView<Produto> tabelaa;

    @FXML
    public TableColumn<Produto,String> colNome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        tabelaa.setItems(observableList);
    }

    ObservableList<Produto> observableList = FXCollections.observableArrayList(
            new Produto("salve")
    );
}
