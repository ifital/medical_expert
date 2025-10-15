package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.User;
import com.medical_expert.medical_expert.repository.UserRepository;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public void save(User user) {
        // Sauvegarde du mot de passe en clair
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
