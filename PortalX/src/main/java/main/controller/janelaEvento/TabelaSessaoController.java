package main.controller.janelaEvento;

import dao.SessaoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.Evento;
import model.Sessao;
import servico.SessaoServico;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaSessaoController {

    public TabelaSessaoController tabelaController;
    public JanelaEventoController janelaEventoController;
    public Evento eventoAberto;

    @FXML
    public TableView<Sessao> tableView;

    @FXML
    public Pane paneAncoraAba;

    @FXML
    public TableColumn<Sessao,String> colId;
    public TableColumn<Sessao,String> col2;
    public TableColumn<Sessao,String> col3;
    public TableColumn<Sessao,String> col4;
    public TableColumn<Sessao, LocalDate> col5;
    public TableColumn<Sessao, LocalTime> col6;
    public TableColumn<Sessao,LocalDate> col7;
    public TableColumn<Sessao,LocalTime> col8;
    public TableColumn<Sessao,Void> col9;
    public TableColumn<Sessao,Void> col10;

    ObservableList<Sessao> observableList = FXCollections.observableArrayList();


    public void initializeManual() {
        atualizaTabela();
        tableView.setItems(observableList);
    }

    public void botaoAdicionar() {
        try {
            FXMLLoader cadastroSessaoLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/cadastroSessao.fxml"));

            CadastroSessaoController cadastroSessaoController = new CadastroSessaoController();
            cadastroSessaoLoader.setController(cadastroSessaoController);
            cadastroSessaoController.tabelaSessaoController = tabelaController;
            cadastroSessaoController.janelaEventoController = janelaEventoController;

            Parent janela = cadastroSessaoLoader.load();

            janela.setLayoutY(20);
            janela.setLayoutX(20);

            cadastroSessaoController.eventoAberto = eventoAberto;
            cadastroSessaoController.modo = "Adicionar";

            paneAncoraAba.getChildren().add(janela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    SessaoDAO sessaoDAO = new SessaoDAO();
    SessaoServico sessaoServico = new SessaoServico();

    public void atualizaTabela() {
        observableList.clear();

        List<Sessao> sessoes = sessaoDAO.buscarTodos(Sessao.class);

        for(Sessao s : sessoes) {
            if(s.getEvento().getId() == eventoAberto.getId()) {
                observableList.add(s);
            }
        }

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
                    Sessao s = getTableView().getItems().get(getIndex());
                    sessaoServico.remover(s);
                    atualizaTabela();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(botaoRemover);
                }
            }
        });


        // BOTÃO PARA ABRIR EVENTO

        col10.setCellFactory(col -> new TableCell<Sessao, Void>() {

            private final Button botaoAbrir = new Button("Abrir");

            {
                botaoAbrir.setOnAction(event -> {
                    Sessao sessao = getTableView().getItems().get(getIndex());


                    try {
                        FXMLLoader cadastroSessaoLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/cadastroSessao.fxml"));

                        CadastroSessaoController cadastroSessaoController = new CadastroSessaoController();
                        cadastroSessaoController.janelaEventoController = janelaEventoController;
                        cadastroSessaoController.tabelaSessaoController = tabelaController;

                        cadastroSessaoLoader.setController(cadastroSessaoController);

                        cadastroSessaoController.sessaoAberta = sessao;

                        Parent janela = cadastroSessaoLoader.load();

                        janela.setLayoutY(20);
                        janela.setLayoutX(20);

                        cadastroSessaoController.eventoAberto = eventoAberto;
                        cadastroSessaoController.modo = "Alterar";
                        cadastroSessaoController.carregaValores();

                        paneAncoraAba.getChildren().add(janela);

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
                    setGraphic(botaoAbrir);
                }
            }
        });

        col2.setText("Título");
        col3.setText("Tipo");
        col4.setText("Status");
        col5.setText("Data do Início");
        col6.setText("Hora Início");
        col7.setText("Data do Fim");
        col8.setText("Hora Fim");
        col9.setText("");
        col10.setText("");

        col2.setPrefWidth(400);
        col3.setPrefWidth(150);
        col4.setPrefWidth(120);
        col5.setPrefWidth(100);
        col6.setPrefWidth(100);
        col7.setPrefWidth(100); // BOTÃO REMOVER
        col8.setPrefWidth(100); // BOTÃO ABRIR
    }

}
