package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.User;
import com.medical_expert.medical_expert.service.UserService;
import com.medical_expert.medical_expert.util.BCryptUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");

        // Vérifie si l'utilisateur existe déjà
        if (userService.findByUsername(username) != null) {
            request.setAttribute("error", "Nom d'utilisateur déjà pris.");
            request.getRequestDispatcher("/jsp/register.jsp").forward(request, response);
            return;
        }

        // Hash du mot de passe
        String hashedPassword = BCryptUtil.hashPassword(password);

        // Création de l’utilisateur
        User user = new User(username, hashedPassword, role, nom, prenom, email);

        // Enregistrement
        userService.save(user);

        request.setAttribute("message", "Inscription réussie ! Vous pouvez vous connecter.");
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }
}
