package com.medical_expert.medical_expert.repository;


import com.medical_expert.medical_expert.model.DemandeExpertise;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class DemandeExpertiseRepository {

    public void save(DemandeExpertise demande) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(demande);
        em.getTransaction().commit();
        em.close();
    }

    public DemandeExpertise findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        DemandeExpertise demande = em.find(DemandeExpertise.class, id);
        em.close();
        return demande;
    }

    public List<DemandeExpertise> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<DemandeExpertise> query =
                em.createQuery("SELECT d FROM DemandeExpertise d", DemandeExpertise.class);
        List<DemandeExpertise> result = query.getResultList();
        em.close();
        return result;
    }

    public void update(DemandeExpertise demande) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(demande);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(DemandeExpertise demande) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(demande) ? demande : em.merge(demande));
        em.getTransaction().commit();
        em.close();
    }

    public List<DemandeExpertise> findByStatut(String statut) {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<DemandeExpertise> query = em.createQuery(
                "SELECT d FROM DemandeExpertise d WHERE d.statut = :statut", DemandeExpertise.class);
        query.setParameter("statut", statut);
        List<DemandeExpertise> result = query.getResultList();
        em.close();
        return result;
    }
}
