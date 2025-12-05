package servico;

import dao.PessoaDAO;
import model.Pessoa;

import java.time.LocalDate;


public class PessoaServico {
    PessoaDAO pessoaDAO = new PessoaDAO();

    public void remover(Pessoa pessoa) {

        pessoaDAO.remover(pessoa);
    }

    public void alterar(Pessoa pessoa, String nome, String email, String telefone, LocalDate dataNascimento) {

        pessoa.setNome(nome);
        pessoa.setEmail(email);
        pessoa.setTelefone(telefone);
        pessoa.setDataNascimento(dataNascimento);

        pessoaDAO.alterar(pessoa);
    }

    public void cadastrar(String nome, String email, String telefone, LocalDate dataNascimento) {
        pessoaDAO.inserir(new Pessoa(nome,email,telefone,dataNascimento));
    }
}
