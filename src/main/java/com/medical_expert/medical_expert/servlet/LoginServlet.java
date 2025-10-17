package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Specialiste;
import com.medical_expert.medical_expert.model.User;
import com.medical_expert.medical_expert.service.AuthService;
import com.medical_expert.medical_expert.service.SpecialisteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();
    private final SpecialisteService specialisteService = new SpecialisteService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            redirectByRole(user, req, resp);
            return;
        }

        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = authService.login(email, password);

        if (user != null) {
            HttpSession session = req.getSession(true);

            if ("SPECIALISTE".equalsIgnoreCase(user.getRole())) {
                Specialiste specialiste = specialisteService.getByUsername(user.getUsername());
                if (specialiste == null) {
                    specialiste = new Specialiste(
                            user.getUsername(),
                            user.getPassword(),
                            user.getRole(),
                            user.getNom(),
                            user.getPrenom(),
                            user.getEmail(),
                            0.0, "" // tarif par défaut et spécialité vide
                    );
                    specialisteService.createSpecialiste(specialiste);
                }
                session.setAttribute("user", specialiste);
            } else {
                session.setAttribute("user", user);
            }

            redirectByRole(user, req, resp);

        } else {
            req.setAttribute("error", "Identifiants invalides. Veuillez réessayer.");
            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
        }
    }

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
                resp.sendRedirect(req.getContextPath() + "/expertise/request");
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/jsp/index.jsp");
        }
    }
}
