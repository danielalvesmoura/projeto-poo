package servico;

import dao.PalestranteDAO;
import model.Evento;
import model.Palestrante;

import java.time.LocalDate;

public class PalestranteServico {
    PalestranteDAO palestranteDAO = new PalestranteDAO();

    public void cadastrar(String nome, String email, String telefone, LocalDate dataNascimento) {
        Palestrante palestrante = new Palestrante(nome, email, telefone, dataNascimento);
        palestranteDAO.inserir(palestrante);
    }

    public void remover(Palestrante palestrante) {
        palestranteDAO.remover(palestrante);
    }

    public void alterar(Palestrante palestrante, String nome, String email, String telefone, LocalDate dataNascimento) {

        palestrante.setNomeCompleto(nome);
        palestrante.setEmail(email);
        palestrante.setTelefone(telefone);
        palestrante.setDataNascimento(dataNascimento);

        palestranteDAO.alterar(palestrante);
    }
}
