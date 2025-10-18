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

@WebServlet("/dashboard/infirmier")
public class DashboardInfirmierServlet extends HttpServlet {
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
                afficherPatientsSansConsultation(req, resp); // ✅ par défaut : afficher les patients sans consultation
                break;
        }
    }

    /** ✅ Afficher uniquement les patients sans consultation */
    private void afficherPatientsSansConsultation(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Patient> patients = patientService.getPatientsWithoutConsultation()
                .stream()
                .sorted(Comparator.comparing(Patient::getDateArrivee).reversed())
                .collect(Collectors.toList());

        req.setAttribute("patients", patients);
        req.getRequestDispatcher("/jsp/dashboard_infirmier.jsp").forward(req, resp);
    }

    /** ✅ Supprimer un patient */
    private void supprimerPatient(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        patientService.deletePatientById(id);
        resp.sendRedirect(req.getContextPath() + "/dashboard/infirmier?action=list");
    }

    /** ✅ Filtrer par date (sur les patients sans consultation) */
    private void filtrerParDate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String dateParam = req.getParameter("date");
        List<Patient> patients = patientService.getPatientsWithoutConsultation();

        if (dateParam != null && !dateParam.isEmpty()) {
            LocalDate dateRecherche = LocalDate.parse(dateParam);
            patients = patients.stream()
                    .filter(p -> p.getDateArrivee().toLocalDate().equals(dateRecherche))
                    .sorted(Comparator.comparing(Patient::getDateArrivee).reversed())
                    .collect(Collectors.toList());
            req.setAttribute("dateRecherche", dateParam);
        } else {
            patients.sort(Comparator.comparing(Patient::getDateArrivee).reversed());
        }

        req.setAttribute("patients", patients);
        req.getRequestDispatcher("/jsp/dashboard_infirmier.jsp").forward(req, resp);
    }

    /** ✅ Ajouter ou modifier un patient */
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

        // ✅ Redirection vers la liste des patients sans consultation
        resp.sendRedirect(req.getContextPath() + "/dashboard/infirmier?action=list");
    }

    /** Outils de parsing sécurisés */
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
