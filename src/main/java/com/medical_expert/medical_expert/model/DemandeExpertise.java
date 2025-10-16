package com.medical_expert.medical_expert.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "demandes_expertise")
public class DemandeExpertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @ManyToOne
    @JoinColumn(name = "specialiste_id")
    private Specialiste specialiste;

    @ManyToOne
    @JoinColumn(name = "creneau_id")
    private Creneau creneau; // Créneau réservé pour cette demande

    private String question;
    private String priorite; // URGENTE / NORMALE / NON_URGENTE
    private String statut = "EN_ATTENTE";
    private LocalDateTime dateDemande = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String reponse;

    @Column(columnDefinition = "TEXT")
    private String recommandations;

    public DemandeExpertise() {}

    // --- Getters & Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Consultation getConsultation() { return consultation; }
    public void setConsultation(Consultation consultation) { this.consultation = consultation; }

    public Specialiste getSpecialiste() { return specialiste; }
    public void setSpecialiste(Specialiste specialiste) { this.specialiste = specialiste; }

    public Creneau getCreneau() { return creneau; }
    public void setCreneau(Creneau creneau) { this.creneau = creneau; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getPriorite() { return priorite; }
    public void setPriorite(String priorite) { this.priorite = priorite; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public LocalDateTime getDateDemande() { return dateDemande; }
    public void setDateDemande(LocalDateTime dateDemande) { this.dateDemande = dateDemande; }

    public String getReponse() { return reponse; }
    public void setReponse(String reponse) { this.reponse = reponse; }

    public String getRecommandations() { return recommandations; }
    public void setRecommandations(String recommandations) { this.recommandations = recommandations; }

    @Override
    public String toString() {
        return "DemandeExpertise{" +
                "id=" + id +
                ", consultation=" + (consultation != null ? consultation.getId() : null) +
                ", specialiste=" + (specialiste != null ? specialiste.getId() : null) +
                ", creneau=" + (creneau != null ? creneau.getId() : null) +
                ", question='" + question + '\'' +
                ", priorite='" + priorite + '\'' +
                ", statut='" + statut + '\'' +
                ", dateDemande=" + dateDemande +
                '}';
    }
}
