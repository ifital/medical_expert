package com.medical_expert.medical_expert.servlet;

import com.medical_expert.medical_expert.model.Specialiste;
import com.medical_expert.medical_expert.model.User;
import com.medical_expert.medical_expert.service.SpecialisteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profil/config")
public class ProfilServlet extends HttpServlet {

    private final SpecialisteService specialisteService = new SpecialisteService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Object userObj = session.getAttribute("user");
        Specialiste specialiste = null;

        // Vérifier le type de l’objet dans la session
        if (userObj instanceof Specialiste) {
            specialiste = (Specialiste) userObj;
        } else if (userObj instanceof User) {
            User user = (User) userObj;
            specialiste = specialisteService.getByUsername(user.getUsername()); // ✅ correction
        }

        if (specialiste == null) {
            request.setAttribute("error", "Impossible de charger le profil du spécialiste.");
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossible de charger le profil du spécialiste.");
            return;
        }

        request.setAttribute("specialiste", specialiste);
        request.getRequestDispatcher("/jsp/profil_config.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Object userObj = session.getAttribute("user");
        Specialiste specialiste = null;

        if (userObj instanceof Specialiste) {
            specialiste = (Specialiste) userObj;
        } else if (userObj instanceof User) {
            User user = (User) userObj;
            specialiste = specialisteService.getByUsername(user.getUsername()); // ✅ correction
        }

        if (specialiste == null) {
            request.setAttribute("error", "Aucun profil trouvé.");
            request.getRequestDispatcher("/jsp/profil_config.jsp").forward(request, response);
            return;
        }

        try {
            String specialite = request.getParameter("specialite");
            String tarifStr = request.getParameter("tarif");

            if (specialite == null || specialite.isEmpty() || tarifStr == null || tarifStr.isEmpty()) {
                request.setAttribute("error", "Veuillez remplir tous les champs.");
                request.setAttribute("specialiste", specialiste);
                request.getRequestDispatcher("/jsp/profil_config.jsp").forward(request, response);
                return;
            }

            double tarif = Double.parseDouble(tarifStr);

            // ✅ On appelle la méthode métier dédiée
            boolean updated = specialisteService.updateProfil(specialiste.getId(), tarif, specialite);

            if (updated) {
                // Recharger le spécialiste mis à jour
                Specialiste updatedSpecialiste = specialisteService.getById(specialiste.getId());
                session.setAttribute("user", updatedSpecialiste);

                request.setAttribute("specialiste", updatedSpecialiste);
                request.setAttribute("success", "Profil mis à jour avec succès !");
            } else {
                request.setAttribute("error", "Erreur lors de la mise à jour du profil.");
                request.setAttribute("specialiste", specialiste);
            }

            request.getRequestDispatcher("/jsp/profil_config.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Le tarif doit être un nombre valide.");
            request.setAttribute("specialiste", specialiste);
            request.getRequestDispatcher("/jsp/profil_config.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur interne lors de la mise à jour du profil.");
            request.setAttribute("specialiste", specialiste);
            request.getRequestDispatcher("/jsp/profil_config.jsp").forward(request, response);
        }
    }
}
