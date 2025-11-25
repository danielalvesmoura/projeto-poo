package dao;

import model.Evento;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract  class DAOImplementacao<T, ID> implements DAOGenerico<T, ID> {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistenciaPU");

    @Override
    public T inserir(T objeto) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.persist(objeto);
        em.getTransaction().commit();
        em.close();

        return objeto;
    }

    @Override
    public T remover(T objeto) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        T entidade = (T) em.merge(objeto);
        em.remove(entidade);
        em.getTransaction().commit();
        em.close();

        return objeto;
    }

    @Override
    public T alterar(T objeto) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.merge(objeto);
        em.getTransaction().commit();
        em.close();

        return objeto;
    }

    @Override
    public T buscarPorId(Class<T> entityClass,ID T) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e where id = " + T;
        return em.createQuery(jpql, entityClass).getSingleResult();
    }



    @Override
    public List<T> buscarTodos(Class<T> entityClass) {
        EntityManager em = emf.createEntityManager();
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return em.createQuery(jpql, entityClass).getResultList();
    }
}
