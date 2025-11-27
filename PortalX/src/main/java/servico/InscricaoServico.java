package servico;

import dao.InscricaoDAO;
import dao.PalestranteDAO;
import dao.ParticipanteDAO;
import dao.PessoaDAO;
import model.*;
import model.Enum.StatusInscricao;
import model.Enum.TipoIngresso;

import java.time.LocalDate;


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

    public void cadastrar(Pessoa pessoa, Evento evento, StatusInscricao statusInscricao, TipoIngresso tipoIngresso) {
        Inscricao inscricao = new Inscricao(pessoa, evento, statusInscricao, tipoIngresso);
        inscricaoDAO.inserir(inscricao);
    }
}
