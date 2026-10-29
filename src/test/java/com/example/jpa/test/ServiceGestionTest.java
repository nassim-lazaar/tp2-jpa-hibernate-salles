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
        Salle salle = new Salle("Salle Test", 20);
        service.ajouterSalle(salle);

        assertNotNull("L'ID de la salle doit être généré", salle.getId());

        List<Salle> salles = service.listerSalles();
        assertFalse("La liste des salles ne doit pas être vide", salles.isEmpty());
        assertEquals("Salle Test", salles.get(0).getNom());
    }

    @Test
    public void testCreationUtilisateurAvecSalle() {
        Salle salle = new Salle("Salle B", 15);
        service.ajouterSalle(salle);

        Utilisateur utilisateur = new Utilisateur("Alice", "alice@example.com", salle);
        service.ajouterUtilisateur(utilisateur);

        assertNotNull("L'ID de l'utilisateur doit être généré", utilisateur.getId());

        List<Utilisateur> utilisateurs = service.listerUtilisateurs();
        assertFalse("La liste des utilisateurs ne doit pas être vide", utilisateurs.isEmpty());
        assertEquals("Alice", utilisateurs.get(0).getNom());
        assertNotNull("L'utilisateur doit avoir une salle associée", utilisateurs.get(0).getSalle());
        assertEquals("Salle B", utilisateurs.get(0).getSalle().getNom());
    }

    @Test
    public void testRechercheUtilisateursParSalle() {
        Salle salle = new Salle("Salle C", 30);
        service.ajouterSalle(salle);

        Utilisateur u1 = new Utilisateur("Bob", "bob@example.com", salle);
        Utilisateur u2 = new Utilisateur("Carol", "carol@example.com", salle);
        service.ajouterUtilisateur(u1);
        service.ajouterUtilisateur(u2);

        List<Utilisateur> utilisateurs = service.utilisateursParSalle("Salle C");
        assertEquals(2, utilisateurs.size());
    }

    @Test
    public void testRechercheSallesParCapacite() {
        service.ajouterSalle(new Salle("Petite Salle", 10));
        service.ajouterSalle(new Salle("Moyenne Salle", 25));
        service.ajouterSalle(new Salle("Grande Salle", 60));

        List<Salle> salles = service.rechercherSallesParCapacite(25);
        assertEquals(2, salles.size());
    }

    @Test
    public void testRechercheSallesDisponibles() {
        service.ajouterSalle(new Salle("Salle Dispo", 20, true));
        service.ajouterSalle(new Salle("Salle Occupée", 20, false));

        List<Salle> dispos = service.rechercherSallesDisponibles(true);
        assertEquals(1, dispos.size());
        assertEquals("Salle Dispo", dispos.get(0).getNom());
    }

    @Test
    public void testSuppressionSalle() {
        Salle s = new Salle("Salle Temporaire", 12);
        service.ajouterSalle(s);
        Long id = s.getId();

        assertTrue(service.trouverSalleParId(id).isPresent());
        service.supprimerSalle(s);
        assertFalse(service.trouverSalleParId(id).isPresent());
    }
}
