package com.medical_expert.medical_expert.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "specialistes")
public class Specialiste extends Medecin {

    private int dureeConsultation = 30;

    @OneToMany(mappedBy = "specialiste", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Creneau> creneaux;

    public Specialiste() {}

    public Specialiste(String username, String password, String role, String nom, String prenom, String email,
                       double tarif, String specialite) {
        super(username, password, role, nom, prenom, email, tarif, specialite);
    }

    // Getters & Setters
    public int getDureeConsultation() { return dureeConsultation; }
    public void setDureeConsultation(int dureeConsultation) { this.dureeConsultation = dureeConsultation; }

    public List<Creneau> getCreneaux() { return creneaux; }
    public void setCreneaux(List<Creneau> creneaux) { this.creneaux = creneaux; }
}
