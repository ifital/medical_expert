package com.medical_expert.medical_expert.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Utilitaire JPA pour gérer l'EntityManagerFactory et fournir des EntityManager.
 */
public class JpaUtil {
    private static final String PERSISTENCE_UNIT_NAME = "teleexpertisePU";
    private static EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError("Erreur d'initialisation de l'EntityManagerFactory : " + e.getMessage());
        }
    }

    private JpaUtil() {
        // Empêche l'instanciation
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
