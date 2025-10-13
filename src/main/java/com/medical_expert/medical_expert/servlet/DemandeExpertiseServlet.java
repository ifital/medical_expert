package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.*;
import com.medical_expert.medical_expert.service.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/expertise/request")
public class DemandeExpertiseServlet extends HttpServlet {
    private final ExpertiseService expertiseService = new ExpertiseService();
    private final ConsultationService consultationService = new ConsultationService();
    private final MedecinService medecinService = new MedecinService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String specialite = req.getParameter("specialite");

        List<Specialiste> specialistes = medecinService.getAllSpecialistes();

        // ✅ Filtrage Stream API : par spécialité et tarif < 500dh
        if (specialite != null && !specialite.isEmpty()) {
            specialistes = specialistes.stream()
                    .filter(s -> s.getSpecialite().equalsIgnoreCase(specialite))
                    .filter(s -> s.getTarif() <= 500)
                    .collect(Collectors.toList());
        }

        req.setAttribute("specialistes", specialistes);
        req.getRequestDispatcher("/jsp/expertise_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Long consultationId = Long.parseLong(req.getParameter("consultationId"));
        Long specialisteId = Long.parseLong(req.getParameter("specialisteId"));
        String question = req.getParameter("question");
        String priorite = req.getParameter("priorite");

        Consultation consultation = consultationService.getConsultationById(consultationId);
        Specialiste specialiste = (Specialiste) medecinService.getMedecinById(specialisteId);

        DemandeExpertise demande = new DemandeExpertise();
        demande.setConsultation(consultation);
        demande.setSpecialiste(specialiste);
        demande.setQuestion(question);
        demande.setPriorite(priorite);
        demande.setDateDemande(LocalDateTime.now());
        demande.setStatut("EN_ATTENTE");

        expertiseService.createDemande(demande);

        // ✅ Calcul du coût total (Lambda + mapToDouble)
        double coutTotal = 150.0 + specialiste.getTarif()
                + consultation.getActesTechniques().stream()
                .mapToDouble(ActeTechnique::getCout)
                .sum();

        req.setAttribute("consultation", consultation);
        req.setAttribute("specialiste", specialiste);
        req.setAttribute("coutTotal", coutTotal);

        req.getRequestDispatcher("/jsp/expertise_result.jsp").forward(req, resp);
    }
}
