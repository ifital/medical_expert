package com.medical_expert.medical_expert.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "generalistes")
public class Generaliste extends Medecin {
    private static final double TARIF_FIXE = 150.0;

    public Generaliste() {}

    public Generaliste(String username, String password, String role, String nom, String prenom, String email,
                       String specialite) {
        super(username, password, role, nom, prenom, email, TARIF_FIXE, specialite);
    }
}
