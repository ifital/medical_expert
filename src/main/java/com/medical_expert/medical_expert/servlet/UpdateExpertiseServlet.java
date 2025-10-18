package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.DemandeExpertise;
import com.medical_expert.medical_expert.service.ExpertiseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateExpertiseServlet", urlPatterns = {"/expertise/update/*"})
public class UpdateExpertiseServlet extends HttpServlet {

    private final ExpertiseService expertiseService = new ExpertiseService();

    // doGet pour tester la servlet si nécessaire
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo(); // ex: /1
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID manquant");
            return;
        }

        Long id;
        try {
            id = Long.parseLong(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID invalide");
            return;
        }

        DemandeExpertise demande = expertiseService.getDemandeById(id);
        if (demande == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Demande introuvable");
            return;
        }

        // Simple affichage pour test
        response.setContentType("text/plain");
        response.getWriter().println("Servlet accessible ! ID: " + id);
        response.getWriter().println("Statut actuel: " + demande.getStatut());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo(); // ex: /1
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID manquant");
            return;
        }

        Long id;
        try {
            id = Long.parseLong(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID invalide");
            return;
        }

        // Récupérer la demande existante
        DemandeExpertise demande = expertiseService.getDemandeById(id);
        if (demande == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Demande introuvable");
            return;
        }

        // Récupérer les données du formulaire
        String reponse = request.getParameter("reponse");
        String recommandations = request.getParameter("recommandations");
        boolean terminer = request.getParameter("terminer") != null;

        // Mettre à jour la demande
        demande.setReponse(reponse);
        demande.setRecommandations(recommandations);
        demande.setStatut(terminer ? "TERMINEE" : "EN_ATTENTE");

        // Sauvegarder via le service
        expertiseService.updateDemande(demande);

        // Redirection vers la page des demandes
        response.sendRedirect(request.getContextPath() + "/expertise/request");
    }
}
