package com.medical_expert.medical_expert.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe utilitaire pour le hachage et la vérification des mots de passe avec BCrypt.
 */
public class BCryptUtil {

    private BCryptUtil() {
        // Classe utilitaire → pas d'instance
    }

    /**
     * Génère un hash sécurisé pour un mot de passe donné.
     *
     * @param plainPassword mot de passe en clair
     * @return hash BCrypt
     */
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    /**
     * Vérifie si un mot de passe correspond à un hash stocké.
     *
     * @param plainPassword mot de passe fourni
     * @param hashedPassword hash en base de données
     * @return true si correspond, sinon false
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
