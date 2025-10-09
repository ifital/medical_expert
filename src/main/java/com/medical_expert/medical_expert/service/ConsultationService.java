package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.Consultation;
import com.medical_expert.medical_expert.repository.ConsultationRepository;

import java.util.List;

public class ConsultationService {

    private final ConsultationRepository consultationRepo = new ConsultationRepository();

    public void createConsultation(Consultation consultation) {
        consultationRepo.save(consultation);
    }

    public Consultation getConsultationById(Long id) {
        return consultationRepo.findById(id);
    }

    public List<Consultation> getAllConsultations() {
        return consultationRepo.findAll();
    }

    public void updateConsultation(Consultation consultation) {
        consultationRepo.update(consultation);
    }

    public void deleteConsultation(Consultation consultation) {
        consultationRepo.delete(consultation);
    }

    public List<Consultation> getConsultationsByStatut(String statut) {
        return consultationRepo.findByStatut(statut);
    }
}
