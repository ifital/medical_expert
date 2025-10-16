package com.medical_expert.medical_expert.repository;

import com.medical_expert.medical_expert.model.Specialiste;
import com.medical_expert.medical_expert.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class SpecialisteRepository {

    // Sauvegarder un nouveau spécialiste
    public void save(Specialiste specialiste) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(specialiste);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Mettre à jour un spécialiste existant
    public void update(Specialiste specialiste) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(specialiste);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Supprimer un spécialiste
    public void delete(Specialiste specialiste) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Specialiste s = em.find(Specialiste.class, specialiste.getId());
            if (s != null) {
                em.remove(s);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Trouver un spécialiste par ID
    public Specialiste findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Specialiste.class, id);
        } finally {
            em.close();
        }
    }

    // Récupérer tous les spécialistes
    public List<Specialiste> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Specialiste> query = em.createQuery("SELECT s FROM Specialiste s", Specialiste.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Trouver un spécialiste par username (facultatif)
    public Specialiste findByUsername(String username) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<Specialiste> query = em.createQuery(
                    "SELECT s FROM Specialiste s WHERE s.username = :username", Specialiste.class);
            query.setParameter("username", username);
            try {
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        } finally {
            em.close();
        }
    }
}
