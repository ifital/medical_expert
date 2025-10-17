package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.Specialiste;
import com.medical_expert.medical_expert.repository.SpecialisteRepository;

import java.util.List;

public class SpecialisteService {

    private final SpecialisteRepository repository = new SpecialisteRepository();

    // --- CRUD de base ---

    public void createSpecialiste(Specialiste specialiste) {
        if (specialiste == null) {
            throw new IllegalArgumentException("Le spécialiste ne peut pas être null");
        }
        specialiste.setDureeConsultation(30); // durée fixe
        repository.save(specialiste);
    }

    public void updateSpecialiste(Specialiste specialiste) {
        if (specialiste == null || specialiste.getId() == null) {
            throw new IllegalArgumentException("Spécialiste invalide pour la mise à jour");
        }
        repository.update(specialiste);
    }

    public void deleteSpecialiste(Specialiste specialiste) {
        if (specialiste == null || specialiste.getId() == null) {
            throw new IllegalArgumentException("Spécialiste invalide pour la suppression");
        }
        repository.delete(specialiste);
    }

    public Specialiste getById(Long id) {
        if (id == null) return null;
        return repository.findById(id);
    }

    public Specialiste getByUsername(String username) {
        if (username == null || username.isEmpty()) return null;
        return repository.findByUsername(username);
    }

    public List<Specialiste> getAllSpecialistes() {
        return repository.findAll();
    }

    // --- Méthodes métier supplémentaires ---

    public boolean isUsernameTaken(String username) {
        return getByUsername(username) != null;
    }

    public List<Specialiste> getAvailableSpecialistes() {
        return repository.findAll();
    }

    // --- ✅ Méthode spécifique à US5 : mise à jour du profil ---
    public boolean updateProfil(Long id, double tarif, String specialite) {
        Specialiste specialiste = repository.findById(id);
        if (specialiste == null) {
            return false; // pas trouvé
        }

        // Mise à jour des champs
        specialiste.setTarif(tarif);
        specialiste.setSpecialite(specialite);
        specialiste.setDureeConsultation(30); // valeur fixe

        repository.update(specialiste);
        return true;
    }
}
