package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.User;
import com.medical_expert.medical_expert.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    public void save(User user) {
        // Hashage du mot de passe avant sauvegarde
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
