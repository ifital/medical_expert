package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Consultation;
import com.medical_expert.medical_expert.model.Creneau;
import com.medical_expert.medical_expert.model.Patient;
import com.medical_expert.medical_expert.model.Specialiste;
import com.medical_expert.medical_expert.service.CreneauService;
import com.medical_expert.medical_expert.service.ConsultationService;
import com.medical_expert.medical_expert.service.MedecinService;
import com.medical_expert.medical_expert.service.PatientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/dashboard/generaliste")
public class DashboardGeneralisteServlet extends HttpServlet {

    private final ConsultationService consultationService = new ConsultationService();
    private final MedecinService medecinService = new MedecinService();
    private final PatientService patientService = new PatientService();
    private final CreneauService creneauService = new CreneauService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 🔹 Récupération des consultations (triées par date décroissante)
        List<Consultation> consultations = consultationService.getAllConsultations()
                .stream()
                .sorted(Comparator.comparing(Consultation::getDateConsultation).reversed())
                .collect(Collectors.toList());

        // 🔹 Récupération des patients (triés par date d’arrivée)
        List<Patient> patients = patientService.getAllPatients()
                .stream()
                .sorted(Comparator.comparing(Patient::getDateArrivee).reversed())
                .collect(Collectors.toList());

        // 🔹 Récupération des spécialistes
        List<Specialiste> specialistes = medecinService.getAllSpecialistes();

        // 🔹 Filtrage optionnel par spécialité
        String specialite = req.getParameter("specialite");
        if (specialite != null && !specialite.isEmpty()) {
            specialistes = specialistes.stream()
                    .filter(s -> s.getSpecialite().equalsIgnoreCase(specialite))
                    .collect(Collectors.toList());
        }

        // 🔹 Récupération des créneaux disponibles par spécialiste
        Map<Long, List<Creneau>> creneauxDisponibles = new HashMap<>();
        for (Specialiste s : specialistes) {
            List<Creneau> dispo = creneauService.getCreneauxDisponiblesParSpecialiste(s.getId());
            creneauxDisponibles.put(s.getId(), dispo);
        }

        // 🔹 Envoi des données à la JSP
        req.setAttribute("consultations", consultations);
        req.setAttribute("patients", patients);
        req.setAttribute("specialistes", specialistes);
        req.setAttribute("creneauxDisponibles", creneauxDisponibles); // ✅

        req.getRequestDispatcher("/jsp/dashboard_generaliste.jsp").forward(req, resp);
    }
}
