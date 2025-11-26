package servico;

import dao.ParticipanteDAO;
import model.Participante;

import java.time.LocalDate;

public class ParticipanteServico {
    ParticipanteDAO participanteDao = new ParticipanteDAO();

    public void cadastrar(String nome, String email, String telefone, LocalDate dataNascimento) {
        Participante participante = new Participante(nome, email, telefone, dataNascimento);
        participanteDao.inserir(participante);
        System.out.println("participante");
        System.out.println(nome);
        System.out.println(email);
        System.out.println(telefone);
        System.out.println(dataNascimento);
    }

    public void remover(Participante participante) {
        participanteDao.remover(participante);
    }

    public void alterar(Participante participante, String nome, String email, String telefone, LocalDate dataNascimento) {

        participante.setNome(nome);
        participante.setEmail(email);
        participante.setTelefone(telefone);
        participante.setDataNascimento(dataNascimento);

        participanteDao.alterar(participante);
    }
}
