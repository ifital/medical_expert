package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Patient;
import com.medical_expert.medical_expert.service.PatientService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/patients/add")
public class PatientAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PatientService patientService = new PatientService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/dashboard_infirmier.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String numSS = req.getParameter("numeroSecuriteSociale");
        String telephone = req.getParameter("telephone");
        String adresse = req.getParameter("adresse");
        String mutuelle = req.getParameter("mutuelle");

        Patient p = new Patient();
        p.setNom(nom);
        p.setPrenom(prenom);
        p.setNumeroSecuriteSociale(numSS);
        p.setTelephone(telephone);
        p.setAdresse(adresse);
        p.setMutuelle(mutuelle);
        p.setDateArrivee(LocalDateTime.now());

        // signes vitaux optionnels
        try {
            String tension = req.getParameter("tension");
            if (tension != null && !tension.isEmpty()) p.setTension(Double.parseDouble(tension));
            String temp = req.getParameter("temperature");
            if (temp != null && !temp.isEmpty()) p.setTemperature(Double.parseDouble(temp));
            String fc = req.getParameter("frequenceCardiaque");
            if (fc != null && !fc.isEmpty()) p.setFrequenceCardiaque(Integer.parseInt(fc));
            String fr = req.getParameter("frequenceRespiratoire");
            if (fr != null && !fr.isEmpty()) p.setFrequenceRespiratoire(Integer.parseInt(fr));
            String poids = req.getParameter("poids");
            if (poids != null && !poids.isEmpty()) p.setPoids(Double.parseDouble(poids));
            String taille = req.getParameter("taille");
            if (taille != null && !taille.isEmpty()) p.setTaille(Double.parseDouble(taille));
        } catch (NumberFormatException ignored) {}

        patientService.createPatient(p);
        resp.sendRedirect(req.getContextPath() + "/jsp/dashboard_infirmier.jsp");
    }
}
