package com.example.jpa.service;

import com.example.jpa.dao.SalleDao;
import com.example.jpa.dao.UtilisateurDao;
import com.example.jpa.entities.Salle;
import com.example.jpa.entities.Utilisateur;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class ServiceGestion {

    private final EntityManagerFactory emf;
    private final EntityManager em;
    private final SalleDao salleRepository;
    private final UtilisateurDao utilisateurRepository;

    public ServiceGestion() {
        this("gestionPU");
    }

    public ServiceGestion(String nomUnitePersistance) {
        this.emf = Persistence.createEntityManagerFactory(nomUnitePersistance);
        this.em = emf.createEntityManager();
        this.salleRepository = new SalleDao(em);
        this.utilisateurRepository = new UtilisateurDao(em);
    }

    public void fermer() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public void nettoyerBase() {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM Utilisateur").executeUpdate();
            em.createQuery("DELETE FROM Salle").executeUpdate();
            tx.commit();
        } catch (RuntimeException ex) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        }
    }

    // Gestion des Salles
    public void ajouterSalle(Salle nouvelleSalle) {
        salleRepository.create(nouvelleSalle);
    }

    public Salle modifierSalle(Salle salleAModifier) {
        return salleRepository.update(salleAModifier);
    }

    public void supprimerSalle(Salle salleASupprimer) {
        salleRepository.delete(salleASupprimer);
    }

    public void supprimerSalleParId(Long idSalle) {
        salleRepository.deleteById(idSalle);
    }

    public Optional<Salle> trouverSalleParId(Long idSalle) {
        return salleRepository.findById(idSalle);
    }

    public List<Salle> listerSalles() {
        return salleRepository.findAll();
    }

    public List<Salle> rechercherSallesParCapacite(int capaciteSeuil) {
        return salleRepository.findByCapaciteMin(capaciteSeuil);
    }

    public List<Salle> rechercherSallesDisponibles(boolean estDispo) {
        return salleRepository.findByDisponible(estDispo);
    }

    // Gestion des Utilisateurs
    public void ajouterUtilisateur(Utilisateur nouvelUtilisateur) {
        utilisateurRepository.create(nouvelUtilisateur);
    }

    public Utilisateur modifierUtilisateur(Utilisateur userAModifier) {
        return utilisateurRepository.update(userAModifier);
    }

    public void supprimerUtilisateur(Utilisateur userASupprimer) {
        utilisateurRepository.delete(userASupprimer);
    }

    public void supprimerUtilisateurParId(Long idUser) {
        utilisateurRepository.deleteById(idUser);
    }

    public Optional<Utilisateur> trouverUtilisateurParId(Long idUser) {
        return utilisateurRepository.findById(idUser);
    }

    public List<Utilisateur> listerUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public List<Utilisateur> utilisateursParSalle(String libelleSalle) {
        return utilisateurRepository.findBySalleNom(libelleSalle);
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
