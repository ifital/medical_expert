package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.Creneau;
import com.medical_expert.medical_expert.repository.CreneauRepository;

import java.util.List;

public class CreneauService {

    private final CreneauRepository creneauRepository;

    public CreneauService() {
        this.creneauRepository = new CreneauRepository();
    }

    // Créer un nouveau créneau
    public void ajouterCreneau(Creneau creneau) {
        creneauRepository.save(creneau);
    }

    // Mettre à jour un créneau existant
    public void modifierCreneau(Creneau creneau) {
        creneauRepository.update(creneau);
    }

    // Supprimer un créneau
    public void supprimerCreneau(Long creneauId) {
        Creneau creneau = creneauRepository.findById(creneauId);
        if (creneau != null) {
            creneauRepository.delete(creneau);
        }
    }

    // Lister tous les créneaux
    public List<Creneau> getTousLesCreneaux() {
        return creneauRepository.findAll();
    }

    // Lister les créneaux disponibles pour un spécialiste donné
    public List<Creneau> getCreneauxDisponiblesParSpecialiste(Long specialisteId) {
        return creneauRepository.findAvailableBySpecialiste(specialisteId);
    }

    // Récupérer un créneau par son ID
    public Creneau getCreneauParId(Long id) {
        return creneauRepository.findById(id);
    }
}
