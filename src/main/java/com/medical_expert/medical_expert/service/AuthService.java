package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.User;
import com.medical_expert.medical_expert.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class AuthService {

    public User login(String email, String password) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // On récupère la liste des utilisateurs correspondant à l'email
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            List<User> users = query.getResultList();

            if (users.isEmpty()) {
                return null; // aucun utilisateur trouvé
            }

            // Vérification du mot de passe pour le premier utilisateur trouvé
            User user = users.get(0);
            if (user.getPassword().equals(password)) {
                return user;
            }

            return null; // mot de passe incorrect
        } finally {
            em.close();
        }
    }
}
