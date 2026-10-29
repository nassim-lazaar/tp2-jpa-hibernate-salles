package com.example.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "salle_id")
    private Salle salle;

    public Utilisateur() {}

    public Utilisateur(String nomComplet, String adresseEmail) {
        this.nom = nomComplet;
        this.email = adresseEmail;
    }

    public Utilisateur(String nomComplet, String adresseEmail, Salle salleAttribuee) {
        this.nom = nomComplet;
        this.email = adresseEmail;
        this.salle = salleAttribuee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idUtilisateur) {
        this.id = idUtilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nomComplet) {
        this.nom = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String adresseEmail) {
        this.email = adresseEmail;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salleAttribuee) {
        this.salle = salleAttribuee;
    }

    @Override
    public String toString() {
        return "Utilisateur [id=" + id + ", nom=" + nom + ", email=" + email +
                ", salle=" + (salle != null ? salle.getNom() : "aucune") + "]";
    }
}
