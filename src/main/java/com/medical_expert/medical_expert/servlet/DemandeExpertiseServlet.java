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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération des paramètres du formulaire
        String consultationIdStr = request.getParameter("consultationId");
        String specialisteIdStr = request.getParameter("specialisteId");
        String question = request.getParameter("question");
        String priorite = request.getParameter("priorite");

        try {
            Long consultationId = Long.parseLong(consultationIdStr);
            Long specialisteId = Long.parseLong(specialisteIdStr);

            // Création de la demande d'expertise
            DemandeExpertise demande = new DemandeExpertise();

            // Création des objets Consultation et Specialiste avec juste l'ID
            // (supposé que ton repository gère les relations via JPA)
            com.medical_expert.medical_expert.model.Consultation consultation =
                    new com.medical_expert.medical_expert.model.Consultation();
            consultation.setId(consultationId);

            com.medical_expert.medical_expert.model.Specialiste specialiste =
                    new com.medical_expert.medical_expert.model.Specialiste();
            specialiste.setId(specialisteId);

            demande.setConsultation(consultation);
            demande.setSpecialiste(specialiste);
            demande.setQuestion(question);
            demande.setPriorite(priorite);

            // Le statut par défaut "EN_ATTENTE" est déjà défini dans l'entité
            expertiseService.createDemande(demande);

            // Redirection vers la liste des demandes après insertion
            response.sendRedirect(request.getContextPath() + "/dashboard/generaliste");

        } catch (NumberFormatException e) {
            // Gestion simple des erreurs de parsing
            request.setAttribute("error", "ID consultation ou spécialiste invalide.");
            request.getRequestDispatcher("/jsp/formulaire_demande.jsp").forward(request, response);
        }
    }

}
