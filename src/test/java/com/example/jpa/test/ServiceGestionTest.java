package com.example.jpa.test;

import com.example.jpa.entities.Salle;
import com.example.jpa.entities.Utilisateur;
import com.example.jpa.service.ServiceGestion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ServiceGestionTest {

    private ServiceGestion service;

    @Before
    public void setUp() {
        service = new ServiceGestion();
        service.nettoyerBase();
    }

    @After
    public void tearDown() {
        if (service != null) {
            try {
                service.nettoyerBase();
            } finally {
                service.fermer();
            }
        }
    }

    @Test
    public void testCreationEtRechercheSalle() {
        Salle salleTest = new Salle("Salle Conférence Alpha", 50);
        service.ajouterSalle(salleTest);

        assertNotNull("L'ID généré ne doit pas être null", salleTest.getId());

        List<Salle> listeSalles = service.listerSalles();
        assertFalse("La liste des salles ne doit pas être vide", listeSalles.isEmpty());
        assertEquals("Salle Conférence Alpha", listeSalles.get(0).getNom());
    }

    @Test
    public void testCreationUtilisateurAvecSalle() {
        Salle salleAssignee = new Salle("Salle TD 1", 30);
        service.ajouterSalle(salleAssignee);

        Utilisateur nouvelUtilisateur = new Utilisateur("Nassim", "nassim@test.ma", salleAssignee);
        service.ajouterUtilisateur(nouvelUtilisateur);

        assertNotNull("L'ID utilisateur doit être généré", nouvelUtilisateur.getId());

        List<Utilisateur> listeUtilisateurs = service.listerUtilisateurs();
        assertFalse("La liste des utilisateurs ne doit pas être vide", listeUtilisateurs.isEmpty());
        assertEquals("Nassim", listeUtilisateurs.get(0).getNom());
        assertNotNull("L'utilisateur doit posséder une salle", listeUtilisateurs.get(0).getSalle());
        assertEquals("Salle TD 1", listeUtilisateurs.get(0).getSalle().getNom());
    }

    @Test
    public void testRechercheUtilisateursParSalle() {
        Salle salleCommune = new Salle("Labo Informatique", 25);
        service.ajouterSalle(salleCommune);

        Utilisateur membre1 = new Utilisateur("Etudiant 1", "e1@test.ma", salleCommune);
        Utilisateur membre2 = new Utilisateur("Etudiant 2", "e2@test.ma", salleCommune);
        service.ajouterUtilisateur(membre1);
        service.ajouterUtilisateur(membre2);

        List<Utilisateur> membresTrouves = service.utilisateursParSalle("Labo Informatique");
        assertEquals(2, membresTrouves.size());
    }

    @Test
    public void testRechercheSallesParCapacite() {
        service.ajouterSalle(new Salle("Petite Salle", 10));
        service.ajouterSalle(new Salle("Salle Moyenne", 35));
        service.ajouterSalle(new Salle("Grand Amphi", 150));

        List<Salle> sallesFiltrees = service.rechercherSallesParCapacite(35);
        assertEquals(2, sallesFiltrees.size());
    }

    @Test
    public void testRechercheSallesDisponibles() {
        service.ajouterSalle(new Salle("Salle Libre", 20, true));
        service.ajouterSalle(new Salle("Salle Reservee", 20, false));

        List<Salle> sallesLibres = service.rechercherSallesDisponibles(true);
        assertEquals(1, sallesLibres.size());
        assertEquals("Salle Libre", sallesLibres.get(0).getNom());
    }

    @Test
    public void testSuppressionSalle() {
        Salle salleEphemere = new Salle("Salle Temporaire", 15);
        service.ajouterSalle(salleEphemere);
        Long idSalle = salleEphemere.getId();

        assertTrue(service.trouverSalleParId(idSalle).isPresent());
        service.supprimerSalle(salleEphemere);
        assertFalse(service.trouverSalleParId(idSalle).isPresent());
    }
}
