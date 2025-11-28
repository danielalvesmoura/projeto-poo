package main.controller.menuPrincipal.tabelas;

import dao.EventoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.controller.janelaEvento.JanelaEventoController;
import model.*;
import servico.EventoServico;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TabelaEventoController implements Initializable {
    public TabelaEventoController tabelaEventoController;


    @FXML
    public TableView<Evento> tableView;

    @FXML
    public TextField campoFiltro;

    @FXML
    public TableColumn<Evento,String> colId;
    public TableColumn<Evento,String> col2;
    public TableColumn<Evento,Integer> col3;
    public TableColumn<Evento,String> col4;
    public TableColumn<Evento,LocalDate> col5;
    public TableColumn<Evento,LocalDate> col6;
    public TableColumn<Evento,Void> col7;
    public TableColumn<Evento,Void> col8;
    public TableColumn<Evento,Void> col9;

    @FXML
    public Pane modalPane;

    // PANE DE CONTERÁ JANELA DO EVENTO
    @FXML
    public Pane paneTelaInteira;

    @FXML
    public Pane paneTelaInteiraAdicionar;

    ObservableList<Evento> observableList = FXCollections.observableArrayList();

    // LISTA FILTRADA
    FilteredList<Evento> filteredList = new FilteredList<>(observableList, p -> true);



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizaTabela();
        tableView.setItems(observableList);
    }

    public Pane paneTelaInteiraMenuPrincipal;  // PANE VINDO DO MENU PRINCIPAL

    @FXML
    public void botaoAdicionar() {
        try {
            FXMLLoader janelaAdicionarLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/janelaEvento.fxml"));

            JanelaEventoController janelaEventoController = new JanelaEventoController();
            janelaEventoController.janelaEventoController = janelaEventoController;
            janelaEventoController.tabelaEventoController = tabelaEventoController;

            janelaAdicionarLoader.setController(janelaEventoController);

            Parent janelaAdicionar = janelaAdicionarLoader.load();

            janelaEventoController.tituloJanelaEvento.setText("Adicionar novo evento");

            //modalAdicionar.setLayoutX(-531);
            //janelaAdicionar.setLayoutY(100);

            paneTelaInteiraMenuPrincipal.getChildren().add(janelaAdicionar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    EventoDAO eventoDAO = new EventoDAO();
    EventoServico eventoServico = new EventoServico();
    public void atualizaTabela() {
        observableList.clear();

        List<Evento> eventos = eventoDAO.buscarTodos(Evento.class);

        for(Evento e : eventos) {
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


                    try {
                        FXMLLoader janelaEventoLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/janelaEvento.fxml"));

                        JanelaEventoController janelaEventoController = new JanelaEventoController();
                        janelaEventoController.janelaEventoController = janelaEventoController;
                        janelaEventoController.tabelaEventoController = tabelaEventoController;

                        janelaEventoLoader.setController(janelaEventoController);

                        janelaEventoController.eventoAberto = evento;

                        Parent janela = janelaEventoLoader.load();

                        janelaEventoController.tituloJanelaEvento.setText(evento.getNome());

                        paneTelaInteiraMenuPrincipal.getChildren().add(janela);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

        col2.setText("Nome");
        col3.setText("Capacidade");
        col4.setText("Endereço");
        col5.setText("Data do Início");
        col6.setText("Data do Fim");

        col2.setPrefWidth(400);
        col3.setPrefWidth(200);
        col4.setPrefWidth(300);
        col5.setPrefWidth(150);
        col6.setPrefWidth(150);
        col7.setPrefWidth(100); // BOTÃO REMOVER
        col8.setPrefWidth(80); // BOTÃO ABRIR
        col9.setPrefWidth(1000);

        colId.setStyle("-fx-alignment: CENTER;");
        col2.setStyle("-fx-alignment: CENTER;");
        col3.setStyle("-fx-alignment: CENTER;");
        col4.setStyle("-fx-alignment: CENTER;");
        col5.setStyle("-fx-alignment: CENTER;");
        col6.setStyle("-fx-alignment: CENTER;");
        col7.setStyle("-fx-alignment: CENTER;");
        col8.setStyle("-fx-alignment: CENTER;");

        campoFiltro.textProperty().addListener((obs, oldValue, newValue) -> {
            filteredList.setPredicate(pessoa -> {

                // Se o campo estiver vazio, mostra tudo
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filter = newValue.toLowerCase();

                // Condição de filtro (pode adicionar mais colunas aqui)
                if (pessoa.getNome().toLowerCase().contains(filter)) {
                    return true;
                }

                return false;
            });
        });

        // Lista ordenável
        SortedList<Evento> sortedData = new SortedList<>(filteredList);

        // Liga a ordenação da tabela com a lista ordenada
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Adiciona os dados filtrados e ordenados à tabela
        tableView.setItems(sortedData);

    }

}
