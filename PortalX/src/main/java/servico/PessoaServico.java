package servico;

import dao.PalestranteDAO;
import dao.ParticipanteDAO;
import dao.PessoaDAO;
import model.Palestrante;
import model.Participante;
import model.Pessoa;

import java.time.LocalDate;


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

    public void alterar(Pessoa pessoa, String nome, String email, String telefone, LocalDate dataNascimento) {

        pessoa.setNomeCompleto(nome);
        pessoa.setEmail(email);
        pessoa.setTelefone(telefone);
        pessoa.setDataNascimento(dataNascimento);

        if (pessoa instanceof Palestrante) {
            Palestrante p = (Palestrante) pessoa;
            palestranteDAO.alterar(p);
        } else {
            Participante p = (Participante) pessoa;
            participanteDAO.alterar(p);
        }
    }

    //public void cadastrar(String nome, String email, String telefone, LocalDate dataNascimento) {
    //    Participante participante = new Participante(nome, email, telefone, dataNascimento);
    //    participanteDao.inserir(participante);
    //}
}
