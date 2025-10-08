package com.medical_expert.medical_expert.model;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String numeroSecuriteSociale;
    private String telephone;
    private String adresse;
    private String mutuelle;

    private LocalDateTime dateArrivee;
    private double tension;
    private double temperature;
    private int frequenceCardiaque;
    private int frequenceRespiratoire;
    private double poids;
    private double taille;

    public Patient() {}

    public Patient(String nom, String prenom, String numeroSecuriteSociale, String telephone,
                   String adresse, String mutuelle, LocalDateTime dateArrivee,
                   double tension, double temperature, int frequenceCardiaque,
                   int frequenceRespiratoire, double poids, double taille) {
        this.nom = nom;
        this.prenom = prenom;
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.telephone = telephone;
        this.adresse = adresse;
        this.mutuelle = mutuelle;
        this.dateArrivee = dateArrivee;
        this.tension = tension;
        this.temperature = temperature;
        this.frequenceCardiaque = frequenceCardiaque;
        this.frequenceRespiratoire = frequenceRespiratoire;
        this.poids = poids;
        this.taille = taille;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getNumeroSecuriteSociale() { return numeroSecuriteSociale; }
    public void setNumeroSecuriteSociale(String numeroSecuriteSociale) { this.numeroSecuriteSociale = numeroSecuriteSociale; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getMutuelle() { return mutuelle; }
    public void setMutuelle(String mutuelle) { this.mutuelle = mutuelle; }

    public LocalDateTime getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(LocalDateTime dateArrivee) { this.dateArrivee = dateArrivee; }

    public double getTension() { return tension; }
    public void setTension(double tension) { this.tension = tension; }

    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }

    public int getFrequenceCardiaque() { return frequenceCardiaque; }
    public void setFrequenceCardiaque(int frequenceCardiaque) { this.frequenceCardiaque = frequenceCardiaque; }

    public int getFrequenceRespiratoire() { return frequenceRespiratoire; }
    public void setFrequenceRespiratoire(int frequenceRespiratoire) { this.frequenceRespiratoire = frequenceRespiratoire; }

    public double getPoids() { return poids; }
    public void setPoids(double poids) { this.poids = poids; }

    public double getTaille() { return taille; }
    public void setTaille(double taille) { this.taille = taille; }
}
