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
        if(!temSobreposicaoInserir(sessao)) {
            sessaoDAO.inserirSessao(eventoAberto,sessao);
        } else {
            Global.mostraErro("Sobreposição de horários!");
        }


    }

    public void remover(Sessao sessao, Evento eventoAberto) {
        sessaoDAO.removerSessao(eventoAberto.getId(),sessao.getId());
    }

    public void alterar(Evento eventoAberto, Sessao sessaoAntiga,String titulo, String descricao, TipoSessao tipo,LocalDate dataInicio, LocalTime horaInicio,LocalDate dataFim, LocalTime horaFim, StatusSessao status) {

        // Criar sessão temporária apenas para teste
        Sessao sessaoNova = new Sessao(eventoAberto,titulo,descricao,tipo,dataInicio,horaInicio,dataFim,horaFim,status);

        // Testar sobreposição sem modificar a sessão antiga ainda
        if (temSobreposicaoAlterar(sessaoAntiga, sessaoNova)) {
            Global.mostraErro("Sobreposição de horários!");
            return;
        }

        // SÓ AQUI altera os campos da sessão original
        sessaoAntiga.setTitulo(titulo);
        sessaoAntiga.setDescricao(descricao);
        sessaoAntiga.setTipo(tipo);
        sessaoAntiga.setDataInicio(dataInicio);
        sessaoAntiga.setHoraInicio(horaInicio);
        sessaoAntiga.setDataFim(dataFim);
        sessaoAntiga.setHoraFim(horaFim);
        sessaoAntiga.setStatus(status);

        // Agora sim: atualizar a sessão ORIGINAL no banco
        sessaoDAO.alterar(sessaoAntiga);
    }

    public boolean temSobreposicaoAlterar(Sessao sessaoAntiga, Sessao sessaoNova) {
        ArvoreSessoesTeste arvoreAux = carregaArvore();

        arvoreAux.remove(sessaoAntiga);

        try {
            arvoreAux.add(sessaoNova);
            return false; // sem sobreposição
        } catch (IllegalArgumentException e) {
            return true; // sobreposição detectada
        }
    }

    public boolean temSobreposicaoInserir(Sessao sessaoNova) {
        ArvoreSessoesTeste arvoreAux = carregaArvore();

        try {
            arvoreAux.add(sessaoNova);
            return false; // sem sobreposição
        } catch (IllegalArgumentException e) {
            return true; // sobreposição detectada
        }
    }
}
