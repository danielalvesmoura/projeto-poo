package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TesteController {

    @FXML
    public Label label;

    @FXML
    public TextField campoNome;
    @FXML
    public TextField campoPreco;

    @FXML
    public TextField filtroNome;
    @FXML
    public TextField filtroPreco;



    @FXML
    public TableView<Produto> tabela;
    public TableColumn<Produto, String> col1;
    public TableColumn<Produto, Double> col2;

    ObservableList<Produto> observableList = FXCollections.observableArrayList();
    FilteredList<Produto> filteredList = new FilteredList<>(observableList, item -> true);

    @FXML
    public void initialize() {

        col1.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Preco"));

        filteredList.setPredicate(item -> {
            if(!(item.getNome().contains(filtroNome.getText()))) return false;
            if(!(Double.toString(item.getPreco()).contains(filtroPreco.getText()))) return false;

            return true;
        });

        filtroNome.textProperty().addListener((a,b,c) -> atualizaTabela());
        filtroPreco.textProperty().addListener((a,b,c) -> atualizaTabela());
    }


    @FXML
    public void enviar() {
        observableList.add(new Produto(campoNome.getText(),Double.parseDouble(campoPreco.getText())));
    }

    public void atualizaTabela() {
        tabela.setItems(null);
        tabela.setItems(filteredList);
    }
}
