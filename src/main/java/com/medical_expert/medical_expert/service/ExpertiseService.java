package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.DemandeExpertise;
import com.medical_expert.medical_expert.repository.DemandeExpertiseRepository;

import java.util.List;

public class ExpertiseService {

    private final DemandeExpertiseRepository demandeRepo = new DemandeExpertiseRepository();

    public void createDemande(DemandeExpertise demande) {
        demandeRepo.save(demande);
    }

    public DemandeExpertise getDemandeById(Long id) {
        return demandeRepo.findById(id);
    }

    public List<DemandeExpertise> getAllDemandes() {
        return demandeRepo.findAll();
    }

    public void updateDemande(DemandeExpertise demande) {
        demandeRepo.update(demande);
    }

    public void deleteDemande(DemandeExpertise demande) {
        demandeRepo.delete(demande);
    }

    public List<DemandeExpertise> getDemandesByStatut(String statut) {
        return demandeRepo.findByStatut(statut);
    }
}