package main.controller.menuPrincipal;

import dao.EventoDAO;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import main.controller.janelaEvento.JanelaEventoController;
import model.Evento;
import servico.EventoServico;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class JanelaTodosEventosController {

    public Stage stage;

    @FXML
    public void fechar() throws IOException {
        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/menuPrincipal.fxml"));

        MenuPrincipalController menuPrincipalController = new MenuPrincipalController();
        appLoader.setController(menuPrincipalController);

        menuPrincipalController.stage = stage;

        Parent app = appLoader.load();

        Scene menuPrincipal = new Scene(app);

        stage.setScene(menuPrincipal);
    }

    @FXML
    public TableView<Evento> tableView;

    @FXML
    public TextField campoNome;
    @FXML
    public TextField campoEndereco;

    @FXML
    public TableColumn<Evento,String> colId;
    public TableColumn<Evento,String> col2;
    public TableColumn<Evento,Integer> col3;
    public TableColumn<Evento,String> col4;
    public TableColumn<Evento, LocalDate> col5;
    public TableColumn<Evento,LocalDate> col6;
    public TableColumn<Evento,Void> col7;
    public TableColumn<Evento,Void> col8;
    public TableColumn<Evento,Void> col9;
    public TableColumn<Evento,Void> col10;

    ObservableList<Evento> observableList = FXCollections.observableArrayList();

    // LISTA FILTRADA
    FilteredList<Evento> filteredList = new FilteredList<>(observableList, p -> true);

    @FXML
    public void initialize() {
        atualizaTabela();
        tableView.setItems(observableList);
        atualizaTabela();
    }

    EventoDAO eventoDAO = new EventoDAO();
    EventoServico eventoServico = new EventoServico();
    public void atualizaTabela() {
        observableList.clear();

        List<Evento> eventos = eventoDAO.buscarTodos(Evento.class);

        for (Evento e : eventos) {
            observableList.add(e);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Capacidade"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
        col5.setCellValueFactory(new PropertyValueFactory<>("DataInicio"));
        col6.setCellValueFactory(new PropertyValueFactory<>("DataFim"));


        // BOTÃO DE REMOVER ITEM

        col7.setCellFactory(col -> new TableCell<Evento, Void>() {

            private final Button botaoRemover = new Button("Remover");

            {
                botaoRemover.setOnAction(event -> {
                    Evento e = getTableView().getItems().get(getIndex());
                    eventoServico.remover(e);
                    atualizaTabela();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    botaoRemover.setStyle("-fx-text-fill: red;");
                    setGraphic(botaoRemover);
                }
            }
        });


        // BOTÃO PARA ABRIR EVENTO

        col8.setCellFactory(col -> new TableCell<Evento, Void>() {

            private final Button botaoAbrir = new Button("Abrir");

            {
                botaoAbrir.setOnAction(event -> {
                    Evento evento = getTableView().getItems().get(getIndex());

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    botaoAbrir.setStyle("-fx-text-fill: #7800ff;");
                    setGraphic(botaoAbrir);
                }
            }

        });

        colId.setText("ID");
        col2.setText("Nome");
        col3.setText("Capacidade");
        col4.setText("Endereço");
        col5.setText("Data do Início");
        col6.setText("Data do Fim");
        col7.setText("");
        col8.setText("");
        col9.setText("");
        col10.setText("");


        col2.setPrefWidth(400);
        col3.setPrefWidth(200);
        col4.setPrefWidth(300);
        col5.setPrefWidth(150);
        col6.setPrefWidth(150);
        col7.setPrefWidth(100); // BOTÃO REMOVER
        col8.setPrefWidth(80); // BOTÃO ABRIR
        col9.setPrefWidth(0);
        col10.setPrefWidth(0);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colId.setStyle("-fx-alignment: CENTER;");
        col2.setStyle("-fx-alignment: CENTER;");
        col3.setStyle("-fx-alignment: CENTER;");
        col4.setStyle("-fx-alignment: CENTER;");
        col5.setStyle("-fx-alignment: CENTER;");
        col6.setStyle("-fx-alignment: CENTER;");
        col7.setStyle("-fx-alignment: CENTER;");
        col8.setStyle("-fx-alignment: CENTER;");

        // Listener genérico para atualizar o filtro
        ChangeListener<String> filtroListener = (obs, oldValue, newValue) -> {
            filteredList.setPredicate(evento -> {

                // Converte os filtros para lowercase
                String nomeFiltro = campoNome.getText().toLowerCase();
                String enderecoFiltro = campoEndereco.getText().toLowerCase();

                // Se ambos os campos estiverem vazios → mostra tudo
                if (nomeFiltro.isEmpty() && enderecoFiltro.isEmpty()) {
                    return true;
                }

                boolean match = true;

                // Aplica filtro do nome
                if (!nomeFiltro.isEmpty()) {
                    match &= evento.getNome().toLowerCase().contains(nomeFiltro);
                }

                // Aplica filtro do endereço
                if (!enderecoFiltro.isEmpty()) {
                    match &= evento.getEndereco().toLowerCase().contains(enderecoFiltro);
                }

                return match;
            });
        };

        // OIE: Adiciona o mesmo listener em ambos os campos de texto
        campoNome.textProperty().addListener(filtroListener);
        campoEndereco.textProperty().addListener(filtroListener);

        // Lista ordenável
        SortedList<Evento> sortedData = new SortedList<>(filteredList);

        // Liga a ordenação da tabela com a lista ordenada
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Adiciona os dados filtrados e ordenados à tabela
        tableView.setItems(sortedData);

    }
}
