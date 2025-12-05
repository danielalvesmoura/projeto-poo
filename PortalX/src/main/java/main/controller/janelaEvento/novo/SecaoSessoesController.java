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
import main.controller.GlobalController;
import model.ArvoreSessoes;
import model.Evento;
import model.Sessao;
import servico.SessaoServico;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class SecaoSessoesController extends GlobalController<Sessao, Evento, JanelaEditarEventoController> {

    @Override
    protected void colocarT(Evento evento, Object controller) {
        if(controller instanceof SecaoCadastraSessaoController c) {
            c.eventoAberto = evento;
        }
    }
    @Override
    protected void colocarA(Sessao sessao, Object controller) {
        if(controller instanceof SecaoCadastraSessaoController c) {
            c.sessaoAberta = sessao;
            c.posCarregamento();
        }
    }
    @Override
    protected void defineBorderPane(Object controller) {
        if (controller instanceof SecaoCadastraSessaoController c) {
            c.setPanes(super.conteudo, super.borderpaneMenor);
        }
    };

    @Override
    public void setPanes(BorderPane conteudo, BorderPane bp) {
        super.setPanes(conteudo, bp);

    }

    public Evento eventoAberto;

    public void posCarregamento() {
        atualizaTabela();
        tableView.setItems(observableList);
    }

    @FXML
    public void adicionar() throws Exception {
        trocaBorderPane("/fxml/janelaEvento/novo/secaoCadastraSessao.fxml",eventoAberto, null);

    }

    @FXML
    public TableView<Sessao> tableView;

    @FXML
    public TableColumn<Sessao,String> colId;
    public TableColumn<Sessao,String> col2;
    public TableColumn<Sessao,String> col3;
    public TableColumn<Sessao,Integer> col4;
    public TableColumn<Sessao, String> col5;
    public TableColumn<Sessao,LocalDate> col6;
    public TableColumn<Sessao,LocalTime> col7;
    public TableColumn<Sessao, LocalDate> col8;
    public TableColumn<Sessao,LocalTime> col9;
    public TableColumn<Sessao,Void> col10;
    public TableColumn<Sessao,Void> col11;

    ObservableList<Sessao> observableList = FXCollections.observableArrayList();

    // LISTA FILTRADA
    FilteredList<Sessao> filteredList = new FilteredList<>(observableList, p -> true);


    SessaoDAO sessaoDAO = new SessaoDAO();
    SessaoServico sessaoServico = new SessaoServico();

    public void atualizaTabela() {
        observableList.clear();

        //List<Sessao> sessoes = sessaoDAO.buscarTodos(Sessao.class);

        ArvoreSessoes sessoes = sessaoServico.carregaArvoreEvento(eventoAberto);

        sessoes.adicionaItensNaLista(observableList, sessoes.getSessaoRaiz());

        /*
        for(Sessao s : sessoes) {
            if(s.getEvento().getId() == eventoAberto.getId()) {
                observableList.add(s);
            }
        }

         */

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("SalaNome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Titulo"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Tipo"));
        col5.setCellValueFactory(new PropertyValueFactory<>("Status"));
        col6.setCellValueFactory(new PropertyValueFactory<>("DataInicio"));
        col7.setCellValueFactory(new PropertyValueFactory<>("HoraInicio"));
        col8.setCellValueFactory(new PropertyValueFactory<>("DataFim"));
        col9.setCellValueFactory(new PropertyValueFactory<>("HoraFim"));


        // BOTÃO DE REMOVER ITEM

        col10.setCellFactory(col -> new TableCell<Sessao, Void>() {

            private final Button botaoRemover = new Button("Remover");

            {
                botaoRemover.setOnAction(event -> {
                    Sessao sessao = getTableView().getItems().get(getIndex());
                    sessaoServico.remover(sessao, eventoAberto);

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

        col11.setCellFactory(col -> new TableCell<Sessao, Void>() {

            private final Button botaoAbrir = new Button("Abrir");

            {
                botaoAbrir.setOnAction(event -> {
                    Sessao sessaoAberta = getTableView().getItems().get(getIndex());

                    try {
                        trocaBorderPane("/fxml/janelaEvento/novo/secaoCadastraSessao.fxml", eventoAberto, sessaoAberta);
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
        col2.setText("Sala");
        col3.setText("Título");
        col4.setText("Tipo");
        col5.setText("Status");
        col6.setText("Data do Início");
        col7.setText("Hora do Início");
        col8.setText("Data do Fim");
        col9.setText("Hora do Fim");
        col10.setText("");
        col11.setText("");


        col2.setPrefWidth(200);
        col3.setPrefWidth(200);
        col4.setPrefWidth(100);
        col5.setPrefWidth(100);
        col6.setPrefWidth(90);
        col7.setPrefWidth(90);
        col8.setPrefWidth(90);
        col9.setPrefWidth(90);
        col10.setPrefWidth(80); // BOTÃO REMOVER
        col11.setPrefWidth(60); // BOTÃO ABRIR

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
