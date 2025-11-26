package main.controller.menuPrincipal.tabelas;

import dao.PalestranteDAO;
import dao.ParticipanteDAO;
import dao.PessoaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import main.controller.menuPrincipal.AbaPessoaController;
import model.Palestrante;
import model.Participante;
import model.Pessoa;
import servico.PalestranteServico;
import servico.ParticipanteServico;
import servico.PessoaServico;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaPessoasController implements Initializable {
    public TabelaPessoasController tabelaPessoasController;

    public void defineTabelaPessoasController(TabelaPessoasController tabelaPessoasController) {
        this.tabelaPessoasController = tabelaPessoasController;
    }

    @FXML
    public TableView<Pessoa> tableView;

    @FXML
    public TableColumn<Pessoa,String> colId;
    public TableColumn<Pessoa,String> col2;
    public TableColumn<Pessoa,String> col3;
    public TableColumn<Pessoa,String> col4;
    public TableColumn<Pessoa,LocalDate> col5;
    public TableColumn<Pessoa,LocalDate> col6;
    public TableColumn<Pessoa,Void> col7;
    public TableColumn<Pessoa,Void> col8;

    // PANE DE CONTERÁ A ABA DE CADASTRO
    @FXML
    public Pane paneJanelaParcial;

    ObservableList<Pessoa> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        atualizaTabela();
        tableView.setItems(observableList);
    }


    @FXML
    public void botaoAdicionar() {
        try {
            FXMLLoader janelaAdicionarLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/abaAdicionarPessoa.fxml"));

            AbaPessoaController abaPessoaController = new AbaPessoaController();
            abaPessoaController.tabelaPessoasController = tabelaPessoasController;
            janelaAdicionarLoader.setController(abaPessoaController);

            Parent janelaAdicionar = janelaAdicionarLoader.load();

            System.out.println("era pra abrir");

            paneJanelaParcial.getChildren().add(janelaAdicionar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    PalestranteDAO palestranteDAO = new PalestranteDAO();
    ParticipanteDAO participanteDAO = new ParticipanteDAO();

    PalestranteServico palestranteServico = new PalestranteServico();
    ParticipanteServico participanteServico = new ParticipanteServico();

    PessoaDAO pessoaDAO = new PessoaDAO();
    PessoaServico pessoaServico = new PessoaServico();



    public void atualizaTabela() {
        observableList.clear();

        List<Palestrante> palestrantes = palestranteDAO.buscarTodos(Palestrante.class);
        List<Participante> participantes = participanteDAO.buscarTodos(Participante.class);

        for(Palestrante p : palestrantes) {
            observableList.add(p);
        }
        for(Participante p : participantes) {
            observableList.add(p);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("email"));
        col4.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        col5.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));


        // BOTÃO DE REMOVER ITEM

        col7.setCellFactory(col -> new TableCell<Pessoa, Void>() {

            private final Button botaoRemover = new Button("Remover");

            {
                botaoRemover.setOnAction(event -> {
                    Pessoa p = getTableView().getItems().get(getIndex());
                    pessoaServico.remover(p);
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

        col8.setCellFactory(col -> new TableCell<Pessoa, Void>() {

            private final Button botaoAbrir = new Button("Abrir");

            {
                botaoAbrir.setOnAction(event -> {
                    Pessoa pessoaAberta = getTableView().getItems().get(getIndex());


                    try {
                        FXMLLoader janelaPessoaLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/abaAdicionarPessoa.fxml"));

                        AbaPessoaController abaPessoaController = new AbaPessoaController();
                        abaPessoaController.tabelaPessoasController = tabelaPessoasController;
                        janelaPessoaLoader.setController(abaPessoaController);

                        abaPessoaController.pessoaAberta = pessoaAberta;

                        Parent janela = janelaPessoaLoader.load();


                        paneJanelaParcial.getChildren().add(janela);

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
        col4.setText("Telefone");
        col5.setText("Data de Nascimento");

        col2.setPrefWidth(300);
        col3.setPrefWidth(400);
        col4.setPrefWidth(300);
        col5.setPrefWidth(100);
        col6.setPrefWidth(100);
        col7.setPrefWidth(70); // BOTÃO REMOVER
        col8.setPrefWidth(50); // BOTÃO ABRIR
    }

}
