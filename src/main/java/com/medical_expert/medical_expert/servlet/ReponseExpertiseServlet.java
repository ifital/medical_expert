package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.DemandeExpertise;
import com.medical_expert.medical_expert.service.ExpertiseService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/expertise/respond")
public class ReponseExpertiseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ExpertiseService expertiseService = new ExpertiseService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String demandeIdStr = req.getParameter("demandeId");
        String reponse = req.getParameter("reponse");
        String recommandations = req.getParameter("recommandations");

        if (demandeIdStr == null || demandeIdStr.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "demandeId requis");
            return;
        }

        Long did = Long.parseLong(demandeIdStr);
        DemandeExpertise demande = expertiseService.getDemandeById(did);
        if (demande == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Demande non trouvée");
            return;
        }

        demande.setReponse(reponse);
        demande.setRecommandations(recommandations);
        demande.setStatut("TERMINEE");

        expertiseService.updateDemande(demande);

        // éventuellement revenir à la liste des demandes
        resp.sendRedirect(req.getContextPath() + "/jsp/dashboard_specialiste.jsp");
    }
}
