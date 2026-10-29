package com.example.jpa;

import com.example.jpa.entities.Salle;
import com.example.jpa.entities.Utilisateur;
import com.example.jpa.service.ServiceGestion;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("============================================================");
        System.out.println("   TP 2 : Gestion des Salles et Utilisateurs (JPA/Hibernate) ");
        System.out.println("============================================================");

        ServiceGestion gestionnaire = new ServiceGestion();

        try {
            System.out.println("\n[1] Création et insertion des Salles...");
            Salle salleA = new Salle("Salle B204 - Algorithmique", 40, true);
            Salle salleB = new Salle("Amphithéâtre Al-Khwarizmi", 200, true);
            Salle salleC = new Salle("Laboratoire IA & Réseaux", 25, false);

            gestionnaire.ajouterSalle(salleA);
            gestionnaire.ajouterSalle(salleB);
            gestionnaire.ajouterSalle(salleC);
            System.out.println("-> Salles enregistrées avec succès.");

            System.out.println("\n[2] Création et affectation des Utilisateurs...");
            Utilisateur user1 = new Utilisateur("Nassim Lazaar", "n.lazaar@ump.ac.ma", salleA);
            Utilisateur user2 = new Utilisateur("Yassine Mansour", "y.mansour@ump.ac.ma", salleA);
            Utilisateur user3 = new Utilisateur("Siham Berrada", "s.berrada@ump.ac.ma", salleB);

            gestionnaire.ajouterUtilisateur(user1);
            gestionnaire.ajouterUtilisateur(user2);
            gestionnaire.ajouterUtilisateur(user3);
            System.out.println("-> Utilisateurs affectés aux salles.");

            System.out.println("\n[3] Répertoire des Salles :");
            for (Salle s : gestionnaire.listerSalles()) {
                System.out.println("   - " + s);
            }

            System.out.println("\n[4] Registre des Utilisateurs :");
            for (Utilisateur u : gestionnaire.listerUtilisateurs()) {
                System.out.println("   - " + u);
            }

            System.out.println("\n[5] Utilisateurs présents dans 'Salle B204 - Algorithmique' :");
            List<Utilisateur> membresSalleA = gestionnaire.utilisateursParSalle("Salle B204 - Algorithmique");
            for (Utilisateur u : membresSalleA) {
                System.out.println("   - " + u.getNom() + " <" + u.getEmail() + ">");
            }

            System.out.println("\n[6] Salles d'une capacité minimale de 30 places :");
            List<Salle> sallesFiltrees = gestionnaire.rechercherSallesParCapacite(30);
            for (Salle s : sallesFiltrees) {
                System.out.println("   - " + s.getNom() + " (Capacité : " + s.getCapacite() + ")");
            }

            System.out.println("\n[7] Salles actuellement disponibles :");
            List<Salle> sallesDispos = gestionnaire.rechercherSallesDisponibles(true);
            for (Salle s : sallesDispos) {
                System.out.println("   - " + s.getNom());
            }

            System.out.println("\n============================================================");
            System.out.println("   Exécution du service terminée avec succès !");
            System.out.println("============================================================");
        } finally {
            gestionnaire.fermer();
        }
    }
}
