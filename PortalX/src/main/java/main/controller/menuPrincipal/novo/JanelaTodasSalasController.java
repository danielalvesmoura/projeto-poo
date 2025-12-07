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
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.controller.GlobalController;
import model.Exportavel;
import model.Sala;
import servico.ExportarServico;
import servico.SalaServico;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JanelaTodasSalasController extends GlobalController<Object,Sala> {

    @Override
    protected void colocarT(Sala sala, Object controller) throws Exception {
        if(controller instanceof CadastroSalaController c) {
            c.salaAberto = sala;
            c.posCarregamento();

            c.confirmou.addListener((a, oldVal, newVal) -> {
                atualizaTabela();
            });
        }
    }
    @Override
    protected void colocarA(Object objetoA, Object controller) {}
    @Override
    protected void defineBorderPane(Object controller) {}

    @FXML
    public void fechar() throws Exception {
        trocaTela("/fxml/menuPrincipal/novo/menuPrincipal.fxml",null);
    }

    @FXML
    public void adicionar() throws Exception {
        modal("/fxml/menuPrincipal/novo/modalCadastroSala.fxml",null,null);
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
        posCarregamento();
    }

    public void posCarregamento() {
        configuraTabela();
        atualizaTabela();
    }

    SortedList<Sala> sortedData;

    @FXML
    public ChoiceBox campoTipoRelatorio;

    SalaServico salaServico = new SalaServico();

    @FXML
    public void gerarRelatorio() {
        List<Exportavel> lista = new ArrayList<>();

        lista.addAll(sortedData);

        ExportarServico exportarServico = new ExportarServico();
        exportarServico.escolheTipo(lista, campoTipoRelatorio.getValue().toString());
    }


    public void configuraTabela() {
        campoTipoRelatorio.getItems().addAll("CSV","Excel");
        campoTipoRelatorio.setValue("Excel");

        colId.setText("ID");
        col2.setText("");
        col3.setText("Nome");
        col4.setText("Localização");
        col5.setText("Capacidade");
        col6.setText("");
        col7.setText("");
        col8.setText("");
        col9.setText("");
        col9.setText("");

        colId.setPrefWidth(50);
        col2.setPrefWidth(0);
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

                String nomeFiltro = campoNome.getText().toLowerCase();
                String localizacaoFiltro = campoLocalizacao.getText().toLowerCase();
                String capacidadeMinimaFiltro = campoCapacidadeMinima.getText().toLowerCase();
                String capacidadeMaximaFiltro = campoCapacidadeMaxima.getText().toLowerCase();

                if (nomeFiltro.isEmpty() && localizacaoFiltro.isEmpty() && capacidadeMinimaFiltro.isEmpty() && capacidadeMaximaFiltro.isEmpty()) {
                    return true;
                }

                boolean match = true;
                if (!nomeFiltro.isEmpty()) {
                    match = match && sala.getNome().toLowerCase().contains(nomeFiltro);
                }
                if (!localizacaoFiltro.isEmpty()) {
                    match = match && sala.getLocalizacao().toLowerCase().contains(localizacaoFiltro);
                }

                if (!capacidadeMinimaFiltro.isEmpty()) {
                    try {
                        int minimo = Integer.parseInt(capacidadeMinimaFiltro);
                        match = match && sala.getCapacidade() >= minimo;

                    } catch (NumberFormatException e) {
                        return false;
                    }
                }

                if (!capacidadeMaximaFiltro.isEmpty()) {
                    try {
                        int max = Integer.parseInt(capacidadeMaximaFiltro);
                        match = match && sala.getCapacidade() <= max;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }

                return match;
            });
        };

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


                    try {
                        modal("/fxml/menuPrincipal/novo/modalCadastroSala.fxml",sala, null);
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




        // Lista ordenável
        sortedData = new SortedList<>(filteredList);

        // Liga a ordenação da tabela com a lista ordenada
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Adiciona os dados filtrados e ordenados à tabela
        tableView.setItems(sortedData);

    }


}
