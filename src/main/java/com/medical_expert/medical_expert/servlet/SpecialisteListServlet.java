package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Medecin;
import com.medical_expert.medical_expert.service.MedecinService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/specialistes")
public class SpecialisteListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final MedecinService medecinService = new MedecinService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String specialite = req.getParameter("specialite");
        List<Medecin> liste;
        if (specialite != null && !specialite.isEmpty()) {
            liste = medecinService.getMedecinsBySpecialite(specialite);
        } else {
            liste = medecinService.getAllMedecins();
        }
        req.setAttribute("specialistes", liste);
        req.getRequestDispatcher("/jsp/specialiste_list.jsp").forward(req, resp);
    }
}
