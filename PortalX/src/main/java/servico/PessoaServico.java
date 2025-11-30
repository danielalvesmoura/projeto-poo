package servico;

import dao.PessoaDAO;
import model.Pessoa;

import java.time.LocalDate;


public class PessoaServico {
    PessoaDAO pessoaDAO = new PessoaDAO();

    public void remover(Pessoa pessoa) {
        /*
        if (pessoa instanceof Palestrante) {
            Palestrante p = (Palestrante) pessoa;
            palestranteDAO.remover(p);
        } else {
            Participante p = (Participante) pessoa;
            participanteDAO.remover(p);
        }

         */
        pessoaDAO.remover(pessoa);
    }

    public void alterar(Pessoa pessoa, String nome, String email, String telefone, LocalDate dataNascimento) {

        pessoa.setNome(nome);
        pessoa.setEmail(email);
        pessoa.setTelefone(telefone);
        pessoa.setDataNascimento(dataNascimento);

        /*
        if (pessoa instanceof Palestrante) {
            Palestrante p = (Palestrante) pessoa;
            palestranteDAO.alterar(p);
        } else {
            Participante p = (Participante) pessoa;
            participanteDAO.alterar(p);
        }

         */
    }

    public void cadastrar(String nome, String email, String telefone, LocalDate dataNascimento) {
        pessoaDAO.inserir(new Pessoa(nome,email,telefone,dataNascimento));
    }
}
