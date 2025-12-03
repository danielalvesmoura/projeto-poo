package main.controller.janelaEvento.novo;

import dao.SalaDAO;
import dao.SessaoDAO;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Evento;
import model.Inscricao;
import model.Sessao;
import servico.InscricaoServico;
import util.Global;
import model.Sala;
import servico.SalaServico;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModalSalasController {

    public Stage stage;
    public SecaoCadastraSessaoController secaoCadastraSessaoController;
    public Evento eventoAberto;

    public ModalSalasController(Stage stage, Evento eventoAberto) {
        this.stage = stage;
        this.eventoAberto = eventoAberto;
    }

    @FXML
    public void cancelar(){
        fechar();
    }

    public void fechar() {
        Stage stage = (Stage) tableView.getScene().getWindow();
        stage.close();
    }

    @FXML
    public TableView<Sala> tableView;

    @FXML
    public TextField campoNome;
    @FXML
    public TextField campoLocalizacao;
    @FXML
    public TextField campoCapacidadeMinima;
    @FXML
    public TextField campoCapacidadeMaxima;


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

        colId.setText("ID");
        col2.setText("Nome");
        col3.setText("Localização");
        col4.setText("Capacidade");
        col5.setText("");
        col6.setText("");
        col7.setText("");


        colId.setPrefWidth(50);
        col2.setPrefWidth(50);
        col3.setPrefWidth(200);
        col4.setPrefWidth(300);
        col5.setPrefWidth(0);
        col6.setPrefWidth(100); // BOTÃO SELECIONAR
        col7.setPrefWidth(100); // BOTÃO CRONOGRAMA
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

                String capacidadeMinimaFiltro = campoCapacidadeMinima.getText().toLowerCase();
                String capacidadeMaximaFiltro = campoCapacidadeMaxima.getText().toLowerCase();


                // Se tudo estiver vazio → mostra tudo
                if (nomeFiltro.isEmpty() &&
                        localizacaoFiltro.isEmpty() &&
                        capacidadeMinimaFiltro.isEmpty() &&
                        capacidadeMaximaFiltro.isEmpty()
                ) {
                    return true;
                }

                boolean match = true;

                // -------------------------------------------------------------
                // 1) FILTROS DE STRING SIMPLES
                // -------------------------------------------------------------
                if (!nomeFiltro.isEmpty()) {match &= sala.getNome().toLowerCase().contains(nomeFiltro);}

                if (!localizacaoFiltro.isEmpty()) {match &= sala.getLocalizacao().toLowerCase().contains(localizacaoFiltro);}

                // FILTRO DE CAPACIDADE MÍNIMA
                if (!capacidadeMinimaFiltro.isEmpty()) {
                    try {
                        int min = Integer.parseInt(capacidadeMinimaFiltro);
                        match &= sala.getCapacidade() >= min;
                    } catch (NumberFormatException e) {
                        return false; // evita quebrar
                    }
                }

                // FILTRO DE CAPACIDADE MAXIMA
                if (!capacidadeMaximaFiltro.isEmpty()) {
                    try {
                        int max = Integer.parseInt(capacidadeMaximaFiltro);
                        match &= sala.getCapacidade() <= max;
                    } catch (NumberFormatException e) {
                        return false; // evita quebrar
                    }
                }


                return match;
            });
        };

        // Strings
        campoNome.textProperty().addListener(filtroListener);
        campoLocalizacao.textProperty().addListener(filtroListener);
        campoCapacidadeMinima.textProperty().addListener(filtroListener);
        campoCapacidadeMaxima.textProperty().addListener(filtroListener);
    }

    SalaDAO salaDAO = new SalaDAO();

    public void atualizaTabela() {
        observableList.clear();

        List<Sala> salas = salaDAO.buscarTodos(Sala.class);

        for (Sala sala : salas) {
            observableList.add(sala);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Localizacao"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Capacidade"));




        col7.setCellFactory(col -> new TableCell<Sala, Void>() {

            private final Button botaoSelecionar = new Button("Selecionar");

            {
                botaoSelecionar.setOnAction(event -> {
                    Sala salaAberta = getTableView().getItems().get(getIndex());

                    InscricaoServico inscricaoServico = new InscricaoServico();
                    int vagasTotais = inscricaoServico.vagasTotais(eventoAberto);

                    int vagasDoEvento = Integer.parseInt(eventoAberto.getCapacidade());

                    if(salaAberta.getCapacidade() + vagasTotais > vagasDoEvento) {
                        Global.mostraErro("A capacidade desta sala ultrapassa o limite de vagas do evento.\nCapacidade do evento: "
                        + vagasDoEvento + "\nVagas das salas já cadastradas: " + vagasTotais + "\nVagas da sala: " + salaAberta.getCapacidade());
                    } else {
                        secaoCadastraSessaoController.salaSelecionada = salaAberta;
                        secaoCadastraSessaoController.botaoSala.setText(salaAberta.getNome());

                        System.out.println("Sala selecionada: " + salaAberta.getId());

                        fechar();
                    }

                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    botaoSelecionar.setStyle("-fx-text-fill: #7800ff;");
                    setGraphic(botaoSelecionar);
                }
            }
        });

        col6.setCellFactory(col -> new TableCell<Sala, Void>() {

            private final Button botaoCronograma = new Button("Cronograma");

            {
                botaoCronograma.setOnAction(event -> {
                    Sala salaAberta = getTableView().getItems().get(getIndex());

                    FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/novo/modalCronogramaSala.fxml"));

                    ModalCronogramaSala modalCronogramaSala = new ModalCronogramaSala(salaAberta);
                    appLoader.setController(modalCronogramaSala);

                    Parent app = null;
                    try {
                        app = appLoader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Stage modal = new Stage();
                    modal.setTitle("Seleção de sala");
                    modal.setScene(new Scene(app));

                    // Modal bloqueia interação com a janela principal
                    modal.initModality(Modality.WINDOW_MODAL);

                    // Define que a janela principal é a "dona" do modal
                    modal.initOwner(stage);

                    modal.setResizable(true);

                    // Abre o modal e bloqueia até fechar
                    modal.showAndWait();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    botaoCronograma.setStyle("-fx-text-fill: yellow;");
                    setGraphic(botaoCronograma);
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
