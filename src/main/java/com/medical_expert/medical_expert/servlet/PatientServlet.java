package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Patient;
import com.medical_expert.medical_expert.service.PatientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/patients")
public class PatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PatientService patientService = new PatientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null || action.equals("list")) {
            // 📋 Liste des patients
            List<Patient> patients = patientService.getAllPatients();
            req.setAttribute("patients", patients);
            req.getRequestDispatcher("/jsp/dashboard_infirmier.jsp").forward(req, resp);
        }
        else if (action.equals("edit")) {
            // ✏️ Préparer les données d’un patient pour édition
            Long id = Long.parseLong(req.getParameter("id"));
            Patient patient = patientService.getPatientById(id);
            req.setAttribute("patientToEdit", patient);
            req.setAttribute("patients", patientService.getAllPatients());
            req.getRequestDispatcher("/jsp/dashboard_infirmier.jsp").forward(req, resp);
        }
        else if (action.equals("delete")) {
            // 🗑️ Supprimer un patient
            Long id = Long.parseLong(req.getParameter("id"));
            patientService.deletePatientById(id);
            resp.sendRedirect(req.getContextPath() + "/patients?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        // ➕ Ajout ou ✏️ Mise à jour
        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String numSS = req.getParameter("numeroSecuriteSociale");
        String telephone = req.getParameter("telephone");
        String adresse = req.getParameter("adresse");
        String mutuelle = req.getParameter("mutuelle");

        double tension = parseDouble(req.getParameter("tension"));
        double temperature = parseDouble(req.getParameter("temperature"));
        int frequenceCardiaque = parseInt(req.getParameter("frequenceCardiaque"));
        int frequenceRespiratoire = parseInt(req.getParameter("frequenceRespiratoire"));
        double poids = parseDouble(req.getParameter("poids"));
        double taille = parseDouble(req.getParameter("taille"));

        if ("update".equals(action)) {
            // 🔄 Mise à jour d’un patient existant
            Long id = Long.parseLong(req.getParameter("id"));
            Patient p = patientService.getPatientById(id);
            if (p != null) {
                p.setNom(nom);
                p.setPrenom(prenom);
                p.setNumeroSecuriteSociale(numSS);
                p.setTelephone(telephone);
                p.setAdresse(adresse);
                p.setMutuelle(mutuelle);
                p.setTension(tension);
                p.setTemperature(temperature);
                p.setFrequenceCardiaque(frequenceCardiaque);
                p.setFrequenceRespiratoire(frequenceRespiratoire);
                p.setPoids(poids);
                p.setTaille(taille);
                patientService.updatePatient(p);
            }
        } else {
            // ➕ Création d’un nouveau patient
            Patient p = new Patient();
            p.setNom(nom);
            p.setPrenom(prenom);
            p.setNumeroSecuriteSociale(numSS);
            p.setTelephone(telephone);
            p.setAdresse(adresse);
            p.setMutuelle(mutuelle);
            p.setDateArrivee(LocalDateTime.now());
            p.setTension(tension);
            p.setTemperature(temperature);
            p.setFrequenceCardiaque(frequenceCardiaque);
            p.setFrequenceRespiratoire(frequenceRespiratoire);
            p.setPoids(poids);
            p.setTaille(taille);

            patientService.createPatient(p);
        }

        resp.sendRedirect(req.getContextPath() + "/patients?action=list");
    }

    private double parseDouble(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Double.parseDouble(value) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private int parseInt(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
