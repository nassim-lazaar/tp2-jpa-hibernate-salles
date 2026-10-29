package com.example.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "salles")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private int capacite;

    @Column(nullable = false)
    private boolean disponible = true;

    public Salle() {}

    public Salle(String nomSalle, int capaciteAccueil) {
        this.nom = nomSalle;
        this.capacite = capaciteAccueil;
        this.disponible = true;
    }

    public Salle(String nomSalle, int capaciteAccueil, boolean estDisponible) {
        this.nom = nomSalle;
        this.capacite = capaciteAccueil;
        this.disponible = estDisponible;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idSalle) {
        this.id = idSalle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nomSalle) {
        this.nom = nomSalle;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capaciteAccueil) {
        this.capacite = capaciteAccueil;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean estDisponible) {
        this.disponible = estDisponible;
    }

    @Override
    public String toString() {
        return "Salle [id=" + id + ", nom=" + nom + ", capacite=" + capacite + ", disponible=" + disponible + "]";
    }
}
