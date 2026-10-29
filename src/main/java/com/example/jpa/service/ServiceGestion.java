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
    private final SalleDao salleDao;
    private final UtilisateurDao utilisateurDao;

    public ServiceGestion() {
        this("gestionPU");
    }

    public ServiceGestion(String persistenceUnitName) {
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        this.em = emf.createEntityManager();
        this.salleDao = new SalleDao(em);
        this.utilisateurDao = new UtilisateurDao(em);
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
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    // Gestion des Salles
    public void ajouterSalle(Salle salle) {
        salleDao.create(salle);
    }

    public Salle modifierSalle(Salle salle) {
        return salleDao.update(salle);
    }

    public void supprimerSalle(Salle salle) {
        salleDao.delete(salle);
    }

    public void supprimerSalleParId(Long id) {
        salleDao.deleteById(id);
    }

    public Optional<Salle> trouverSalleParId(Long id) {
        return salleDao.findById(id);
    }

    public List<Salle> listerSalles() {
        return salleDao.findAll();
    }

    public List<Salle> rechercherSallesParCapacite(int capaciteMin) {
        return salleDao.findByCapaciteMin(capaciteMin);
    }

    public List<Salle> rechercherSallesDisponibles(boolean disponible) {
        return salleDao.findByDisponible(disponible);
    }

    // Gestion des Utilisateurs
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateurDao.create(utilisateur);
    }

    public Utilisateur modifierUtilisateur(Utilisateur utilisateur) {
        return utilisateurDao.update(utilisateur);
    }

    public void supprimerUtilisateur(Utilisateur utilisateur) {
        utilisateurDao.delete(utilisateur);
    }

    public void supprimerUtilisateurParId(Long id) {
        utilisateurDao.deleteById(id);
    }

    public Optional<Utilisateur> trouverUtilisateurParId(Long id) {
        return utilisateurDao.findById(id);
    }

    public List<Utilisateur> listerUtilisateurs() {
        return utilisateurDao.findAll();
    }

    public List<Utilisateur> utilisateursParSalle(String nomSalle) {
        return utilisateurDao.findBySalleNom(nomSalle);
    }

    public EntityManager getEntityManager() {
        return em;
    }
}
