package main.controller.tabelas;

import dao.EventoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import main.controller.janelaEvento.JanelaEventoController;
import model.*;
import servico.EventoServico;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TabelaEventoController implements Initializable {
    TabelaEventoController tabelaEventoController;

    public void defineTabelaEventoController(TabelaEventoController tabelaEventoController) {
        this.tabelaEventoController = tabelaEventoController;
    }

    @FXML
    public TableView<Evento> tableView;

    @FXML
    public TableColumn<Evento,String> colId;
    public TableColumn<Evento,String> col2;
    public TableColumn<Evento,String> col3;
    public TableColumn<Evento,String> col4;
    public TableColumn<Evento,LocalDate> col5;
    public TableColumn<Evento,LocalDate> col6;
    public TableColumn<Evento,Void> col7;
    public TableColumn<Evento,Void> col8;

    @FXML
    public Pane modalPane;

    // PANE DE CONTERÁ JANELA DO EVENTO
    @FXML
    public Pane paneJanelaEvento;

    ObservableList<Evento> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizaTabela();
        tableView.setItems(observableList);
    }



    public void botaoAdicionar() {
        try {
            FXMLLoader modalAdicionarLoader = new FXMLLoader(getClass().getResource("/fxml/modal/modalAdicionarEvento.fxml"));

            modalAdicionarLoader.setController(tabelaEventoController);

            Parent modalAdicionar = modalAdicionarLoader.load();

            //modalAdicionar.setLayoutX(-531);
            modalAdicionar.setLayoutY(100);

            modalPane.getChildren().add(modalAdicionar);

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
        col3.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
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
                        FXMLLoader janelaEventoLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/janelaEventoBase.fxml"));

                        JanelaEventoController janelaEventoController = new JanelaEventoController();
                        //janelaEventoController.defineController(janelaEventoController);
                        janelaEventoLoader.setController(janelaEventoController);

                        Parent janela = janelaEventoLoader.load();

                        //janela.setLayoutY(-100);

                        janelaEventoController.eventoAberto = evento;

                        paneJanelaEvento.getChildren().add(janela);

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

        col2.setText("Nome");
        col3.setText("Descrição");
        col4.setText("Endereço");
        col5.setText("Data do Início");
        col6.setText("Data do Fim");

        col2.setPrefWidth(300);
        col3.setPrefWidth(400);
        col4.setPrefWidth(300);
        col5.setPrefWidth(100);
        col6.setPrefWidth(100);
        col7.setPrefWidth(70); // BOTÃO REMOVER
        col8.setPrefWidth(50); // BOTÃO ABRIR
    }




    // MODAL ADICIONAR

    @FXML
    private Pane paneModalAdicionar;

    public void fecharModal() {
        ((Pane) paneModalAdicionar.getParent()).getChildren().clear();
    }

    @FXML
    private TextField campoNome;
    @FXML
    private TextArea campoDescricao;
    @FXML
    private TextField campoEndereco;
    @FXML
    private DatePicker campoDataInicio;
    @FXML
    private DatePicker campoDataFim;
    @FXML
    private Label mensagem;

    public void confirmar() {
        System.out.println(campoNome.getText());
        System.out.println(campoDescricao.getText());
        System.out.println(campoEndereco.getText());
        System.out.println(campoDataInicio.getValue());
        System.out.println(campoDataFim.getValue());

        eventoServico.cadastrar(campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(),campoDataFim.getValue());

        mensagem.setStyle("-fx-text-fill: green;");
        mensagem.setText("Evento cadastrado com sucesso!");

        tabelaEventoController.atualizaTabela();
    }
}
