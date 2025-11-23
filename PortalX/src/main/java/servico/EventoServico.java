package servico;

import dao.EventoDAO;
import model.Evento;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventoServico {
    EventoDAO eventoDAO = new EventoDAO();

    public void cadastrar(String nome, String descricao, String endereco, LocalDate dataInicio, LocalDate dataFim) {
        Evento evento = new Evento(nome, descricao, endereco, dataInicio, dataFim);
        eventoDAO.inserir(evento);
    }

    public void remover(Evento evento) {
        eventoDAO.remover(evento);
    }
}
