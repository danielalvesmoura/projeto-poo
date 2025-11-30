package servico;

import dao.InscricaoDAO;
import dao.PessoaDAO;
import model.*;
import model.Enum.StatusInscricao;
import model.Enum.TipoIngresso;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class InscricaoServico {

    InscricaoDAO inscricaoDAO = new InscricaoDAO();

    public void remover(Inscricao inscricao) {
        inscricaoDAO.remover(inscricao);
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

        for(Pessoa pessoa : pessoasSelecionadas) {
            /*
            if(pessoa instanceof Palestrante) {
                tipoIngresso = TipoIngresso.PALESTRANTE;
            } else {
                tipoIngresso = TipoIngresso.PARTICIPANTE;
            }

             */

            Pessoa pessoaPesquisada = pessoaDAO.find(Pessoa.class,pessoa.getId());

            inscricaoDAO.inserir(new Inscricao(pessoaPesquisada, evento, StatusInscricao.PENDENTE, tipoIngressoEnum));
        }

    }
}
