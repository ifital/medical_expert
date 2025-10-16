package com.medical_expert.medical_expert.repository;

import com.medical_expert.medical_expert.model.Creneau;
import com.medical_expert.medical_expert.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class CreneauRepository {

    // Sauvegarder un nouveau créneau
    public void save(Creneau creneau) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(creneau);
        em.getTransaction().commit();
        em.close();
    }

    // Mettre à jour un créneau existant
    public void update(Creneau creneau) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(creneau);
        em.getTransaction().commit();
        em.close();
    }

    // Supprimer un créneau
    public void delete(Creneau creneau) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(creneau) ? creneau : em.merge(creneau));
        em.getTransaction().commit();
        em.close();
    }

    // Chercher un créneau par son ID
    public Creneau findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        Creneau creneau = em.find(Creneau.class, id);
        em.close();
        return creneau;
    }

    // Lister tous les créneaux
    public List<Creneau> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Creneau> query = em.createQuery("SELECT c FROM Creneau c", Creneau.class);
        List<Creneau> result = query.getResultList();
        em.close();
        return result;
    }

    // Lister les créneaux disponibles pour un spécialiste donné
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
