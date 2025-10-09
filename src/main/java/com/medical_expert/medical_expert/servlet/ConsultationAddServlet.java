package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Consultation;
import com.medical_expert.medical_expert.model.Generaliste;
import com.medical_expert.medical_expert.model.Patient;
import com.medical_expert.medical_expert.service.ConsultationService;
import com.medical_expert.medical_expert.service.MedecinService;
import com.medical_expert.medical_expert.service.PatientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/consultations/add")
public class ConsultationAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ConsultationService consultationService = new ConsultationService();
    private final PatientService patientService = new PatientService();
    private final MedecinService medecinService = new MedecinService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // forward vers formulaire si besoin
        req.getRequestDispatcher("/jsp/consultation_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String patientIdStr = req.getParameter("patientId");
        String generalisteIdStr = req.getParameter("generalisteId");
        String motif = req.getParameter("motif");
        String observations = req.getParameter("observations");

        Consultation c = new Consultation();
        c.setMotif(motif);
        c.setObservations(observations);
        c.setDateConsultation(LocalDateTime.now());
        c.setStatut("EN_COURS");
        c.setCout(150.0);

        if (patientIdStr != null && !patientIdStr.isEmpty()) {
            Long pid = Long.parseLong(patientIdStr);
            Patient p = patientService.getPatientById(pid);
            c.setPatient(p);
        }

        if (generalisteIdStr != null && !generalisteIdStr.isEmpty()) {
            Long gid = Long.parseLong(generalisteIdStr);
            Generaliste g = (Generaliste) medecinService.getMedecinById(gid);
            c.setGeneraliste(g);
        } else {
            // try to get generaliste from session user
            HttpSession session = req.getSession(false);
            if (session != null) {
                Object userObj = session.getAttribute("user");
                if (userObj instanceof Generaliste) {
                    c.setGeneraliste((Generaliste) userObj);
                }
            }
        }

        consultationService.createConsultation(c);
        resp.sendRedirect(req.getContextPath() + "/jsp/dashboard_generaliste.jsp");
    }
}
