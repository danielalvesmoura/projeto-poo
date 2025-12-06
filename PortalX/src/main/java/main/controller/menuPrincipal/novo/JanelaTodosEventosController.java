package main.controller.menuPrincipal.novo;

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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.controller.FiltroGenerico;
import main.controller.GlobalController;
import main.controller.janelaEvento.novo.JanelaEditarEventoController;
import main.controller.janelaEvento.novo.SecaoDetalhesController;
import model.Evento;
import model.Exportavel;
import servico.EventoServico;
import servico.ExportarServico;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class JanelaTodosEventosController extends GlobalController<Evento, Evento> {

    @Override
    protected void colocarT(Evento evento, Object controller) {
        if(controller instanceof JanelaEditarEventoController c) {
            c.eventoAberto = evento;

            try {
                c.posCarregamento();
            } catch (Exception e) {
                System.out.println("Erro ao tentar carregar JanelaEditarEventoController");
            }

        }
    }
    @Override
    protected void colocarA(Evento objetoA, Object controller) {}
    @Override
    protected void defineBorderPane(Object controller) {};

    @FXML
    public void fechar() throws Exception {
        trocaTela("/fxml/menuPrincipal/novo/menuPrincipal.fxml");
    }

    @FXML
    public void adicionar() throws Exception {
        trocaTela("/fxml/janelaEvento/novo/janelaEditarEvento.fxml",null);
    }


    @FXML
    public TableView<Evento> tableView;

    @FXML
    public TextField campoNome;
    @FXML
    public TextField campoEndereco;
    @FXML
    public TextField campoCapacidade;
    @FXML
    public DatePicker campoDataInicio;
    @FXML
    public TextField campoHoraInicio;
    @FXML
    public DatePicker campoDataFim;
    @FXML
    public TextField campoHoraFim;

    @FXML
    public TableColumn<Evento,String> colId;
    public TableColumn<Evento,String> col2;
    public TableColumn<Evento,String> col3;
    public TableColumn<Evento,String> col4;
    public TableColumn<Evento, LocalDate> col5;
    public TableColumn<Evento,LocalTime> col6;
    public TableColumn<Evento,LocalDate> col7;
    public TableColumn<Evento, LocalTime> col8;
    public TableColumn<Evento,Void> col9;
    public TableColumn<Evento,Void> col10;

    ObservableList<Evento> observableList = FXCollections.observableArrayList();

    // LISTA FILTRADA
    FilteredList<Evento> filteredList = new FilteredList<>(observableList, p -> true);


    @FXML
    public void initialize() {

        configuraTabela();

        atualizaTabela();
        tableView.setItems(observableList);
        atualizaTabela();
    }

    SortedList<Evento> sortedData;

    @FXML
    public ChoiceBox campoTipoRelatorio;

    @FXML
    public void exportarTabela() {
        List<Exportavel> lista = new ArrayList<>();

        lista.addAll(sortedData);

        ExportarServico exportarServico = new ExportarServico();
        exportarServico.escolheTipo(lista, campoTipoRelatorio.getValue().toString());
    }


    EventoDAO eventoDAO = new EventoDAO();
    EventoServico eventoServico = new EventoServico();

    public void configuraTabela() {
        campoTipoRelatorio.getItems().addAll("CSV","Excel");
        campoTipoRelatorio.setValue("Excel");

        colId.setText("ID");
        col2.setText("Nome");
        col3.setText("Capacidade");
        col4.setText("Endereço");
        col5.setText("Data do Início");
        col6.setText("Hora do Início");
        col7.setText("Data do Fim");
        col8.setText("Hora do Fim");
        col9.setText("");
        col10.setText("");


        colId.setPrefWidth(100);
        col2.setPrefWidth(150);
        col3.setPrefWidth(80);
        col4.setPrefWidth(250);
        col5.setPrefWidth(150);
        col6.setPrefWidth(100);
        col7.setPrefWidth(100);
        col8.setPrefWidth(100);
        col9.setPrefWidth(100); // BOTÃO REMOVER
        col10.setPrefWidth(80); // BOTÃO ABRIR

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colId.setStyle("-fx-alignment: CENTER;");
        col2.setStyle("-fx-alignment: CENTER;");
        col2.setStyle("-fx-alignment: CENTER;");
        col3.setStyle("-fx-alignment: CENTER;");
        col4.setStyle("-fx-alignment: CENTER;");
        col5.setStyle("-fx-alignment: CENTER;");
        col6.setStyle("-fx-alignment: CENTER;");
        col7.setStyle("-fx-alignment: CENTER;");
        col8.setStyle("-fx-alignment: CENTER;");


        /*
        ChangeListener<Object> filtroListener = (obs, valorAntigo, valorNovo) -> {

            filteredList.setPredicate(item -> {

                String filtroNome = campoNome.getText().toLowerCase();
                String filtroEndereco = campoEndereco.getText().toLowerCase();
                String filtroCapacidade = campoCapacidade.getText().toLowerCase();

                LocalDate filtroDataInicio = campoDataInicio.getValue();
                LocalDate filtroDataFim = campoDataFim.getValue();

                String filtroHoraInicio = campoHoraInicio.toString();
                String filtroHoraFim = campoHoraFim.toString();


                if (filtroNome.isEmpty() && filtroEndereco.isEmpty() && filtroCapacidade.isEmpty() && filtroDataInicio == null) {
                    return true;
                }

                if(!item.getNome().toLowerCase().contains(filtroNome) ||
                   !item.getEndereco().toLowerCase().contains(filtroEndereco) ||
                   !item.getCapacidade().toLowerCase().contains(filtroCapacidade)) return false;







                return true;
            });
        };



        campoNome.textProperty().addListener(filtroListener);
        campoEndereco.textProperty().addListener(filtroListener);
        campoCapacidade.textProperty().addListener(filtroListener);

         */

        // FILTRO



        ChangeListener<Object> filtroListener = (obs, oldValue, newValue) -> {

            filteredList.setPredicate(evento -> {

                String nomeFiltro = campoNome.getText().toLowerCase();
                String enderecoFiltro = campoEndereco.getText().toLowerCase();
                String capacidadeFiltro = campoCapacidade.getText().toLowerCase();
                String horaIniFiltroStr = campoHoraInicio.getText().toLowerCase();
                String horaFimFiltroStr = campoHoraFim.getText().toLowerCase();
                LocalDate dataIniFiltro = campoDataInicio.getValue();
                LocalDate dataFimFiltro = campoDataFim.getValue();

                // se tive vazio mostra tudo
                if (nomeFiltro.isEmpty() && enderecoFiltro.isEmpty() && capacidadeFiltro.isEmpty() && horaIniFiltroStr.isEmpty() && horaFimFiltroStr.isEmpty() && dataIniFiltro == null && dataFimFiltro == null) {
                    return true;
                }

                boolean match = true;

                if (!nomeFiltro.isEmpty()) {
                    match &= evento.getNome().toLowerCase().contains(nomeFiltro);
                }
                if (!enderecoFiltro.isEmpty()) {
                    match &= evento.getEndereco().toLowerCase().contains(enderecoFiltro);
                }
                if (!capacidadeFiltro.isEmpty()) {
                    match &= (evento.getCapacidade().contains(capacidadeFiltro));
                }

                LocalDate dataInicioEvento = evento.getDataInicio();
                LocalDate dataFimEvento = evento.getDataFim();

                if (dataIniFiltro != null && dataInicioEvento != null) {
                    match &= !dataInicioEvento.isBefore(dataIniFiltro);   // >=
                }
                if (dataFimFiltro != null && dataFimEvento != null) {
                    match &= !dataFimEvento.isAfter(dataFimFiltro);       // <=
                }

                try {
                    // Converte só se o usuário escreveu
                    if (!horaIniFiltroStr.isEmpty()) {
                        LocalTime horaIniFiltro = LocalTime.parse(horaIniFiltroStr);
                        match &= !evento.getHoraInicio().isBefore(horaIniFiltro);  // >=
                    }

                    if (!horaFimFiltroStr.isEmpty()) {
                        LocalTime horaFimFiltro = LocalTime.parse(horaFimFiltroStr);
                        match &= !evento.getHoraFim().isAfter(horaFimFiltro);      // <=
                    }

                } catch (Exception e) {

                }

                return match;
            });
        };

        campoNome.textProperty().addListener(filtroListener);
        campoEndereco.textProperty().addListener(filtroListener);
        campoCapacidade.textProperty().addListener(filtroListener);
        campoHoraInicio.textProperty().addListener(filtroListener);
        campoHoraFim.textProperty().addListener(filtroListener);
        campoDataInicio.valueProperty().addListener(filtroListener);
        campoDataFim.valueProperty().addListener(filtroListener);

         


    }

    public void atualizaTabela() {
        observableList.clear();

        List<Evento> eventos = eventoDAO.buscarTodos(Evento.class);

        // CARREGA LISTAS DE INSCRICOES DOS EVENTOS
        for (Evento e : eventos) {
            eventoServico.carregaListaInscricoes(e);

            observableList.add(e);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("CapacidadeView"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
        col5.setCellValueFactory(new PropertyValueFactory<>("DataInicio"));
        col6.setCellValueFactory(new PropertyValueFactory<>("HoraInicio"));
        col7.setCellValueFactory(new PropertyValueFactory<>("DataFim"));
        col8.setCellValueFactory(new PropertyValueFactory<>("HoraFim"));


        // BOTÃO DE REMOVER ITEM

        col9.setCellFactory(col -> new TableCell<Evento, Void>() {

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

        col10.setCellFactory(col -> new TableCell<Evento, Void>() {

            private final Button botaoAbrir = new Button("Abrir");

            {
                botaoAbrir.setOnAction(event -> {
                    Evento evento = getTableView().getItems().get(getIndex());

                    try {
                        trocaTela("/fxml/janelaEvento/novo/janelaEditarEvento.fxml",evento);
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
