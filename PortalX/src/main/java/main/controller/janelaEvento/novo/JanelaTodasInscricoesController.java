package main.controller.janelaEvento.novo;

import dao.InscricaoDAO;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.controller.GlobalController;
import main.controller.menuPrincipal.novo.MenuPrincipalController;
import model.Evento;
import model.Exportavel;
import model.Inscricao;
import servico.ExportarServico;
import servico.InscricaoServico;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JanelaTodasInscricoesController extends GlobalController<Inscricao, Evento> {

    @Override
    protected void colocarT(Evento evento, Object controller) {
        if(controller instanceof JanelaInscreverController c) {
            c.eventoAberto = evento;
            c.posCarregamento();
        }
    }
    @Override
    protected void colocarA(Inscricao inscricao, Object controller) {
        if(controller instanceof ModalInscricaoController c) {
            c.inscricaoAberta = inscricao;
            c.posCarregamento();

            c.confirmou.addListener((property,antigo,novo) -> {
                atualizaTabela();
            });
        }
    }
    @Override
    protected void defineBorderPane(Object controller) {};

    public Evento eventoAberto;

    @FXML
    public void fechar() throws Exception {
        trocaTela("/fxml/janelaEvento/novo/janelaEditarEvento.fxml",eventoAberto);
    }

    @FXML
    public void inscrever() throws Exception {
        trocaTela("/fxml/janelaEvento/novo/janelaInscreverPessoas.fxml", eventoAberto);
    }

    @FXML
    public TableView<Inscricao> tableView;

    @FXML
    public TextField campoNome;
    @FXML
    public TextField campoEmail;
    @FXML
    public TextField campoTelefone;
    @FXML
    public DatePicker campoDataNascimentoMinimo;
    @FXML
    public DatePicker campoDataNascimentoMaximo;
    @FXML
    public ChoiceBox campoTipo;
    @FXML
    public ChoiceBox campoStatus;
    @FXML
    public DatePicker campoDataInscricaoMinima;
    @FXML
    public DatePicker campoDataInscricaoMaxima;
    @FXML
    public ChoiceBox campoTipoRelatorio;

    @FXML
    public Label labelVagasDisponiveis;

    @FXML
    public TableColumn<Inscricao,String> colId;
    public TableColumn<Inscricao,String> col2;
    public TableColumn<Inscricao,String> col3;
    public TableColumn<Inscricao,String> col4;
    public TableColumn<Inscricao,LocalDate> col5;
    public TableColumn<Inscricao,String> col6;
    public TableColumn<Inscricao,Void> col7;
    public TableColumn<Inscricao, Void> col8;
    public TableColumn<Inscricao,Void> col9;
    public TableColumn<Inscricao, Void> col10;

    SortedList<Inscricao> sortedData;

    @FXML
    public void exportarTabela() {
        List<Exportavel> lista = new ArrayList<>();

        lista.addAll(sortedData);

        ExportarServico exportarServico = new ExportarServico();
        exportarServico.escolheTipo(lista, campoTipoRelatorio.getValue().toString());
    }


    ObservableList<Inscricao> observableList = FXCollections.observableArrayList();

    // LISTA FILTRADA
    FilteredList<Inscricao> filteredList = new FilteredList<>(observableList, p -> true);

    public void posCarregamento() {
        campoTipoRelatorio.getItems().addAll("CSV","Excel");
        campoTipoRelatorio.setValue("Excel");

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Email"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        col5.setCellValueFactory(new PropertyValueFactory<>("DataNascimento"));
        col6.setCellValueFactory(new PropertyValueFactory<>("TipoIngresso"));
        col7.setCellValueFactory(new PropertyValueFactory<>("Status"));

        campoTipo.getItems().addAll("","Participante","Palestrante");
        campoStatus.getItems().addAll("","Pendente","Confirmada","Cancelada");

        defineListerners();

        atualizaTabela();
    }

    InscricaoDAO inscricaoDAO = new InscricaoDAO();
    InscricaoServico inscricaoServico = new InscricaoServico();

    ChangeListener<Object> filtroListener;

    public void defineListerners() {
        filtroListener = (obs, oldValue, newValue) -> {

            filteredList.setPredicate(inscricao -> {

                String nomeFiltro = campoNome.getText().toLowerCase();
                String emailFiltro = campoEmail.getText().toLowerCase();
                String telefoneFiltro = campoTelefone.getText().toLowerCase();
                LocalDate dataIniFiltro = campoDataNascimentoMinimo.getValue();
                LocalDate dataFimFiltro = campoDataNascimentoMaximo.getValue();
                String tipoFiltro = campoTipo.getValue() == null ? "" : campoTipo.getValue().toString();
                String statusFiltro = campoStatus.getValue() == null ? "" : campoStatus.getValue().toString();
                LocalDate dataInscricaoMaximaFiltro = campoDataInscricaoMaxima.getValue();
                LocalDate dataInscricaoMinimaFiltro = campoDataInscricaoMinima.getValue();

                if (nomeFiltro.isEmpty() && emailFiltro.isEmpty() && telefoneFiltro.isEmpty() && tipoFiltro.isEmpty() && statusFiltro.isEmpty() &&
                        dataIniFiltro == null && dataFimFiltro == null && dataInscricaoMaximaFiltro == null && dataInscricaoMinimaFiltro == null) {
                    return true;
                }

                boolean match = true;
                if (!nomeFiltro.isEmpty()) {
                    match &= inscricao.getPessoa().getNome().toLowerCase().contains(nomeFiltro);
                }
                if (!emailFiltro.isEmpty()) {
                    match &= inscricao.getPessoa().getEmail().toLowerCase().contains(emailFiltro);
                }
                if (!telefoneFiltro.isEmpty()) {
                    match &= (inscricao.getPessoa().getTelefone().contains(telefoneFiltro));
                }
                if (tipoFiltro != null && !tipoFiltro.isEmpty()) {
                    match &= inscricao.getTipoIngresso().equalsIgnoreCase(tipoFiltro);
                }
                if (statusFiltro != null && !statusFiltro.isEmpty()) {
                    match &= inscricao.getStatus().equalsIgnoreCase(statusFiltro);
                }


                LocalDate dataNascimento = inscricao.getPessoa().getDataNascimento();

                if (dataIniFiltro != null && dataNascimento != null) {
                    match &= !dataNascimento.isBefore(dataIniFiltro);   // >=
                }
                if (dataFimFiltro != null && dataNascimento != null) {
                    match &= !dataNascimento.isAfter(dataFimFiltro);   // >=
                }
                if (dataInscricaoMaximaFiltro != null && inscricao.getDataCriacao() != null) {
                    match &= !inscricao.getDataCriacao().isBefore(dataInscricaoMaximaFiltro);       // <=
                }
                if (dataInscricaoMinimaFiltro != null && inscricao.getDataCriacao() != null) {
                    match &= !inscricao.getDataCriacao().isAfter(dataInscricaoMinimaFiltro);       // <=
                }

                return match;
            });
        };

        campoNome.textProperty().addListener(filtroListener);
        campoEmail.textProperty().addListener(filtroListener);
        campoTelefone.textProperty().addListener(filtroListener);
        campoTipo.valueProperty().addListener(filtroListener);
        campoStatus.valueProperty().addListener(filtroListener);
        campoDataNascimentoMaximo.valueProperty().addListener(filtroListener);
        campoDataNascimentoMinimo.valueProperty().addListener(filtroListener);
        campoDataInscricaoMinima.valueProperty().addListener(filtroListener);
        campoDataInscricaoMaxima.valueProperty().addListener(filtroListener);
    }

    public void atualizaTabela() {
        observableList.clear();

        List<Inscricao> inscricoes = inscricaoDAO.buscarTodos(Inscricao.class);

        for(Inscricao i : inscricoes) {
            if(i.getEventoId() == eventoAberto.getId()) {
                observableList.add(i);
            }
        }

        // BOTÃO DE REMOVER ITEM

        col8.setCellFactory(col -> new TableCell<Inscricao, Void>() {

            private final Button botaoRemover = new Button("Remover");

            {
                botaoRemover.setOnAction(event -> {
                    Inscricao e = getTableView().getItems().get(getIndex());
                    inscricaoServico.remover(e);
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


        // BOTÃO PARA ABRIR Inscricao

        col9.setCellFactory(col -> new TableCell<Inscricao, Void>() {

            private final Button botaoAbrir = new Button("Abrir");

            {
                botaoAbrir.setOnAction(event -> {
                    Inscricao inscricaoAberta = getTableView().getItems().get(getIndex());

                    try {
                        modal("/fxml/janelaEvento/novo/modalEditarInscricao.fxml",null,inscricaoAberta);

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


        colId.setText("ID");
        col2.setText("Nome");
        col3.setText("Email");
        col4.setText("Telefone");
        col5.setText("Data de Nascimento");
        col6.setText("Tipo de Ingresso");
        col7.setText("Status");
        col8.setText("");
        col9.setText("");
        col10.setText("");


        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colId.setMaxWidth(1f * Integer.MAX_VALUE * 2);
        col2.setMaxWidth(1f * Integer.MAX_VALUE * 23);
        col3.setMaxWidth(1f * Integer.MAX_VALUE * 23);
        col4.setMaxWidth(1f * Integer.MAX_VALUE * 13);
        col5.setMaxWidth(1f * Integer.MAX_VALUE * 8);
        col6.setMaxWidth(1f * Integer.MAX_VALUE * 8);
        col7.setMaxWidth(1f * Integer.MAX_VALUE * 3);
        col8.setMaxWidth(1f * Integer.MAX_VALUE * 3);
        col9.setMaxWidth(1f * Integer.MAX_VALUE * 3);
        col10.setMaxWidth(1f * Integer.MAX_VALUE * 0);

        colId.setStyle("-fx-alignment: CENTER;");
        col2.setStyle("-fx-alignment: CENTER;");
        col3.setStyle("-fx-alignment: CENTER;");
        col4.setStyle("-fx-alignment: CENTER;");
        col5.setStyle("-fx-alignment: CENTER;");
        col6.setStyle("-fx-alignment: CENTER;");
        col8.setStyle("-fx-alignment: CENTER;");

        // Lista ordenável
        sortedData = new SortedList<>(filteredList);

        // Liga a ordenação da tabela com a lista ordenada
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Adiciona os dados filtrados e ordenados à tabela
        tableView.setItems(sortedData);

        labelVagasDisponiveis.setText(inscricaoServico.vagasDisponiveis(eventoAberto) + " / " +
                inscricaoServico.vagasTotais(eventoAberto) + " vagas disponíveis");
    }


}
