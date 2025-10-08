package com.medical_expert.medical_expert.repository;


import com.medical_expert.medical_expert.model.Medecin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class MedecinRepository {

    public void save(Medecin medecin) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.persist(medecin);
        em.getTransaction().commit();
        em.close();
    }

    public Medecin findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        Medecin medecin = em.find(Medecin.class, id);
        em.close();
        return medecin;
    }

    public List<Medecin> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Medecin> query = em.createQuery("SELECT m FROM Medecin m", Medecin.class);
        List<Medecin> medecins = query.getResultList();
        em.close();
        return medecins;
    }

    public void update(Medecin medecin) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.merge(medecin);
        em.getTransaction().commit();
        em.close();
    }

    public void delete(Medecin medecin) {
        EntityManager em = JpaUtil.getEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(medecin) ? medecin : em.merge(medecin));
        em.getTransaction().commit();
        em.close();
    }

    public List<Medecin> findBySpecialite(String specialite) {
        EntityManager em = JpaUtil.getEntityManager();
        TypedQuery<Medecin> query = em.createQuery(
                "SELECT m FROM Medecin m WHERE m.specialite = :spec", Medecin.class);
        query.setParameter("spec", specialite);
        List<Medecin> result = query.getResultList();
        em.close();
        return result;
    }
}
