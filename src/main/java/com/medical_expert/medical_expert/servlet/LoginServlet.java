package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.User;
import com.medical_expert.medical_expert.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Si l'utilisateur est déjà connecté, rediriger vers son dashboard
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            redirectByRole(user, req, resp);
            return;
        }

        // Sinon afficher la page de connexion
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Vérification des identifiants
        User user = authService.login(email, password);

        if (user != null) {
            // Création d'une session utilisateur
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            // Redirection selon rôle
            redirectByRole(user, req, resp);

        } else {
            // Identifiants invalides
            req.setAttribute("error", "Identifiants invalides. Veuillez réessayer.");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
    }

    /**
     * Redirige l'utilisateur selon son rôle vers le bon dashboard.
     */
    private void redirectByRole(User user, HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String role = user.getRole() == null ? "" : user.getRole().toUpperCase();

        switch (role) {
            case "INFIRMIER":
                resp.sendRedirect(req.getContextPath() + "/dashboard/infirmier");
                break;
            case "GENERALISTE":
                resp.sendRedirect(req.getContextPath() + "/dashboard/generaliste");
                break;
            case "SPECIALISTE":
                resp.sendRedirect(req.getContextPath() + "/dashboard/specialiste");
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/jsp/index.jsp");
        }
    }
}
