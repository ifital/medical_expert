package com.medical_expert.medical_expert.service;

import com.medical_expert.medical_expert.model.Patient;
import com.medical_expert.medical_expert.repository.PatientRepository;

import java.util.List;

public class PatientService {

    private final PatientRepository patientRepo = new PatientRepository();

    public void createPatient(Patient patient) {
        patientRepo.save(patient);
    }

    public Patient getPatientById(Long id) {
        return patientRepo.findById(id);
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public void updatePatient(Patient patient) {
        patientRepo.update(patient);
    }

    public void deletePatient(Patient patient) {
        patientRepo.delete(patient);
    }
}