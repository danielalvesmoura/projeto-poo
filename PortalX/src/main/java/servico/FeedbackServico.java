package servico;

import dao.EventoDAO;
import dao.FeedbackDAO;
import model.Evento;
import model.Feedback;

import java.time.LocalDate;

public class FeedbackServico {
    FeedbackDAO feedbackDAO = new FeedbackDAO();

    public void cadastrar(int nota, String comentario, LocalDate dataEnvio) {
        Feedback feedback = new Feedback(nota, comentario, dataEnvio);
        feedbackDAO.inserir(feedback);
    }
}
