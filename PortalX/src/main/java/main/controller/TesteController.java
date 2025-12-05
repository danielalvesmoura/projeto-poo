package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    public TableColumn<Produto, Void> col3;

    ObservableList<Produto> observableList = FXCollections.observableArrayList();
    FilteredList<Produto> filteredList = new FilteredList<>(observableList, item -> true);

    @FXML
    public ChoiceBox choiceAtributo;

    @FXML
    public void initialize() {

        col1.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Preco"));

        filtroNome.textProperty().addListener((a,b,c) -> atualizaTabela());
        filtroPreco.textProperty().addListener((a,b,c) -> atualizaTabela());

        choiceAtributo.getItems().addAll("Nome","Preço");
        choiceAtributo.setValue("Nome");
    }


    @FXML
    public void enviar() {
        observableList.add(new Produto(campoNome.getText(),Double.parseDouble(campoPreco.getText())));
        atualizaTabela();
    }

    @FXML
    public Label titulo;

    public void atualizaTabela() {
        filteredList.setPredicate(item -> {
            if(!(item.getNome().contains(filtroNome.getText()))) return false;
            if(!(Double.toString(item.getPreco()).contains(filtroPreco.getText()))) return false;

            return true;
        });

        col3.setCellFactory(col -> new TableCell<Produto,Void>() {
            private Button botao = new Button("botao");

            {
                botao.setOnAction(event -> {
                    Produto produtoSelecionado = getTableView().getItems().get(getIndex());

                    switch (choiceAtributo.getValue().toString()) {
                        case "Nome":
                            titulo.setText(produtoSelecionado.getNome());
                            break;
                        case "Preço":
                            titulo.setText(Double.toString(produtoSelecionado.getPreco()));

                    }

                    atualizaTabela();


                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if(empty) return;

                setGraphic(botao);
            }
        }
        );

        tabela.setItems(filteredList);
    }
}
