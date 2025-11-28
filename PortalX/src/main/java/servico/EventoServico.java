package servico;

import dao.EventoDAO;
import model.Evento;

import java.time.LocalDate;
import java.time.LocalTime;

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
