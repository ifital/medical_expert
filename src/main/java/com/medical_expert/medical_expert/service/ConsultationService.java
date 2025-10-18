package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.Consultation;
import com.medical_expert.medical_expert.model.Patient;
import com.medical_expert.medical_expert.model.Generaliste;
import com.medical_expert.medical_expert.repository.ConsultationRepository;

import java.time.LocalDate;
import java.util.List;

public class ConsultationService {

    private final ConsultationRepository consultationRepo = new ConsultationRepository();

    /**
     * Crée une consultation uniquement si elle n'existe pas déjà pour le même patient,
     * généraliste et motif à la même date.
     *
     * @param consultation La consultation à ajouter
     * @return true si la consultation a été créée, false si elle existe déjà
     */
    public boolean createConsultation(Consultation consultation) {
        Patient patient = consultation.getPatient();
        Generaliste generaliste = consultation.getGeneraliste();
        String motif = consultation.getMotif();
        LocalDate date = consultation.getDateConsultation().toLocalDate();

        if (consultationRepo.existsConsultation(patient, generaliste, motif, date)) {
            return false; // Consultation existante
        }

        consultationRepo.save(consultation);
        return true; // Consultation ajoutée
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
