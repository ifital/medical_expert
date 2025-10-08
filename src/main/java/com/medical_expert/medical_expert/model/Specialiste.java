package com.medical_expert.medical_expert.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "specialistes")
public class Specialiste extends Medecin {
    private int dureeConsultation = 30; // minutes
    private boolean disponible = true;

    public Specialiste() {}

    public Specialiste(String username, String password, String role, String nom, String prenom, String email,
                       double tarif, String specialite) {
        super(username, password, role, nom, prenom, email, tarif, specialite);
    }

    public int getDureeConsultation() { return dureeConsultation; }
    public void setDureeConsultation(int dureeConsultation) { this.dureeConsultation = dureeConsultation; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
