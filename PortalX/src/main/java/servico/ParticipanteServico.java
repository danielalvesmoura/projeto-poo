package servico;

import dao.ParticipanteDAO;
import model.Participante;

import java.time.LocalDate;

public class ParticipanteServico {
    ParticipanteDAO participanteDao = new ParticipanteDAO();

    public void cadastrar(String nome, String email, String telefone, LocalDate dataNascimento) {
        Participante participante = new Participante(nome, email, telefone, dataNascimento);
        participanteDao.inserir(participante);
    }

    public void remover(Participante participante) {
        participanteDao.remover(participante);
    }

    public void alterar(Participante participante, String nome, String email, String telefone, LocalDate dataNascimento) {

        participante.setNomeCompleto(nome);
        participante.setEmail(email);
        participante.setTelefone(telefone);
        participante.setDataNascimento(dataNascimento);

        participanteDao.alterar(participante);
    }
}
