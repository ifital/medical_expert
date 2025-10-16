package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.DemandeExpertise;
import com.medical_expert.medical_expert.service.ExpertiseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/expertise/request")
public class DemandeExpertiseServlet extends HttpServlet {

    private final ExpertiseService expertiseService = new ExpertiseService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération des paramètres de filtrage
        String statutParam = request.getParameter("statut"); // EN_ATTENTE, TERMINEE
        String prioriteParam = request.getParameter("priorite"); // ex: HAUTE, NORMALE, BASSE

        // Récupération de toutes les demandes
        List<DemandeExpertise> allDemandes = expertiseService.getAllDemandes();

        // Filtrage via Stream API
        List<DemandeExpertise> filteredDemandes = allDemandes.stream()
                .filter(d -> (statutParam == null || d.getStatut().equalsIgnoreCase(statutParam)))
                .filter(d -> (prioriteParam == null || d.getPriorite().equalsIgnoreCase(prioriteParam)))
                .collect(Collectors.toList());

        // Ajout de la liste filtrée en attribut pour JSP
        request.setAttribute("demandes", filteredDemandes);

        // Redirection vers la JSP de consultation
        request.getRequestDispatcher("/jsp/dashboard_specialiste.jsp").forward(request, response);
    }
}
