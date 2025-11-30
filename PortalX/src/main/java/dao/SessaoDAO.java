package dao;

import model.Evento;
import model.Sessao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class SessaoDAO extends DAOImplementacao<Sessao, Long> {


    public void removerSessao(long eventoId, long sessaoId) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Evento evento = em.find(Evento.class, eventoId);
        Sessao sessao = em.find(Sessao.class, sessaoId);

        if (sessao != null && evento != null) {
            evento.getSessoes().remove(sessao);
            sessao.setEvento(null);
            em.remove(sessao);
        } else {
            System.out.println("Evento ou sessao nao encontrado");
        }

        em.getTransaction().commit();
        em.close();
    }

    public void inserirSessao(Evento evento, Sessao sessao) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        evento = em.merge(evento); // Reanexa o evento à sessão
        evento.getSessoes().add(sessao);

        em.persist(sessao);
        em.getTransaction().commit();
        em.close();
    }
}