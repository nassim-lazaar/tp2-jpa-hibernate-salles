package com.example.jpa.dao;

import com.example.jpa.entities.Utilisateur;

import javax.persistence.EntityManager;
import java.util.List;

public class UtilisateurDao extends GenericDao<Utilisateur> {

    public UtilisateurDao(EntityManager em) {
        super(Utilisateur.class, em);
    }

    public List<Utilisateur> findBySalleNom(String nomDeSalle) {
        return em.createQuery("SELECT u FROM Utilisateur u WHERE u.salle.nom = :nomDeSalle", Utilisateur.class)
                .setParameter("nomDeSalle", nomDeSalle)
                .getResultList();
    }
}
