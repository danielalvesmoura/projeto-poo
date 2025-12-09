package servico;

import dao.InscricaoDAO;
import dao.PessoaDAO;
import dao.SalaDAO;
import model.*;
import model.Enum.StatusInscricao;
import model.Enum.TipoIngresso;
import util.Global;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class InscricaoServico {

    InscricaoDAO inscricaoDAO = new InscricaoDAO();

    public void remover(Inscricao inscricao) {
        //Inscricao i = inscricaoDAO.find(Inscricao.class,inscricao.getId());
        inscricaoDAO.remover(inscricao);

        System.out.println("inscricao removida");
    }

    public void alterar(Inscricao inscricao, StatusInscricao statusInscricao, TipoIngresso tipoIngresso) {
        inscricao.setStatus(statusInscricao);
        inscricao.setTipoIngresso(tipoIngresso);

        inscricaoDAO.alterar(inscricao);
    }

    PessoaDAO pessoaDAO = new PessoaDAO();

    public void cadastrar(Evento evento, List<Pessoa> pessoasSelecionadas, String tipoIngresso) {

        TipoIngresso tipoIngressoEnum;

        if(tipoIngresso == "Participante") {
            tipoIngressoEnum = TipoIngresso.PARTICIPANTE;
        } else {
            tipoIngressoEnum = TipoIngresso.PALESTRANTE;
        }


        int vagasDisponiveis = vagasDisponiveis(evento);

        //System.out.println("Vagas disponiveis antes do inscrição: " + vagasDisponiveis);

        for(Pessoa pessoa : pessoasSelecionadas) {

            if(vagasDisponiveis == 0) {
                Global.mostraErro("Salas cheias. Inscreva mais salas.");
                return;
            }

            Pessoa pessoaPesquisada = pessoaDAO.find(Pessoa.class,pessoa.getId());

            Inscricao inscricao = new Inscricao(pessoaPesquisada, evento, StatusInscricao.PENDENTE, tipoIngressoEnum);

            inscricaoDAO.inserir(inscricao);

            vagasDisponiveis--;

            //evento.getInscricoes().adicionar(inscricao.getId());
        }

        //System.out.println("Vagas disponiveis depois da inscrição: " + vagasDisponiveis);

    }

    public int vagasDisponiveis(Evento evento) {
        int vagasTotais = vagasTotais(evento);
        //System.out.println("\nvagas totais: " + vagasTotais);

        int vagasPreenchidas = vagasPreenchidas(evento);
        //System.out.println("vagas preenchidas: " + vagasPreenchidas);

        return vagasTotais - vagasPreenchidas;
    }

    public int vagasTotais(Evento evento) {

        //System.out.println("ID do evento: " + evento.getId());

        List<Sessao> sessoes = evento.getSessoes();
        int vagasTotais = 0;
        for(Sessao s : sessoes) {
            vagasTotais += s.getSala().getCapacidade();
            //System.out.println("Sala encontrada: " + s.getSala().getNome());
        }

        return vagasTotais;
    }

    public int vagasPreenchidas(Evento evento) {
        int vagasPreenchidas = 0;
        List<Inscricao> inscricoes = inscricaoDAO.buscarTodos(Inscricao.class);
        for(Inscricao i : inscricoes) {
            if(i.getEventoId() == evento.getId()) {
                vagasPreenchidas++;
            }
        }

        return vagasPreenchidas;
    }

}
