package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Specialiste;
import com.medical_expert.medical_expert.model.Creneau;
import com.medical_expert.medical_expert.service.CreneauService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/mes-creneaux")
public class MesCreneauxServlet extends HttpServlet {

    private final CreneauService creneauService = new CreneauService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Object userObj = session.getAttribute("user");
        if (!(userObj instanceof Specialiste)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès réservé aux spécialistes.");
            return;
        }

        Specialiste specialiste = (Specialiste) userObj;

        // Archiver les créneaux passés automatiquement
        creneauService.archiverCreneauxPasses();

        // Récupérer les créneaux de ce spécialiste
        List<Creneau> creneaux = creneauService.getTousLesCreneaux();
        request.setAttribute("creneaux", creneaux);

        request.getRequestDispatcher("/jsp/mes_creneaux.jsp").forward(request, response);
    }
}
