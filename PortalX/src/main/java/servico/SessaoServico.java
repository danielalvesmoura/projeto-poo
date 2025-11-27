package servico;

import dao.EventoDAO;
import dao.SessaoDAO;
import model.Enum.StatusSessao;
import model.Enum.TipoSessao;
import model.Evento;
import model.Sessao;

import java.time.LocalDate;
import java.time.LocalTime;

public class SessaoServico {
    SessaoDAO sessaoDAO = new SessaoDAO();

    public void cadastrar(Evento evento, String titulo, String descricao, TipoSessao tipo, LocalDate dataInicio, LocalTime horaInicio, LocalDate dataFim, LocalTime horaFim) {
        Sessao sessao = new Sessao(evento, titulo, descricao, tipo, dataInicio, horaInicio, dataFim, horaFim, StatusSessao.PENDENTE);
        sessaoDAO.inserir(sessao);
    }

    public void remover(Sessao sessao) {
        sessaoDAO.remover(sessao);
    }

    public void alterar(Sessao sessao, String titulo, String descricao, TipoSessao tipo, LocalDate dataInicio, LocalTime horaInicio, LocalDate dataFim, LocalTime horaFim, StatusSessao status) {

        sessao.setTitulo(titulo);
        sessao.setDescricao(descricao);
        sessao.setTipo(tipo);
        sessao.setDataInicio(dataInicio);
        sessao.setHoraInicio(horaInicio);
        sessao.setDataFim(dataFim);
        sessao.setHoraFim(horaFim);
        sessao.setStatus(status);

        sessaoDAO.alterar(sessao);
    }
}
