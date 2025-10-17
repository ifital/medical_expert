package com.medical_expert.medical_expert.repository;

import com.medical_expert.medical_expert.model.Creneau;
import com.medical_expert.medical_expert.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CreneauRepository {

    public void save(Creneau creneau) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(creneau);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Creneau creneau) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(creneau);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Creneau creneau) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(creneau) ? creneau : em.merge(creneau));
        em.getTransaction().commit();
        em.close();
    }

    public Creneau findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        Creneau creneau = em.find(Creneau.class, id);
        em.close();
        return creneau;
    }

    public List<Creneau> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Creneau> query = em.createQuery("SELECT c FROM Creneau c", Creneau.class);
        List<Creneau> result = query.getResultList();
        em.close();
        return result;
    }

    public List<Creneau> findAvailableBySpecialiste(Long specialisteId) {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Creneau> query = em.createQuery(
                "SELECT c FROM Creneau c WHERE c.specialiste.id = :id AND c.disponible = true",
                Creneau.class);
        query.setParameter("id", specialisteId);
        List<Creneau> result = query.getResultList();
        em.close();
        return result;
    }
}
