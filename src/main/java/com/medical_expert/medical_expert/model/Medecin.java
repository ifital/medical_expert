package com.medical_expert.medical_expert.model;


import jakarta.persistence.*;

@Entity
@Table(name = "medecins")
@Inheritance(strategy = InheritanceType.JOINED)
public class Medecin extends User {

    private double tarif;
    private String specialite;

    public Medecin() {}

    public Medecin(String username, String password, String role, String nom, String prenom, String email,
                   double tarif, String specialite) {
        super(username, password, role, nom, prenom, email);
        this.tarif = tarif;
        this.specialite = specialite;
    }

    public double getTarif() { return tarif; }
    public void setTarif(double tarif) { this.tarif = tarif; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    @Override
    public String toString() {
        return "Medecin{" +
                super.toString() +
                ", tarif=" + tarif +
                ", specialite='" + specialite + '\'' +
                '}';
    }
}
