package servico;

import dao.EventoDAO;
import dao.InscricaoDAO;
import javafx.collections.transformation.SortedList;
import model.Evento;
import model.Inscricao;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventoServico {
    EventoDAO eventoDAO = new EventoDAO();

    public void cadastrar(String nome, String descricao, String endereco, LocalDate dataInicio, String horaInicio, LocalDate dataFim, String horaFim, int capacidade) {
        Evento evento = new Evento(nome, descricao, endereco, dataInicio, horaInicio, dataFim, horaFim, capacidade);
        eventoDAO.inserir(evento);
    }

    public void remover(Evento evento) {
        eventoDAO.remover(evento);
    }

    public void alterar(Evento evento, String nome, String descricao, String endereco, LocalDate dataInicio, LocalTime horaInicio, LocalDate dataFim, LocalTime horaFim, int capacidade) {

        evento.setNome(nome);
        evento.setDescricao(descricao);
        evento.setEndereco(endereco);
        evento.setDataInicio(dataInicio);
        evento.setHoraInicio(horaInicio);
        evento.setDataFim(dataFim);
        evento.setHoraFim(horaFim);
        evento.setCapacidade(capacidade);

        eventoDAO.alterar(evento);
    }


}
