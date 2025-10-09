package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.User;
import com.medical_expert.medical_expert.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = authService.login(username, password);
        if (user != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("user", user);

            // redirection selon rôle
            String role = user.getRole() == null ? "" : user.getRole().toUpperCase();
            switch (role) {
                case "INFIRMIER":
                    resp.sendRedirect(req.getContextPath() + "/jsp/dashboard_infirmier.jsp");
                    break;
                case "GENERALISTE":
                    resp.sendRedirect(req.getContextPath() + "/jsp/dashboard_generaliste.jsp");
                    break;
                case "SPECIALISTE":
                    resp.sendRedirect(req.getContextPath() + "/jsp/dashboard_specialiste.jsp");
                    break;
                default:
                    resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
            }
        } else {
            req.setAttribute("error", "Identifiants invalides");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
    }
}
