package servico;

import dao.EventoDAO;
import dao.SessaoDAO;
import main.controller.Global;
import model.ArvoreSessoesTeste;
import model.Enum.StatusSessao;
import model.Enum.TipoSessao;
import model.Evento;
import model.Sala;
import model.Sessao;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;

public class SessaoServico {
    SessaoDAO sessaoDAO = new SessaoDAO();

    ArvoreSessoesTeste arvore = new ArvoreSessoesTeste();

    public ArvoreSessoesTeste carregaArvoreEvento(Evento eventoAberto) {
        List<Sessao> sessoes = sessaoDAO.buscarTodos(Sessao.class);

        for(Sessao s : sessoes) {

            if(s.getEvento().getId() == eventoAberto.getId()) {
                arvore.add(s);
            }
        }

        return this.arvore;
    }

    public ArvoreSessoesTeste carregaArvoreSala(Sala salaAberta) {
        List<Sessao> sessoes = sessaoDAO.buscarTodos(Sessao.class);

        for(Sessao s : sessoes) {

            try {
                if(s.getSala().getId() == salaAberta.getId()) {
                    arvore.add(s);
                }
            } catch (IllegalArgumentException e) {

            }

        }

        return this.arvore;
    }

    public void cadastrar(Sala salaSelecionada, Evento eventoAberto, String titulo, String descricao, TipoSessao tipo, LocalDate dataInicio, LocalTime horaInicio, LocalDate dataFim, LocalTime horaFim) {
        Sessao sessao = new Sessao(salaSelecionada,eventoAberto, titulo, descricao, tipo, dataInicio, horaInicio, dataFim, horaFim, StatusSessao.PENDENTE);

        if(!temSobreposicaoSalaInserir(salaSelecionada, sessao)) {

            if(!temSobreposicaoEventoInserir(eventoAberto, sessao)) {
                sessaoDAO.inserirSessao(salaSelecionada,eventoAberto,sessao);
            } else {
                Global.mostraErro("Sobreposição de horários com outros sessões do evento ou da sala!");
            }

        } else {
            Global.mostraErro("Sobreposição de horários com outros sessões do evento ou da sala!");
        }

    }

    public void remover(Sessao sessao, Evento eventoAberto) {
        sessaoDAO.removerSessao(eventoAberto.getId(),sessao.getId());
    }

    public void alterar(Sala salaSelecionada, Evento eventoAberto, Sessao sessaoAntiga,String titulo, String descricao, TipoSessao tipo,LocalDate dataInicio, LocalTime horaInicio,LocalDate dataFim, LocalTime horaFim, StatusSessao status) {

        // Criar sessão temporária apenas para teste
        Sessao sessaoNova = new Sessao(salaSelecionada,eventoAberto,titulo,descricao,tipo,dataInicio,horaInicio,dataFim,horaFim,status);

        // Testar sobreposição sem modificar a sessão antiga ainda
        if (temSobreposicaoEventoAlterar(eventoAberto, sessaoAntiga, sessaoNova)) {
            Global.mostraErro("Sobreposição com os outros horários do evento!");
            return;
        }

        if (temSobreposicaoSalaAlterar(salaSelecionada, sessaoAntiga, sessaoNova)) {
            Global.mostraErro("Sobreposição com os outros horários da sala!");
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
        sessaoAntiga.setSala(salaSelecionada);

        // Agora sim: atualizar a sessão ORIGINAL no banco
        sessaoDAO.alterar(sessaoAntiga);
    }

    public boolean temSobreposicaoEventoAlterar(Evento eventoAberto, Sessao sessaoAntiga, Sessao sessaoNova) {
        ArvoreSessoesTeste arvoreAux = carregaArvoreEvento(eventoAberto);

        arvoreAux.remove(sessaoAntiga);

        try {
            arvoreAux.add(sessaoNova);
            return false; // sem sobreposição
        } catch (IllegalArgumentException e) {
            return true; // sobreposição detectada
        }
    }

    public boolean temSobreposicaoEventoInserir(Evento eventoAberto, Sessao sessaoNova) {
        ArvoreSessoesTeste arvoreAux = carregaArvoreEvento(eventoAberto);

        try {
            arvoreAux.add(sessaoNova);
            return false; // sem sobreposição
        } catch (IllegalArgumentException e) {
            return true; // sobreposição detectada
        }
    }

    public boolean temSobreposicaoSalaAlterar(Sala salaSelecionada, Sessao sessaoAntiga, Sessao sessaoNova) {
        ArvoreSessoesTeste arvoreAux = carregaArvoreSala(salaSelecionada);

        arvoreAux.remove(sessaoAntiga);

        try {
            arvoreAux.add(sessaoNova);
            return false; // sem sobreposição
        } catch (IllegalArgumentException e) {
            return true; // sobreposição detectada
        }
    }

    public boolean temSobreposicaoSalaInserir(Sala salaSelecionada, Sessao sessaoNova) {
        ArvoreSessoesTeste arvoreAux = carregaArvoreSala(salaSelecionada);

        try {
            arvoreAux.add(sessaoNova);
            return false; // sem sobreposição
        } catch (IllegalArgumentException e) {
            return true; // sobreposição detectada
        }
    }
}
