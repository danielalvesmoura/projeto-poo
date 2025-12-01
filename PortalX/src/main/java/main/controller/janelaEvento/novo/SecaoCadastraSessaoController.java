package main.controller.janelaEvento.novo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.controller.Global;
import main.controller.menuPrincipal.novo.CadastroPessoaController;
import main.controller.menuPrincipal.novo.JanelaTodosEventosController;
import model.Enum.StatusSessao;
import model.Enum.TipoSessao;
import model.Evento;
import model.Sala;
import model.Sessao;
import servico.EventoServico;
import servico.SessaoServico;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class SecaoCadastraSessaoController {

    public Stage stage;
    public Evento eventoAberto;
    public Sessao sessaoAberta;
    public SecaoCadastraSessaoController secaoCadastraSessaoController;

    public SecaoCadastraSessaoController(Stage stage, Evento eventoAberto, Sessao sessaoAberta) {
        this.stage = stage;
        this.eventoAberto = eventoAberto;
        this.sessaoAberta = sessaoAberta;
    }

    public SecaoCadastraSessaoController(Stage stage, Evento eventoAberto) {
        this.stage = stage;
        this.eventoAberto = eventoAberto;
    }


    EventoServico eventoServico = new EventoServico();

    @FXML
    public TextField campoTitulo;
    @FXML
    public TextArea campoDescricao;
    @FXML
    public DatePicker campoDataInicio;
    @FXML
    public TextField campoHoraInicio;
    @FXML
    public DatePicker campoDataFim;
    @FXML
    public TextField campoHoraFim;
    @FXML
    public ChoiceBox campoTipo;
    @FXML
    public ChoiceBox campoStatus;

    @FXML
    public void initialize() {
        campoTipo.getItems().addAll("Palestra","Painel", "Treinamento");
        campoTipo.setValue("Treinamento");

        campoStatus.getItems().addAll("Pendente","Confirmado", "Cancelado");
        campoStatus.setValue("Pendente");

        if(sessaoAberta != null) {
            campoTitulo.setText(sessaoAberta.getTitulo());
            campoDescricao.setText(sessaoAberta.getDescricao());
            campoDataInicio.setValue(sessaoAberta.getDataInicio());
            campoHoraInicio.setText(sessaoAberta.getHoraInicio().toString());
            campoDataFim.setValue(sessaoAberta.getDataFim());
            campoHoraFim.setText(sessaoAberta.getHoraFim().toString());
            campoTipo.setValue(sessaoAberta.getTipo());
            campoStatus.setValue(sessaoAberta.getStatus());

            salaSelecionada = sessaoAberta.getSala();
            botaoSala.setText(sessaoAberta.getSalaNome());
        } else {
            campoDataInicio.setValue(eventoAberto.getDataInicio());
            campoDataFim.setValue(eventoAberto.getDataFim());
            campoHoraInicio.setText(eventoAberto.getHoraInicio().toString());
            campoHoraFim.setText(eventoAberto.getHoraFim().toString());
        }

    }

    @FXML
    public Button botaoSala;

    public Sala salaSelecionada;

    @FXML
    public void trocarSala() throws IOException {
        FXMLLoader appLoader = new FXMLLoader(getClass().getResource("/fxml/janelaEvento/novo/modalSalasDisponiveis.fxml"));

        ModalSalasController modalSalasController = new ModalSalasController(stage);
        modalSalasController.secaoCadastraSessaoController = secaoCadastraSessaoController;

        appLoader.setController(modalSalasController);

        Parent app = appLoader.load();

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
    }

    SessaoServico sessaoServico = new SessaoServico();

    @FXML
    public void salvar() throws IOException {
        TipoSessao tipoSessao;
        if(campoTipo.getValue() == "Palestra") {
            tipoSessao = TipoSessao.PALESTRA;
        } else if(campoTipo.getValue() == "Painel") {
            tipoSessao = TipoSessao.PAINEL;
        } else {
            tipoSessao = TipoSessao.TREINAMENTO;
        }


        StatusSessao statusSessao;
        if(campoStatus.getValue() == "Pendente") {
            statusSessao = StatusSessao.PENDENTE;
        } else if(campoStatus.getValue() == "Confirmado") {
            statusSessao = StatusSessao.CONFIRMADO;
        } else {
            statusSessao = StatusSessao.CANCELADO;
        }


        if(Objects.equals(campoHoraInicio.getText(), "")) {
            campoHoraInicio.setText("00:00");
        }
        if(Objects.equals(campoHoraFim.getText(), "")) {
            campoHoraFim.setText("00:00");
        }
        if(campoDataInicio.getValue() == null) {
            campoDataInicio.setValue(eventoAberto.getDataInicio());
        }
        if(campoDataFim.getValue() == null) {
            campoDataFim.setValue(eventoAberto.getDataFim());
        }


        LocalDate dataInicio = LocalDate.parse(campoDataInicio.getValue().toString());
        LocalTime horaInicio = LocalTime.parse(campoHoraInicio.getText());

        LocalDate dataFim = LocalDate.parse(campoDataFim.getValue().toString());
        LocalTime horaFim = LocalTime.parse(campoHoraFim.getText());



        if(campoDataInicio.getValue() == null) {
            campoDataInicio.setValue(eventoAberto.getDataInicio());
        }
        if(campoDataFim.getValue() == null) {
            campoDataFim.setValue(eventoAberto.getDataFim());
        }



        if(campoDataInicio.getValue().isBefore(eventoAberto.getDataInicio())) {
            Global.mostraErro("Data de ínicio informada vem antes do início do evento.\nInício do evento: "
                    + eventoAberto.getDataInicio() + "\nInício da sessão informada: " + campoDataInicio.getValue());

        } else if(campoDataFim.getValue().isAfter(eventoAberto.getDataFim())) {
            Global.mostraErro("Data de fim informada vem depois do fim do evento.\nFim do evento: "
                    + eventoAberto.getDataFim() + "\nFim da sessão informada: " + campoDataFim.getValue());

        } else {

            if(sessaoAberta == null) {
                System.out.println("sessao nula. Cadastrar nova com sala " + salaSelecionada.getNome());
                sessaoServico.cadastrar(salaSelecionada,eventoAberto, campoTitulo.getText(), campoDescricao.getText(), tipoSessao, campoDataInicio.getValue(), horaInicio, campoDataFim.getValue(), LocalTime.parse(campoHoraFim.getText()));
            } else {
                System.out.println("sessao aberta. Alterar com sala" + salaSelecionada.getNome());
                sessaoServico.alterar(salaSelecionada,eventoAberto, sessaoAberta, campoTitulo.getText(), campoDescricao.getText(), tipoSessao, campoDataInicio.getValue(), LocalTime.parse(campoHoraInicio.getText()), campoDataFim.getValue(), LocalTime.parse(campoHoraFim.getText()), statusSessao);
            }
            janelaEditarEventoController.abreAbaSessoes();
        }

    }

    JanelaEditarEventoController janelaEditarEventoController;

}
