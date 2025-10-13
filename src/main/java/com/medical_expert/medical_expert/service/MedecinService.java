package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.Medecin;
import com.medical_expert.medical_expert.model.Specialiste;
import com.medical_expert.medical_expert.repository.MedecinRepository;

import java.util.List;
import java.util.stream.Collectors;

public class MedecinService {

    private final MedecinRepository medecinRepo = new MedecinRepository();

    public void createMedecin(Medecin medecin) {
        medecinRepo.save(medecin);
    }

    public Medecin getMedecinById(Long id) {
        return medecinRepo.findById(id);
    }

    public List<Medecin> getAllMedecins() {
        return medecinRepo.findAll();
    }

    public void updateMedecin(Medecin medecin) {
        medecinRepo.update(medecin);
    }

    public void deleteMedecin(Medecin medecin) {
        medecinRepo.delete(medecin);
    }

    public List<Medecin> getMedecinsBySpecialite(String specialite) {
        return medecinRepo.findBySpecialite(specialite);
    }

    // ✅ Nouvelle méthode pour récupérer tous les spécialistes
    public List<Specialiste> getAllSpecialistes() {
        return medecinRepo.findAll().stream()
                .filter(m -> m instanceof Specialiste)
                .map(m -> (Specialiste) m)
                .collect(Collectors.toList());
    }
}
