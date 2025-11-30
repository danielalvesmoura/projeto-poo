package servico;

import dao.EventoDAO;
import dao.SessaoDAO;
import main.controller.Global;
import model.ArvoreSessoesTeste;
import model.Enum.StatusSessao;
import model.Enum.TipoSessao;
import model.Evento;
import model.Sessao;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;

public class SessaoServico {
    SessaoDAO sessaoDAO = new SessaoDAO();
    EventoDAO eventoDAO = new EventoDAO();

    ArvoreSessoesTeste arvore = new ArvoreSessoesTeste();

    public ArvoreSessoesTeste carregaArvore() {
        List<Sessao> sessoes = sessaoDAO.buscarTodos(Sessao.class);

        for(Sessao s : sessoes) {
            arvore.add(s);
        }

        return this.arvore;
    }

    public void cadastrar(Evento eventoAberto, String titulo, String descricao, TipoSessao tipo, LocalDate dataInicio, LocalTime horaInicio, LocalDate dataFim, LocalTime horaFim) {
        Sessao sessao = new Sessao(eventoAberto, titulo, descricao, tipo, dataInicio, horaInicio, dataFim, horaFim, StatusSessao.PENDENTE);
        sessaoDAO.inserirSessao(eventoAberto,sessao);
    }

    public void remover(Sessao sessao, Evento eventoAberto) {
        sessaoDAO.removerSessao(eventoAberto.getId(),sessao.getId());
    }

    public void alterar(Evento eventoAberto, Sessao sessao, String titulo, String descricao, TipoSessao tipo, LocalDate dataInicio, LocalTime horaInicio, LocalDate dataFim, LocalTime horaFim, StatusSessao status) {

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
