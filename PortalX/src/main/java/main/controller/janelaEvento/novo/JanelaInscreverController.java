package main.controller.janelaEvento.novo;

import dao.InscricaoDAO;
import dao.PessoaDAO;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.controller.GlobalController;
import main.controller.menuPrincipal.novo.CadastroPessoaController;
import main.controller.menuPrincipal.novo.MenuPrincipalController;
import model.Evento;
import model.Inscricao;
import model.Pessoa;
import servico.InscricaoServico;
import servico.PessoaServico;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JanelaInscreverController extends GlobalController<Object,Evento> {

    @Override
    protected void colocarT(Evento evento, Object controller) {
        if(controller instanceof JanelaTodasInscricoesController c) {
            c.eventoAberto = evento;
            c.posCarregamento();
        }
    }

    @Override
    protected void colocarA(Object objetoA, Object controller) {}
    @Override
    protected void defineBorderPane(Object controller) {};

    public Evento eventoAberto;

    @FXML
    public void fechar() throws Exception {
        trocaTela("/fxml/janelaEvento/novo/janelaTodasInscricoes.fxml", eventoAberto);
    }




    @FXML
    public TableView<Pessoa> tableView;

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
    public Label labelVagasDisponiveis;

    @FXML
    public TableColumn<Pessoa,String> colId;
    public TableColumn<Pessoa,String> col2;
    public TableColumn<Pessoa,String> col3;
    public TableColumn<Pessoa,String> col4;
    public TableColumn<Pessoa,LocalDate> col5;
    public TableColumn<Pessoa,Boolean> col6;
    public TableColumn<Pessoa,Void> col7;
    public TableColumn<Pessoa, Void> col8;
    public TableColumn<Pessoa,Void> col9;
    public TableColumn<Pessoa, Void> col10;


    ObservableList<Pessoa> observableList = FXCollections.observableArrayList();

    // LISTA FILTRADA
    FilteredList<Pessoa> filteredList = new FilteredList<>(observableList, p -> true);

    public void posCarregamento() {
        campoTipo.getItems().addAll("Participante","Palestrante");
        campoTipo.setValue("Participante");

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Email"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        col5.setCellValueFactory(new PropertyValueFactory<>("DataNascimento"));

        tableView.setEditable(true);
        col6.setEditable(true);

        configuraTabela();

        atualizaTabela();
    }

    @FXML
    public ChoiceBox campoTipo;

    InscricaoServico inscricaoServico = new InscricaoServico();

    @FXML
    public void confirmar() throws Exception {
        List<Pessoa> pessoasSelecionadas = tableView.getItems().stream().filter(Pessoa::isSelecionado).toList();

        inscricaoServico.cadastrar(eventoAberto,pessoasSelecionadas,campoTipo.toString());



        fechar();
    }


    PessoaDAO pessoaDAO = new PessoaDAO();
    PessoaServico pessoaServico = new PessoaServico();

    ChangeListener<Object> filtroListener;

    public void configuraTabela() {
        colId.setPrefWidth(50);
        col2.setPrefWidth(300);
        col3.setPrefWidth(300);
        col4.setPrefWidth(300);
        col5.setPrefWidth(150);
        col6.setPrefWidth(0);
        col7.setPrefWidth(100);// BOTÃO REMOVER
        col8.setPrefWidth(100);// BOTÃO ABRIR

        colId.setText("ID");
        col2.setText("Nome");
        col3.setText("Email");
        col4.setText("Telefone");
        col5.setText("Data de Nascimento");
        col6.setText("");
        col7.setText("");
        col8.setText("");

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colId.setMaxWidth(1f * Integer.MAX_VALUE * 1);
        col2.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        col3.setMaxWidth(1f * Integer.MAX_VALUE * 25);
        col4.setMaxWidth(1f * Integer.MAX_VALUE * 15);
        col5.setMaxWidth(1f * Integer.MAX_VALUE * 10);
        col6.setMaxWidth(1f * Integer.MAX_VALUE * 15);


        colId.setStyle("-fx-alignment: CENTER;");
        col2.setStyle("-fx-alignment: CENTER;");
        col3.setStyle("-fx-alignment: CENTER;");
        col4.setStyle("-fx-alignment: CENTER;");
        col5.setStyle("-fx-alignment: CENTER;");
        col6.setStyle("-fx-alignment: CENTER;");
        col7.setStyle("-fx-alignment: CENTER;");
        col8.setStyle("-fx-alignment: CENTER;");

        ChangeListener<Object> filtroListener = (obs, oldValue, newValue) -> {

            filteredList.setPredicate(pessoa -> {

                String nomeFiltro = campoNome.getText().toLowerCase();
                String emailFiltro = campoEmail.getText().toLowerCase();
                String telefoneFiltro = campoTelefone.getText().toLowerCase();
                LocalDate dataIniFiltro = campoDataNascimentoMinimo.getValue();
                LocalDate dataFimFiltro = campoDataNascimentoMaximo.getValue();

                if (nomeFiltro.isEmpty() && emailFiltro.isEmpty() && telefoneFiltro.isEmpty() && dataIniFiltro == null && dataFimFiltro == null) {
                    return true;
                }

                boolean match = true;

                if (!nomeFiltro.isEmpty()) {
                    match &= pessoa.getNome().toLowerCase().contains(nomeFiltro);
                }
                if (!emailFiltro.isEmpty()) {
                    match &= pessoa.getEmail().toLowerCase().contains(emailFiltro);
                }
                if (!telefoneFiltro.isEmpty()) {
                    match &= (pessoa.getTelefone().contains(telefoneFiltro));
                }

                LocalDate dataNascimento = pessoa.getDataNascimento();

                if (dataIniFiltro != null && dataNascimento != null) {
                    match &= !dataNascimento.isBefore(dataIniFiltro);   // >=
                }
                if (dataFimFiltro != null && dataNascimento != null) {
                    match &= !dataNascimento.isAfter(dataFimFiltro);       // <=
                }

                return match;
            });
        };

        campoNome.textProperty().addListener(filtroListener);
        campoEmail.textProperty().addListener(filtroListener);
        campoTelefone.textProperty().addListener(filtroListener);
        campoDataNascimentoMaximo.valueProperty().addListener(filtroListener);
        campoDataNascimentoMinimo.valueProperty().addListener(filtroListener);
    }

    InscricaoDAO inscricaoDAO = new InscricaoDAO();
    ArrayList<Pessoa> pessoasSelecionadas = new ArrayList<>();

    public void atualizaTabela() {
        observableList.clear();

        List<Pessoa> pessoas = pessoaDAO.buscarTodos(Pessoa.class);
        List<Inscricao> inscricoes = inscricaoDAO.buscarTodos(Inscricao.class);

        for(Pessoa pessoa : pessoas) {
            boolean temInscricao = false;

            for(Inscricao inscricao : inscricoes) {
                if(inscricao.getPessoa().getId() == pessoa.getId() && inscricao.getEvento().getId() == eventoAberto.getId()) {
                    temInscricao = true;
                }
            }

            if (!temInscricao) {
                observableList.add(pessoa);
            }
        }

        col6.setCellValueFactory(param -> param.getValue().selecionadoProperty());
        col6.setCellFactory(CheckBoxTableCell.forTableColumn(col6));

        SortedList<Pessoa> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);


        labelVagasDisponiveis.setText(inscricaoServico.vagasDisponiveis(eventoAberto) + " / " +
                inscricaoServico.vagasTotais(eventoAberto) + " vagas disponíveis");
    }



}
