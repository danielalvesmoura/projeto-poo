package main.controller.menuPrincipal.novo;

import dao.PessoaDAO;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import main.controller.GlobalController;
import model.Pessoa;
import servico.PessoaServico;

import java.time.LocalDate;

public class JanelaTodasPessoasController extends GlobalController<Pessoa, Pessoa> {

    @Override
    protected void colocarT(Pessoa pessoa, Object controller) {
        if(controller instanceof CadastroPessoaController c) {
            c.pessoaAberta = pessoa;
            c.posCarregamento();

            c.confirmou.addListener((property,antigo,novo) -> {
                atualizaTabela();
            });
        }
    }
    @Override
    protected void colocarA(Pessoa pessoa, Object controller) {}
    @Override
    protected void defineBorderPane(Object controller) {};

    @FXML
    public void fechar() throws Exception {
        trocaTela("/fxml/menuPrincipal/novo/menuPrincipal.fxml",null);
    }


    @FXML
    public void adicionar() throws Exception {
        modal("/fxml/menuPrincipal/novo/modalCadastroPessoa.fxml",null,null);
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
    public TableColumn<Pessoa,String> colId;
    public TableColumn<Pessoa,String> col2;
    public TableColumn<Pessoa,String> col3;
    public TableColumn<Pessoa,String> col4;
    public TableColumn<Pessoa,LocalDate> col5;
    public TableColumn<Pessoa,String> col6;
    public TableColumn<Pessoa,Void> col7;
    public TableColumn<Pessoa, Void> col8;
    public TableColumn<Pessoa,Void> col9;
    public TableColumn<Pessoa, Void> col10;


    ObservableList<Pessoa> observableList = FXCollections.observableArrayList();

    // LISTA FILTRADA
    FilteredList<Pessoa> filteredList = new FilteredList<>(observableList, p -> true);

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("Email"));
        col4.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        col5.setCellValueFactory(new PropertyValueFactory<>("DataNascimento"));

        configuraTabela();

        atualizaTabela();
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

        ChangeListener<Object> filtroListener = (obs, oldValue, newValue) -> {filteredList.setPredicate(pessoa -> {

                String nomeFiltro = campoNome.getText().toLowerCase();
                String emailFiltro = campoEmail.getText().toLowerCase();
                String telefoneFiltro = campoTelefone.getText().toLowerCase();
                LocalDate dataIniFiltro = campoDataNascimentoMinimo.getValue();
                LocalDate dataFimFiltro = campoDataNascimentoMaximo.getValue();

                if (nomeFiltro.isEmpty() && emailFiltro.isEmpty() && telefoneFiltro.isEmpty() && dataIniFiltro == null && dataFimFiltro == null) {
                    return true;
                }
                boolean match = true;

                if (!nomeFiltro.isEmpty()) {match = match && pessoa.getNome().toLowerCase().contains(nomeFiltro);}
                if (!emailFiltro.isEmpty()) {match = match && pessoa.getEmail().toLowerCase().contains(emailFiltro);}
                if (!telefoneFiltro.isEmpty()) { match = match && (pessoa.getTelefone().contains(telefoneFiltro));}

                LocalDate dataNascimento = pessoa.getDataNascimento();

                if (dataIniFiltro != null && dataNascimento != null) {
                    match = match && !dataNascimento.isBefore(dataIniFiltro);   // >=
                }

                if (dataFimFiltro != null && dataNascimento != null) {
                    match = match && !dataNascimento.isAfter(dataFimFiltro);       // <=
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

    public void atualizaTabela() {
        observableList.clear();
        observableList.addAll(pessoaDAO.buscarTodos(Pessoa.class));


        // BOTÃO DE REMOVER ITEM

        col7.setCellFactory(col -> new TableCell<Pessoa, Void>() {

            private final Button botaoRemover = new Button("Remover");

            {
                botaoRemover.setOnAction(event -> {
                    Pessoa e = getTableView().getItems().get(getIndex());
                    pessoaServico.remover(e);
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

        // BOTÃO PARA ABRIR Pessoa

        col8.setCellFactory(col -> new TableCell<Pessoa, Void>() {

            private final Button botaoAbrir = new Button("Abrir");

            {
                botaoAbrir.setOnAction(event -> {
                    Pessoa pessoa = getTableView().getItems().get(getIndex());

                    try {
                        modal("/fxml/menuPrincipal/novo/modalCadastroPessoa.fxml", pessoa, null);
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



        SortedList<Pessoa> sortedData = new SortedList<>(filteredList);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);


    }



}
