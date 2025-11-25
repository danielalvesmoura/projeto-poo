package servico;

import dao.PalestranteDAO;
import dao.ParticipanteDAO;
import dao.PessoaDAO;
import model.Palestrante;
import model.Participante;
import model.Pessoa;


public class PessoaServico {
    PessoaDAO pessoaDAO = new PessoaDAO();
    PalestranteDAO palestranteDAO = new PalestranteDAO();
    ParticipanteDAO participanteDAO = new ParticipanteDAO();

    public void remover(Pessoa pessoa) {
        if (pessoa instanceof Palestrante) {
            Palestrante p = (Palestrante) pessoa;
            palestranteDAO.remover(p);
        } else {
            Participante p = (Participante) pessoa;
            participanteDAO.remover(p);
        }
    }
}
