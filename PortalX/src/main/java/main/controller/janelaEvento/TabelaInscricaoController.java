package main.controller.janelaEvento;

import dao.InscricaoDAO;
import dao.SessaoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import main.controller.menuPrincipal.tabelas.TabelaEventoController;
import model.Evento;
import model.Inscricao;
import model.Sessao;
import servico.EventoServico;
import servico.InscricaoServico;
import servico.SessaoServico;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TabelaInscricaoController {
    public TabelaInscricaoController tabelaInscricaoController;
    public JanelaEventoController janelaEventoController;
    public Evento eventoAberto;

    @FXML
    public TableView<Inscricao> tableView;

    @FXML
    public Pane paneAncoraAba;

    @FXML
    public TableColumn<Inscricao,String> colId;
    public TableColumn<Inscricao,String> col2;
    public TableColumn<Inscricao,String> col3;
    public TableColumn<Inscricao,String> col4;
    public TableColumn<Inscricao, LocalDate> col5;
    public TableColumn<Inscricao, LocalTime> col6;
    public TableColumn<Inscricao,LocalDate> col7;
    public TableColumn<Inscricao,LocalTime> col8;
    public TableColumn<Inscricao,Void> col9;
    public TableColumn<Inscricao,Void> col10;

    ObservableList<Inscricao> observableList = FXCollections.observableArrayList();

    public void initializeManual() {
        atualizaTabela();
        tableView.setItems(observableList);
    }

    public void botaoInscrever() {
        try {
            FXMLLoader tabelaInscreverLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/tableviewInscrever.fxml"));

            TabelaInscreverController tabelaInscreverController = new TabelaInscreverController();
            tabelaInscreverLoader.setController(tabelaInscreverController);
            //cadastroSessaoController.tabelaSessaoController = tabelaController;
            tabelaInscreverController.janelaEventoController = janelaEventoController;

            Parent janela = tabelaInscreverLoader.load();

            //janela.setLayoutY(20);
            //janela.setLayoutX(20);

            tabelaInscreverController.eventoAberto = eventoAberto;

            paneAncoraAba.getChildren().add(janela);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    InscricaoDAO inscricaoDAO = new InscricaoDAO();

    public void atualizaTabela() {
        observableList.clear();

        List<Inscricao> inscricoes = inscricaoDAO.buscarTodos(Inscricao.class);

        for(Inscricao i : inscricoes) {
            if(i.getEvento().getId() == eventoAberto.getId()) {
                observableList.add(i);
                System.out.println(i);
            }
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>(""));
        col3.setCellValueFactory(new PropertyValueFactory<>("tipoingresso"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Status"));
        col5.setCellValueFactory(new PropertyValueFactory<>("datacriacao"));
        //col6.setCellValueFactory(new PropertyValueFactory<>("DataCriacao"));

        // BOTÃO DE REMOVER ITEM

        InscricaoServico inscricaoServico = new InscricaoServico();

        col9.setCellFactory(col -> new TableCell<Inscricao, Void>() {

            private final Button botaoRemover = new Button("Remover");

            {
                botaoRemover.setOnAction(event -> {
                    Inscricao i = getTableView().getItems().get(getIndex());
                    inscricaoServico.remover(i);
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


        // BOTÃO PARA ABRIR

        col10.setCellFactory(col -> new TableCell<Inscricao, Void>() {

            private final Button botaoAbrir = new Button("Abrir");

            {
                botaoAbrir.setOnAction(event -> {
                    Inscricao inscricao = getTableView().getItems().get(getIndex());


                    try {
                        FXMLLoader editarInscricaoLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/editarInscricao.fxml"));

                        EditarInscricaoController editarInscricaoController = new EditarInscricaoController();
                        //cadastroSessaoController.janelaEventoController = janelaEventoController;
                        editarInscricaoController.tabelaInscricaoController = tabelaInscricaoController;

                        editarInscricaoLoader.setController(editarInscricaoController);

                        editarInscricaoController.inscricao = inscricao;

                        Parent janela = editarInscricaoLoader.load();

                        //janela.setLayoutY(20);
                        //janela.setLayoutX(20);

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

        col2.setText("Nome");
        col3.setText("Email");
        col4.setText("Status");
        col5.setText("Tipo do Ingresso");
        col6.setText("Data de Criação");
        col7.setText("");
        col8.setText("");
        col9.setText("");
        col10.setText("");

        col2.setPrefWidth(200);
        col3.setPrefWidth(300);
        col4.setPrefWidth(120);
        col5.setPrefWidth(100);
        col6.setPrefWidth(100);
        col7.setPrefWidth(0); // BOTÃO REMOVER
        col8.setPrefWidth(1000); // BOTÃO ABRIR
    }

}
