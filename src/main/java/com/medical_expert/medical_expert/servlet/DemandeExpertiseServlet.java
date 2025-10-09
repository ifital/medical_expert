package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Consultation;
import com.medical_expert.medical_expert.model.DemandeExpertise;
import com.medical_expert.medical_expert.model.Specialiste;
import com.medical_expert.medical_expert.service.ConsultationService;
import com.medical_expert.medical_expert.service.ExpertiseService;
import com.medical_expert.medical_expert.service.MedecinService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/expertise/request")
public class DemandeExpertiseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ExpertiseService expertiseService = new ExpertiseService();
    private final ConsultationService consultationService = new ConsultationService();
    private final MedecinService medecinService = new MedecinService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String consultationIdStr = req.getParameter("consultationId");
        String specialisteIdStr = req.getParameter("specialisteId");
        String question = req.getParameter("question");
        String priorite = req.getParameter("priorite");

        DemandeExpertise demande = new DemandeExpertise();
        demande.setQuestion(question);
        demande.setPriorite(priorite != null ? priorite.toUpperCase() : "NORMALE");
        demande.setStatut("EN_ATTENTE");
        demande.setDateDemande(LocalDateTime.now());

        if (consultationIdStr != null && !consultationIdStr.isEmpty()) {
            Long cid = Long.parseLong(consultationIdStr);
            Consultation c = consultationService.getConsultationById(cid);
            demande.setConsultation(c);
            // mark consultation status
            c.setStatut("EN_ATTENTE_AVIS_SPECIALISTE");
            consultationService.updateConsultation(c);
        }

        if (specialisteIdStr != null && !specialisteIdStr.isEmpty()) {
            Long sid = Long.parseLong(specialisteIdStr);
            Specialiste s = (Specialiste) medecinService.getMedecinById(sid);
            demande.setSpecialiste(s);
        }

        expertiseService.createDemande(demande);

        // Optionally notify specialist (email / websocket) - à implémenter
        resp.sendRedirect(req.getContextPath() + "/jsp/dashboard_generaliste.jsp");
    }
}
