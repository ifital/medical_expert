package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Creneau;
import com.medical_expert.medical_expert.model.DemandeExpertise;
import com.medical_expert.medical_expert.model.Specialiste;
import com.medical_expert.medical_expert.model.Consultation;
import com.medical_expert.medical_expert.repository.CreneauRepository;
import com.medical_expert.medical_expert.repository.SpecialisteRepository;
import com.medical_expert.medical_expert.repository.ConsultationRepository;
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
    private final CreneauRepository creneauRepo = new CreneauRepository();
    private final SpecialisteRepository specialisteRepo = new SpecialisteRepository();
    private final ConsultationRepository consultationRepo = new ConsultationRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Récupération des paramètres de filtrage
        String statutParam = request.getParameter("statut");
        String prioriteParam = request.getParameter("priorite");

        // Récupération de toutes les demandes
        List<DemandeExpertise> allDemandes = expertiseService.getAllDemandes();

        // Filtrage via Stream API
        List<DemandeExpertise> filteredDemandes = allDemandes.stream()
                .filter(d -> (statutParam == null || d.getStatut().equalsIgnoreCase(statutParam)))
                .filter(d -> (prioriteParam == null || d.getPriorite().equalsIgnoreCase(prioriteParam)))
                .collect(Collectors.toList());

        request.setAttribute("demandes", filteredDemandes);

        // Redirection vers la JSP
        request.getRequestDispatcher("/jsp/dashboard_specialiste.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long consultationId = Long.parseLong(request.getParameter("consultationId"));
            Long specialisteId = Long.parseLong(request.getParameter("specialisteId"));
            Long creneauId = Long.parseLong(request.getParameter("creneauId"));
            String question = request.getParameter("question");
            String priorite = request.getParameter("priorite");

            // Récupération depuis la BDD
            Consultation consultation = consultationRepo.findById(consultationId);
            Specialiste specialiste = specialisteRepo.findById(specialisteId);
            Creneau creneau = creneauRepo.findById(creneauId);

            if (consultation == null || specialiste == null || creneau == null) {
                request.setAttribute("error", "Consultation, spécialiste ou créneau invalide.");
                request.getRequestDispatcher("/jsp/formulaire_demande.jsp").forward(request, response);
                return;
            }

            if (!creneau.isDisponible()) {
                request.setAttribute("error", "Ce créneau n'est plus disponible.");
                return;
            }

            // Création de la demande
            DemandeExpertise demande = new DemandeExpertise();
            demande.setConsultation(consultation);
            demande.setSpecialiste(specialiste);
            demande.setCreneau(creneau);
            demande.setQuestion(question);
            demande.setPriorite(priorite);

            // Bloquer le créneau et mettre à jour BDD
            creneau.setDisponible(false);
            creneauRepo.update(creneau);

            // Sauvegarder la demande
            expertiseService.createDemande(demande);

            response.sendRedirect(request.getContextPath() + "/dashboard/generaliste");

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID consultation, spécialiste ou créneau invalide.");
            request.getRequestDispatcher("/jsp/formulaire_demande.jsp").forward(request, response);
        }
    }
}
