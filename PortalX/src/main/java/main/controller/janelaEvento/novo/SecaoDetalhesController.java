package main.controller.janelaEvento.novo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.controller.Global;
import main.controller.menuPrincipal.novo.JanelaTodosEventosController;
import model.Evento;
import servico.EventoServico;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class SecaoDetalhesController {

    public Stage stage;
    public Evento eventoAberto;

    public SecaoDetalhesController(Stage stage, Evento eventoAberto) {
        this.stage = stage;
        this.eventoAberto = eventoAberto;
    }

    EventoServico eventoServico = new EventoServico();

    @FXML
    public TextField campoNome;
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
    public TextField campoEndereco;
    @FXML
    public TextField campoCapacidade;

    @FXML
    public void initialize() {
        if(eventoAberto != null) {
            campoNome.setText(eventoAberto.getNome());
            campoDescricao.setText(eventoAberto.getDescricao());
            campoDataInicio.setValue(eventoAberto.getDataInicio());
            campoHoraInicio.setText(eventoAberto.getHoraInicio().toString());
            campoDataFim.setValue(eventoAberto.getDataFim());
            campoHoraFim.setText(eventoAberto.getHoraFim().toString());
            campoEndereco.setText(eventoAberto.getEndereco());
            campoCapacidade.setText(eventoAberto.getCapacidade());
        }

    }


    @FXML
    public void salvar() {
        if(campoNome.getText().isEmpty()) {
            Global.mostraErro("O nome é obrigatório.");
            return;
        } else if(campoDescricao.getText().isEmpty()) {
            Global.mostraErro("A descrição é obrigatória.");
            return;
        } else if(campoEndereco.getText().isEmpty()) {
            Global.mostraErro("O endereço é obrigatório.");
            return;
        } else {

            try {
                LocalDate teste = campoDataInicio.getValue();
            } catch (Exception e) {
                Global.mostraErro("Data de início inválida.");
                return;
            }
            try {
                LocalDate teste = campoDataFim.getValue();
            } catch (Exception e) {
                Global.mostraErro("Data de fim inválida.");
                return;
            }
            try {
                LocalTime.parse(campoHoraInicio.getText());
            } catch (Exception e) {
                Global.mostraErro("Hora de início inválida.");
                return;
            }
            try {
                LocalTime.parse(campoHoraFim.getText());
            } catch (Exception e) {
                Global.mostraErro("Hora de fim inválida.");
                return;
            }

            LocalDate dataInicio = campoDataInicio.getValue();
            LocalDate dataFim = campoDataFim.getValue();

            LocalTime horaInicio = LocalTime.parse(campoHoraInicio.getText());
            LocalTime horaFim = LocalTime.parse(campoHoraInicio.getText());

            try {
                if (LocalDateTime.of(dataInicio, horaInicio).isAfter(LocalDateTime.of(dataFim, horaFim))) {
                    Global.mostraErro("Data de início não pode vir depois do fim");
                    return;
                }
            } catch (Exception e) {
                Global.mostraErro("Data inválida.");
                return;
            }

            try {
                int teste = Integer.parseInt(campoCapacidade.getText());
            } catch (Exception e) {
                Global.mostraErro("Capacidade deve ser um número.");
                return;
            }


        }

        if(eventoAberto == null) {

            eventoServico.cadastrar(campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(), campoHoraInicio.getText(),campoDataFim.getValue(), campoHoraFim.getText(), Integer.parseInt(campoCapacidade.getText()));

            try {
                FXMLLoader janelaTodosEventosLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/novo/janelaTodosEventos.fxml"));

                JanelaTodosEventosController janelaTodosEventosController = new JanelaTodosEventosController(stage);
                janelaTodosEventosLoader.setController(janelaTodosEventosController);

                Parent janela = janelaTodosEventosLoader.load();

                Scene cenaTodosEventos = new Scene(janela);

                stage.setScene(cenaTodosEventos);
                stage.setFullScreen(true);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            eventoServico.alterar(eventoAberto,campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(), LocalTime.parse(campoHoraInicio.getText()), campoDataFim.getValue(), LocalTime.parse(campoHoraFim.getText()), Integer.parseInt(campoCapacidade.getText()));
        }
    }

}
