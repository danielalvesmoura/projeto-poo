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

    public void alterar(Evento evento, String nome, String descricao, String endereco, LocalDate dataInicio, LocalDate dataFim) {
        evento.setNome(nome);
        evento.setDescricao(descricao);
        evento.setEndereco(endereco);
        evento.setDataInicio(dataInicio);
        evento.setDataFim(dataFim);

        eventoDAO.alterar(evento);
    }
}
