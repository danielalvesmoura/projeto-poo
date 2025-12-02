package servico;

import dao.SessaoDAO;
import util.Global;
import model.ArvoreSessoes;
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

    ArvoreSessoes arvore = new ArvoreSessoes();

    public ArvoreSessoes carregaArvoreEvento(Evento eventoAberto) {
        List<Sessao> sessoes = sessaoDAO.buscarTodos(Sessao.class);
        arvore = new ArvoreSessoes();

        for(Sessao s : sessoes) {
            if(s.getEvento().getId() == eventoAberto.getId()) {
                arvore.add(s);
            }
        }
        return this.arvore;
    }

    public ArvoreSessoes carregaArvoreSala(Sala salaAberta) {
        List<Sessao> sessoes = sessaoDAO.buscarTodos(Sessao.class);
        arvore = new ArvoreSessoes();

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

        // altera os campos da sessão original
        sessaoAntiga.setTitulo(titulo);
        sessaoAntiga.setDescricao(descricao);
        sessaoAntiga.setTipo(tipo);
        sessaoAntiga.setDataInicio(dataInicio);
        sessaoAntiga.setHoraInicio(horaInicio);
        sessaoAntiga.setDataFim(dataFim);
        sessaoAntiga.setHoraFim(horaFim);
        sessaoAntiga.setStatus(status);
        sessaoAntiga.setSala(salaSelecionada);

        //  atualizar a sessão ORIGINAL no banco
        sessaoDAO.alterar(sessaoAntiga);
    }

    private boolean temSobreposicao(ArvoreSessoes arvore, Sessao sessaoAntiga, Sessao sessaoNova) {
        if (sessaoAntiga != null) {
            arvore.remove(sessaoAntiga);
        }

        try {
            arvore.add(sessaoNova);
            return false;  // sem sobreposição
        } catch (IllegalArgumentException e) {
            return true;   // sobreposição detectada
        }
    }

    public boolean temSobreposicaoEventoAlterar(Evento eventoAberto, Sessao sessaoAntiga, Sessao sessaoNova) {
        return temSobreposicao(carregaArvoreEvento(eventoAberto), sessaoAntiga, sessaoNova);
    }

    public boolean temSobreposicaoEventoInserir(Evento eventoAberto, Sessao sessaoNova) {
        return temSobreposicao(carregaArvoreEvento(eventoAberto), null, sessaoNova);
    }

    public boolean temSobreposicaoSalaAlterar(Sala salaSelecionada, Sessao sessaoAntiga, Sessao sessaoNova) {
        return temSobreposicao(carregaArvoreSala(salaSelecionada), sessaoAntiga, sessaoNova);
    }

    public boolean temSobreposicaoSalaInserir(Sala salaSelecionada, Sessao sessaoNova) {


        return temSobreposicao(carregaArvoreSala(salaSelecionada), null, sessaoNova);
    }


}
