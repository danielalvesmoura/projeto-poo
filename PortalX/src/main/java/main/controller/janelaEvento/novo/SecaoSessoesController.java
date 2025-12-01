package main.controller.janelaEvento.novo;

import dao.SessaoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.ArvoreSessoesTeste;
import model.Evento;
import model.Sessao;
import servico.SessaoServico;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class SecaoSessoesController {

    public Stage stage;
    public Evento eventoAberto;
    public BorderPane borderpaneConteudo;
    JanelaEditarEventoController janelaEditarEventoController;

    public SecaoSessoesController(Stage stage, Evento eventoAberto) {
        this.stage = stage;
        this.eventoAberto = eventoAberto;
    }

    @FXML
    public void adicionar() throws IOException {
        borderpaneConteudo.getChildren().clear();

        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/novo/secaoCadastraSessao.fxml"));

        SecaoCadastraSessaoController secaoCadastraSessaoController = new SecaoCadastraSessaoController(stage,eventoAberto);
        appLoader.setController(secaoCadastraSessaoController);
        secaoCadastraSessaoController.janelaEditarEventoController = janelaEditarEventoController;

        Parent app = appLoader.load();

        borderpaneConteudo.setCenter(app);

    }

    @FXML
    public TableView<Sessao> tableView;

    @FXML
    public TableColumn<Sessao,String> colId;
    public TableColumn<Sessao,String> col2;
    public TableColumn<Sessao,Integer> col3;
    public TableColumn<Sessao,String> col4;
    public TableColumn<Sessao, LocalDate> col5;
    public TableColumn<Sessao,LocalTime> col6;
    public TableColumn<Sessao,LocalDate> col7;
    public TableColumn<Sessao, LocalTime> col8;
    public TableColumn<Sessao,Void> col9;
    public TableColumn<Sessao,Void> col10;

    ObservableList<Sessao> observableList = FXCollections.observableArrayList();

    // LISTA FILTRADA
    FilteredList<Sessao> filteredList = new FilteredList<>(observableList, p -> true);

    @FXML
    public void initialize() {
        atualizaTabela();
        tableView.setItems(observableList);
    }

    SessaoDAO sessaoDAO = new SessaoDAO();
    SessaoServico sessaoServico = new SessaoServico();

    public void atualizaTabela() {
        observableList.clear();

        //List<Sessao> sessoes = sessaoDAO.buscarTodos(Sessao.class);

        ArvoreSessoesTeste sessoes = sessaoServico.carregaArvore();

        sessoes.adicionaItensNaLista(observableList, sessoes.getSessaoRaiz());

        /*
        for(Sessao s : sessoes) {
            if(s.getEvento().getId() == eventoAberto.getId()) {
                observableList.add(s);
            }
        }

         */

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Status"));
        col5.setCellValueFactory(new PropertyValueFactory<>("DataInicio"));
        col6.setCellValueFactory(new PropertyValueFactory<>("HoraInicio"));
        col7.setCellValueFactory(new PropertyValueFactory<>("DataFim"));
        col8.setCellValueFactory(new PropertyValueFactory<>("HoraFim"));


        // BOTÃO DE REMOVER ITEM

        col9.setCellFactory(col -> new TableCell<Sessao, Void>() {

            private final Button botaoRemover = new Button("Remover");

            {
                botaoRemover.setOnAction(event -> {
                    Sessao sessao = getTableView().getItems().get(getIndex());
                    sessaoServico.remover(sessao, eventoAberto);

                    try {
                        janelaEditarEventoController.abreAbaSessoes();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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

        col10.setCellFactory(col -> new TableCell<Sessao, Void>() {

            private final Button botaoAbrir = new Button("Abrir");

            {
                botaoAbrir.setOnAction(event -> {
                    Sessao sessaoAberta = getTableView().getItems().get(getIndex());

                    try {
                        borderpaneConteudo.getChildren().clear();

                        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/novo/secaoCadastraSessao.fxml"));

                        SecaoCadastraSessaoController secaoCadastraSessaoController = new SecaoCadastraSessaoController(stage,eventoAberto,sessaoAberta);
                        appLoader.setController(secaoCadastraSessaoController);
                        secaoCadastraSessaoController.janelaEditarEventoController = janelaEditarEventoController;

                        Parent app = appLoader.load();

                        borderpaneConteudo.setCenter(app);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    System.out.println("clicou em alterar");
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
        col2.setText("Título");
        col3.setText("Tipo");
        col4.setText("Status");
        col5.setText("Data do Início");
        col6.setText("Hora do Início");
        col7.setText("Data do Fim");
        col8.setText("Hora do Fim");
        col9.setText("");
        col10.setText("");


        col2.setPrefWidth(400);
        col3.setPrefWidth(200);
        col4.setPrefWidth(300);
        col5.setPrefWidth(150);
        col6.setPrefWidth(150);
        col7.setPrefWidth(100);
        col8.setPrefWidth(100);
        col9.setPrefWidth(100); // BOTÃO REMOVER
        col10.setPrefWidth(80); // BOTÃO ABRIR

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colId.setStyle("-fx-alignment: CENTER;");
        col2.setStyle("-fx-alignment: CENTER;");
        col3.setStyle("-fx-alignment: CENTER;");
        col4.setStyle("-fx-alignment: CENTER;");
        col5.setStyle("-fx-alignment: CENTER;");
        col6.setStyle("-fx-alignment: CENTER;");
        col7.setStyle("-fx-alignment: CENTER;");
        col8.setStyle("-fx-alignment: CENTER;");


    }
}
