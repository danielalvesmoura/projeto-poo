package main.controller.janelaEvento;

import dao.InscricaoDAO;
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
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import main.controller.menuPrincipal.CadastroPessoaController;
import model.*;
import servico.InscricaoServico;
import servico.PalestranteServico;
import servico.ParticipanteServico;
import servico.PessoaServico;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TabelaInscreverController{
    public TabelaInscreverController tabelaInscreverController;
    public JanelaEventoController janelaEventoController;
    public TabelaInscricaoController tabelaInscricaoController;

    @FXML
    public TableView<Pessoa> tableView;

    @FXML
    public TableColumn<Pessoa,String> colId;
    public TableColumn<Pessoa,String> col2;
    public TableColumn<Pessoa,String> col3;
    public TableColumn<Pessoa,String> col4;
    public TableColumn<Pessoa,LocalDate> col5;
    public TableColumn<Pessoa,Void> col6;

    public Evento eventoAberto;

    ObservableList<Pessoa> observableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        atualizaTabela();
        tableView.setItems(observableList);
    }

    PalestranteDAO palestranteDAO = new PalestranteDAO();
    ParticipanteDAO participanteDAO = new ParticipanteDAO();
    InscricaoDAO inscricaoDAO = new InscricaoDAO();
    PessoaDAO pessoaDAO = new PessoaDAO();

    ArrayList<Pessoa> pessoasSelecionadas = new ArrayList<>();

    List<Inscricao> inscricoes;

    public void atualizaTabela() {
        observableList.clear();

        List<Palestrante> palestrantes = palestranteDAO.buscarTodos(Palestrante.class);
        List<Participante> participantes = participanteDAO.buscarTodos(Participante.class);
        inscricoes = inscricaoDAO.buscarTodos(Inscricao.class);

        List<Pessoa> pessoas = pessoaDAO.buscarTodos(Pessoa.class);


        for(Pessoa pessoa : pessoas) {
            boolean temInscricao = false;

            for(Inscricao inscricao : inscricoes) {
                if(inscricao.getPessoa().getId() == pessoa.getId()) {
                    temInscricao = true;
                }
            }

            if (temInscricao == false) {
                observableList.add(pessoa);
            }
        }

        //for(Palestrante p : palestrantes) {
        //    if(inscricaoDAO.find(Inscricao.class, p.getId()) == null) {
        //        observableList.add(p);
        //    }
        //}
        //for(Participante p : participantes) {
        //    if(inscricaoDAO.find(Inscricao.class, p.getId()) == null) {
        //        observableList.add(p);
        //    }
        //}

        colId.setCellValueFactory(new PropertyValueFactory<>("Id"));
        col2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        col3.setCellValueFactory(new PropertyValueFactory<>("email"));
        col4.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        col5.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));



        // CHECK BOX

        PessoaDAO pessoaDAO = new PessoaDAO();

        col6.setCellFactory(column -> new TableCell<Pessoa, Void>() {

            private final CheckBox check = new CheckBox();

            {
                // define ação do checkbox
                check.setOnAction(e -> {
                    Pessoa pessoa = getTableView().getItems().get(getIndex());

                    if(pessoasSelecionadas.contains(pessoa)) {
                        pessoasSelecionadas.remove(pessoa);
                    } else {
                        pessoasSelecionadas.add(pessoa);
                    }

                    System.out.println("Linha: " + pessoa.getNome() + "   Marcado: " + check.isSelected());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(check);
                }
            }
        });

        col2.setText("Nome");
        col3.setText("Email");
        col4.setText("Telefone");
        col5.setText("Data de Nascimento");
        col6.setText("");

        col2.setPrefWidth(300);
        col3.setPrefWidth(400);
        col4.setPrefWidth(300);
        col5.setPrefWidth(100);
        col6.setPrefWidth(30);

    }

    InscricaoServico inscricaoServico = new InscricaoServico();

    @FXML
    public void confirmar() {
        inscricaoServico.cadastrar(eventoAberto,pessoasSelecionadas);
        tabelaInscricaoController.atualizaTabela();
        fechar();
    }

    @FXML
    public Pane paneRaiz;

    @FXML
    public void fechar() {
        ((Pane) paneRaiz.getParent()).getChildren().clear();
    }

    @FXML
    public void cancelar() {
        fechar();
        janelaEventoController.abreInscricoes();
    }

}
