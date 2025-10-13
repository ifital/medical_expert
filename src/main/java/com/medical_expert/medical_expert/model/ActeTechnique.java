package com.medical_expert.medical_expert.model;

import jakarta.persistence.*;

@Entity
@Table(name = "actes_techniques")
public class ActeTechnique {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private double prix;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    public ActeTechnique() {}

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }

    public Consultation getConsultation() { return consultation; }
    public void setConsultation(Consultation consultation) { this.consultation = consultation; }

    // Optionnel : alias pour mapToDouble dans le calcul
    public double getCout() { return prix; }
}
