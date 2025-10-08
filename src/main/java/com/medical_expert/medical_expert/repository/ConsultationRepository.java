package com.medical_expert.medical_expert.repository;


import com.medical_expert.medical_expert.model.Consultation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ConsultationRepository {

    public void save(Consultation consultation) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(consultation);
        em.getTransaction().commit();
        em.close();
    }

    public Consultation findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        Consultation consultation = em.find(Consultation.class, id);
        em.close();
        return consultation;
    }

    public List<Consultation> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Consultation> query = em.createQuery("SELECT c FROM Consultation c", Consultation.class);
        List<Consultation> consultations = query.getResultList();
        em.close();
        return consultations;
    }

    public void update(Consultation consultation) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(consultation);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Consultation consultation) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(consultation) ? consultation : em.merge(consultation));
        em.getTransaction().commit();
        em.close();
    }

    public List<Consultation> findByStatut(String statut) {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Consultation> query = em.createQuery(
                "SELECT c FROM Consultation c WHERE c.statut = :statut", Consultation.class);
        query.setParameter("statut", statut);
        List<Consultation> result = query.getResultList();
        em.close();
        return result;
    }
}
