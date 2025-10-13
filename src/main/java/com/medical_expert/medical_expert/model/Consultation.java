package com.medical_expert.medical_expert.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "consultations")
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "generaliste_id")
    private Generaliste generaliste;

    private String motif;
    private String observations;
    private String diagnostic;
    private double cout = 150.0;
    private String statut; // TERMINEE ou EN_ATTENTE_AVIS_SPECIALISTE
    private LocalDateTime dateConsultation = LocalDateTime.now();

    // --- Nouvelle relation ---
    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ActeTechnique> actesTechniques = new ArrayList<>();

    public Consultation() {}

    // --- Getters & Setters existants ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Generaliste getGeneraliste() { return generaliste; }
    public void setGeneraliste(Generaliste generaliste) { this.generaliste = generaliste; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public double getCout() { return cout; }
    public void setCout(double cout) { this.cout = cout; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDateTime getDateConsultation() { return dateConsultation; }
    public void setDateConsultation(LocalDateTime dateConsultation) { this.dateConsultation = dateConsultation; }

    // --- Getter/Setter pour actes techniques ---
    public List<ActeTechnique> getActesTechniques() {
        return actesTechniques;
    }

    public void setActesTechniques(List<ActeTechnique> actesTechniques) {
        this.actesTechniques = actesTechniques;
    }

    public void addActeTechnique(ActeTechnique acte) {
        actesTechniques.add(acte);
        acte.setConsultation(this);
    }

    public void removeActeTechnique(ActeTechnique acte) {
        actesTechniques.remove(acte);
        acte.setConsultation(null);
    }
}
