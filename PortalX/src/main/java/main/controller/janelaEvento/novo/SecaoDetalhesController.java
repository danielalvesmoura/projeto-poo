package main.controller.janelaEvento.novo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.controller.GlobalController;
import util.Global;
import main.controller.menuPrincipal.novo.JanelaTodosEventosController;
import model.Evento;
import servico.EventoServico;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class SecaoDetalhesController extends GlobalController {

    @Override
    protected void colocarT(Object objeto, Object controller) {}
    @Override
    protected void colocarA(Object objetoA, Object controller) {}
    @Override
    protected void defineBorderPane(Object controller) {};


    @Override
    public void setConteudo(BorderPane conteudo) {
        super.setConteudo(conteudo);
    }
    @Override
    public void setBorderpaneMenor(BorderPane borderpaneMenor) {
        super.setBorderpaneMenor(borderpaneMenor);
    }

    public Evento eventoAberto;

    public void posCarregamento() {

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
                trocaTela("/fxml/menuPrincipal/novo/janelaTodosEventos.fxml");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            eventoServico.alterar(eventoAberto,campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(), LocalTime.parse(campoHoraInicio.getText()), campoDataFim.getValue(), LocalTime.parse(campoHoraFim.getText()), Integer.parseInt(campoCapacidade.getText()));
        }
    }


}
