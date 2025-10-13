package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Patient;
import com.medical_expert.medical_expert.service.PatientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/patients")
public class PatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PatientService patientService = new PatientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "delete":
                supprimerPatient(req, resp);
                break;
            case "filter":
                filtrerParDate(req, resp);
                break;
            default:
                afficherPatients(req, resp);
                break;
        }
    }

    // ✅ Afficher la liste des patients triés du plus récent au plus ancien
    private void afficherPatients(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Patient> patients = patientService.getAllPatients();

        patients.sort(Comparator.comparing(Patient::getDateArrivee).reversed());
        req.setAttribute("patients", patients);
        req.getRequestDispatcher("/jsp/dashboard_infirmier.jsp").forward(req, resp);
    }


    // ✅ Supprimer un patient
    private void supprimerPatient(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        patientService.deletePatientById(id);
        resp.sendRedirect(req.getContextPath() + "/patients?action=list");
    }

    // ✅ Filtrer par date avec Stream API + mise à jour directe de la liste
    private void filtrerParDate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String dateParam = req.getParameter("date");
        List<Patient> patients = patientService.getAllPatients();

        if (dateParam != null && !dateParam.isEmpty()) {
            LocalDate dateRecherche = LocalDate.parse(dateParam);
            patients = patients.stream()
                    .filter(p -> p.getDateArrivee().toLocalDate().equals(dateRecherche))
                    .sorted(Comparator.comparing(Patient::getDateArrivee).reversed())
                    .collect(Collectors.toList());
        } else {
            patients.sort(Comparator.comparing(Patient::getDateArrivee).reversed());
        }

        req.setAttribute("patients", patients);
        req.setAttribute("dateRecherche", dateParam);
        req.getRequestDispatcher("/jsp/dashboard_infirmier.jsp").forward(req, resp);
    }

    // ✅ Création ou mise à jour directe d’un patient (sans redirect)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

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

        // ✅ Afficher directement la liste mise à jour (sans reload manuel)
        List<Patient> patients = patientService.getAllPatients()
                .stream()
                .sorted(Comparator.comparing(Patient::getDateArrivee).reversed())
                .collect(Collectors.toList());

        req.setAttribute("patients", patients);
        req.getRequestDispatcher("/jsp/dashboard_infirmier.jsp").forward(req, resp);
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
