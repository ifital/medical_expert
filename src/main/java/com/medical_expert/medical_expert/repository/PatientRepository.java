package com.medical_expert.medical_expert.repository;


import com.medical_expert.medical_expert.model.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class PatientRepository {

    public void save(Patient patient) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(patient);
        em.getTransaction().commit();
        em.close();
    }

    public Patient findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        Patient patient = em.find(Patient.class, id);
        em.close();
        return patient;
    }

    public List<Patient> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Patient> query = em.createQuery("SELECT p FROM Patient p", Patient.class);
        List<Patient> patients = query.getResultList();
        em.close();
        return patients;
    }

    public void update(Patient patient) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(patient);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Patient patient) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(patient) ? patient : em.merge(patient));
        em.getTransaction().commit();
        em.close();
    }
}
