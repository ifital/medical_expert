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
import java.util.List;

@WebServlet("/consultations/add")
public class ConsultationAddServlet extends HttpServlet {
    private final ConsultationService consultationService = new ConsultationService();
    private final PatientService patientService = new PatientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Liste des patients disponibles
        List<Patient> patients = patientService.getAllPatients();
        req.setAttribute("patients", patients);

        req.getRequestDispatcher("/jsp/consultation_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String patientIdStr = req.getParameter("patientId");
        String motif = req.getParameter("motif");
        String observations = req.getParameter("observations");

        if (patientIdStr == null || patientIdStr.isEmpty()) {
            req.setAttribute("error", "Veuillez sélectionner un patient.");
            doGet(req, resp);
            return;
        }

        Long pid = Long.parseLong(patientIdStr);
        Patient patient = patientService.getPatientById(pid);

        HttpSession session = req.getSession(false);
        Generaliste generaliste = (session != null && session.getAttribute("user") instanceof Generaliste)
                ? (Generaliste) session.getAttribute("user")
                : null;

        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setGeneraliste(generaliste);
        consultation.setMotif(motif);
        consultation.setObservations(observations);
        consultation.setCout(150.0);
        consultation.setStatut("EN_COURS");
        consultation.setDateConsultation(LocalDateTime.now());

        consultationService.createConsultation(consultation);

        resp.sendRedirect(req.getContextPath() + "/dashboard/generaliste");
    }
}
