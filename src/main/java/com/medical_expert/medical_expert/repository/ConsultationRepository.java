package com.medical_expert.medical_expert.repository;

import com.medical_expert.medical_expert.model.Consultation;
import com.medical_expert.medical_expert.model.Patient;
import com.medical_expert.medical_expert.model.Generaliste;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
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

    /**
     * Vérifie si une consultation existe déjà pour le même patient, généraliste et motif à la même date.
     *
     * @param patient le patient
     * @param generaliste le généraliste
     * @param motif le motif de consultation
     * @param date la date de consultation (LocalDate)
     * @return true si une consultation existe déjà, false sinon
     */
    public boolean existsConsultation(Patient patient, Generaliste generaliste, String motif, LocalDate date) {
        EntityManager em = JpaUtil.getEntityManager();

        String jpql = "SELECT COUNT(c) FROM Consultation c " +
                "WHERE c.patient = :patient AND c.generaliste = :generaliste " +
                "AND LOWER(c.motif) = LOWER(:motif) " +
                "AND FUNCTION('DATE', c.dateConsultation) = :date";

        Long count = em.createQuery(jpql, Long.class)
                .setParameter("patient", patient)
                .setParameter("generaliste", generaliste)
                .setParameter("motif", motif)
                .setParameter("date", date)
                .getSingleResult();

        em.close();
        return count > 0;
    }
}
