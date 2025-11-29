package dao;

import model.Evento;
import model.Participante;
import model.Sessao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class SessaoDAO extends DAOImplementacao<Sessao, Long> {


    public void removerSessao(long eventoId, long sessaoId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Evento ev = em.find(Evento.class, eventoId);
        Sessao se = em.find(Sessao.class, sessaoId);

        ev.getSessoes().remove(se);
        se.setEvento(null);

        em.getTransaction().commit();
        em.close();
    }
}