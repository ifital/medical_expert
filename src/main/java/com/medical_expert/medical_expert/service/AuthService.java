package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.User;
import com.medical_expert.medical_expert.repository.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class AuthService {

    public User login(String username, String password) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                    "SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            User user = query.getSingleResult();

            if (user != null && user.getPassword().equals(password)) { // bcrypt à intégrer
                return user;
            }
            return null;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }
}
