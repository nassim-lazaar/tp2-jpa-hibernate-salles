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

        ServiceGestion service = new ServiceGestion();

        try {
            System.out.println("\n[1] Création et insertion des Salles...");
            Salle salleA = new Salle("Salle Java", 30, true);
            Salle salleB = new Salle("Amphi Turing", 150, true);
            Salle salleC = new Salle("Lab IA", 25, false);

            service.ajouterSalle(salleA);
            service.ajouterSalle(salleB);
            service.ajouterSalle(salleC);
            System.out.println("-> 3 salles créées avec succès.");

            System.out.println("\n[2] Création et affectation des Utilisateurs...");
            Utilisateur u1 = new Utilisateur("Nassim Lazaar", "nassim@example.com", salleA);
            Utilisateur u2 = new Utilisateur("Sara Alami", "sara@example.com", salleA);
            Utilisateur u3 = new Utilisateur("Karim Idrissi", "karim@example.com", salleB);

            service.ajouterUtilisateur(u1);
            service.ajouterUtilisateur(u2);
            service.ajouterUtilisateur(u3);
            System.out.println("-> 3 utilisateurs créés et associés à des salles.");

            System.out.println("\n[3] Liste de toutes les Salles :");
            for (Salle s : service.listerSalles()) {
                System.out.println("   - " + s);
            }

            System.out.println("\n[4] Liste de tous les Utilisateurs :");
            for (Utilisateur u : service.listerUtilisateurs()) {
                System.out.println("   - " + u);
            }

            System.out.println("\n[5] Recherche des Utilisateurs dans 'Salle Java' :");
            List<Utilisateur> usersJava = service.utilisateursParSalle("Salle Java");
            for (Utilisateur u : usersJava) {
                System.out.println("   - " + u.getNom() + " (" + u.getEmail() + ")");
            }

            System.out.println("\n[6] Recherche des Salles avec capacité >= 30 :");
            List<Salle> sallesGrandes = service.rechercherSallesParCapacite(30);
            for (Salle s : sallesGrandes) {
                System.out.println("   - " + s.getNom() + " (Capacité: " + s.getCapacite() + ")");
            }

            System.out.println("\n[7] Recherche des Salles disponibles :");
            List<Salle> sallesDispo = service.rechercherSallesDisponibles(true);
            for (Salle s : sallesDispo) {
                System.out.println("   - " + s.getNom());
            }

            System.out.println("\n============================================================");
            System.out.println("   Exécution terminée avec succès !");
            System.out.println("============================================================");
        } finally {
            service.fermer();
        }
    }
}
