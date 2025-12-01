package main.controller.menuPrincipal.novo;

import dao.SalaDAO;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.controller.Global;
import model.Sala;
import model.Sala;
import servico.SalaServico;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class JanelaTodasSalasController {

    public Stage stage;


    @FXML
    public void fechar() throws IOException {
        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/novo/menuPrincipal.fxml"));

        MenuPrincipalController menuPrincipalController = new MenuPrincipalController(stage);
        appLoader.setController(menuPrincipalController);

        menuPrincipalController.stage = stage;

        Parent app = appLoader.load();

        Scene menuPrincipal = new Scene(app);

        stage.setScene(menuPrincipal);


    }

    @FXML
    public void adicionar() throws IOException {
        /*
        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/fxml/janelaSala/novo/janelaEditarSala.fxml"));

        JanelaEditarSalaController janelaEditarSalaController = new JanelaEditarSalaController(stage);
        appLoader.setController(janelaEditarSalaController);
        janelaEditarSalaController.janelaEditarSalaController = janelaEditarSalaController;

        janelaEditarSalaController.stage = stage;

        Parent app = appLoader.load();

        Scene scene = new Scene(app);

        stage.setScene(scene);

         */


    }

    @FXML
    public TableView<Sala> tableView;

    @FXML
    public TextField campoNome;
    @FXML
    public TextField campoLocalizacao;
    @FXML
    public TextField campoCapacidade;
    @FXML
    public TextField campoNotaMinima;
    @FXML
    public TextField campoNotaMaxima;


    @FXML
    public TableColumn<Sala,String> colId;
    public TableColumn<Sala,Double> col2;
    public TableColumn<Sala,String> col3;
    public TableColumn<Sala,String> col4;
    public TableColumn<Sala, Integer> col5;
    public TableColumn<Sala,Void> col6;
    public TableColumn<Sala,Void> col7;
    public TableColumn<Sala,Void> col8;
    public TableColumn<Sala,Void> col9;
    public TableColumn<Sala,Void> col10;

    ObservableList<Sala> observableList = FXCollections.observableArrayList();

    // LISTA FILTRADA
    FilteredList<Sala> filteredList = new FilteredList<>(observableList, p -> true);

    @FXML
    public void initialize() {
        configuraTabela();

        atualizaTabela();
        tableView.setItems(observableList);
        atualizaTabela();
    }

    SortedList<Sala> sortedData;

    @FXML
    public ChoiceBox campoTipoRelatorio;

    SalaServico salaServico = new SalaServico();

    @FXML
    public void gerarRelatorio() {
        if(campoTipoRelatorio.getValue().toString().equals("CSV")) {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
            File arquivo = fc.showSaveDialog(null);

            if (arquivo != null) {
                salaServico.gerarCSV(sortedData, arquivo);
                Global.mostraMensagem("Portal X","Relatório CSV exportado com sucesso!");
            }
        } else {

            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel", "*.xlsx"));
            File arquivo = fc.showSaveDialog(null);

            if (arquivo != null) {
                salaServico.gerarExcel(sortedData, arquivo);
                Global.mostraMensagem("Portal X","Relatório Excel exportado com sucesso!");
            }
        }
    }


    public void configuraTabela() {
        campoTipoRelatorio.getItems().addAll("CSV","Excel");
        campoTipoRelatorio.setValue("Excel");

        colId.setText("ID");
        col2.setText("Nota");
        col3.setText("Nome");
        col4.setText("Localização");
        col5.setText("Capacidade");
        col6.setText("");
        col7.setText("");


        colId.setPrefWidth(100);
        col2.setPrefWidth(400);
        col3.setPrefWidth(200);
        col4.setPrefWidth(300);
        col5.setPrefWidth(150);
        col6.setPrefWidth(100); // BOTÃO REMOVER
        col7.setPrefWidth(100); // BOTÃO ABRIR
        col8.setPrefWidth(0);
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


        // FILTRO

        // Listener genérico para atualizar o filtro
        ChangeListener<Object> filtroListener = (obs, oldValue, newValue) -> {
            filteredList.setPredicate(sala -> {

                // Strings
                String nomeFiltro       = campoNome.getText().toLowerCase();
                String localizacaoFiltro   = campoLocalizacao.getText().toLowerCase();
                String capacidadeFiltro = campoCapacidade.getText().toLowerCase();

                // Horas (strings vindas dos TextFields)
                String notaMinimaFiltro = campoNotaMinima.getText().toLowerCase();
                String notaMaximaFiltro = campoNotaMaxima.getText().toLowerCase();

                // Se tudo estiver vazio → mostra tudo
                if (nomeFiltro.isEmpty() &&
                        localizacaoFiltro.isEmpty() &&
                        capacidadeFiltro.isEmpty() &&
                        notaMinimaFiltro.isEmpty() &&
                        notaMaximaFiltro.isEmpty()
                ) {
                    return true;
                }

                boolean match = true;

                // -------------------------------------------------------------
                // 1) FILTROS DE STRING SIMPLES
                // -------------------------------------------------------------
                if (!nomeFiltro.isEmpty()) {match &= sala.getNome().toLowerCase().contains(nomeFiltro);}

                if (!localizacaoFiltro.isEmpty()) {match &= sala.getLocalizacao().toLowerCase().contains(localizacaoFiltro);}

                if (!capacidadeFiltro.isEmpty()) { match &= (Integer.toString(sala.getCapacidade()).contains(capacidadeFiltro));}

                // data início → deve ser >= campo
                if (notaMinimaFiltro != null && Double.toString(sala.getNota()) != null) {
                    match &= !(sala.getNota() < Double.parseDouble(notaMinimaFiltro));   // >=
                }

                // data fim → deve ser <= campo
                if (notaMaximaFiltro != null && Double.toString(sala.getNota()) != null) {
                    match &= !(sala.getNota() > Double.parseDouble(notaMaximaFiltro));   // >=
                }

                return match;
            });
        };

        // Strings
        campoNome.textProperty().addListener(filtroListener);
        campoLocalizacao.textProperty().addListener(filtroListener);
        campoCapacidade.textProperty().addListener(filtroListener);
        campoNotaMinima.textProperty().addListener(filtroListener);
    }

    SalaDAO salaDAO = new SalaDAO();

    public void atualizaTabela() {
        observableList.clear();

        List<Sala> salas = salaDAO.buscarTodos(Sala.class);

        for (Sala sala : salas) {
            observableList.add(sala);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Nota"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Localizacao"));
        col5.setCellValueFactory(new PropertyValueFactory<>("Capacidade"));


        // BOTÃO DE REMOVER ITEM

        col6.setCellFactory(col -> new TableCell<Sala, Void>() {

            private final Button botaoRemover = new Button("Remover");

            {
                botaoRemover.setOnAction(event -> {
                    Sala e = getTableView().getItems().get(getIndex());
                    salaServico.remover(e);
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

        col7.setCellFactory(col -> new TableCell<Sala, Void>() {

            private final Button botaoAbrir = new Button("Abrir");

            {
                botaoAbrir.setOnAction(event -> {
                    Sala sala = getTableView().getItems().get(getIndex());

                    /*
                    try {
                        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/fxml/janelaSala/novo/janelaEditarSala.fxml"));

                        JanelaEditarSalaController janelaEditarSalaController = new JanelaEditarSalaController(stage,evento);
                        appLoader.setController(janelaEditarSalaController);
                        janelaEditarSalaController.janelaEditarSalaController = janelaEditarSalaController;

                        janelaEditarSalaController.stage = stage;

                        Parent app = appLoader.load();

                        Scene scene = new Scene(app);

                        stage.setScene(scene);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                     */
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




        // Lista ordenável
        sortedData = new SortedList<>(filteredList);

        // Liga a ordenação da tabela com a lista ordenada
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Adiciona os dados filtrados e ordenados à tabela
        tableView.setItems(sortedData);

    }
}
