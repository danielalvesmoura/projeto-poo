package main.controller.janelaEvento.novo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.controller.menuPrincipal.novo.JanelaTodosEventosController;
import model.Evento;
import servico.EventoServico;

import java.time.LocalTime;
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
        if(eventoAberto == null) {
            if(Objects.equals(campoCapacidade.getText(), "")) campoCapacidade.setText("1");
            if(Objects.equals(campoHoraInicio.getText(), "")) campoHoraInicio.setText("00:00");
            if(Objects.equals(campoHoraFim.getText(), "")) campoHoraFim.setText("00:00");

            eventoServico.cadastrar(campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(), campoHoraInicio.getText(),campoDataFim.getValue(), campoHoraFim.getText(), Integer.parseInt(campoCapacidade.getText()));

            try {
                FXMLLoader janelaTodosEventosLoader = new FXMLLoader(getClass().getResource("/fxml/menuPrincipal/novo/janelaTodosEventos.fxml"));

                JanelaTodosEventosController janelaTodosEventosController = new JanelaTodosEventosController(stage);
                janelaTodosEventosLoader.setController(janelaTodosEventosController);

                Parent janela = janelaTodosEventosLoader.load();

                Scene cenaTodosEventos = new Scene(janela);

                stage.setScene(cenaTodosEventos);



            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            eventoServico.alterar(eventoAberto,campoNome.getText(), campoDescricao.getText(), campoEndereco.getText(), campoDataInicio.getValue(), LocalTime.parse(campoHoraInicio.getText()), campoDataFim.getValue(), LocalTime.parse(campoHoraFim.getText()), Integer.parseInt(campoCapacidade.getText()));
        }
    }

}
