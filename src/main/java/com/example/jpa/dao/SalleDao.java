package com.example.jpa.dao;

import com.example.jpa.entities.Salle;

import javax.persistence.EntityManager;
import java.util.List;

public class SalleDao extends GenericDao<Salle> {

    public SalleDao(EntityManager em) {
        super(Salle.class, em);
    }

    public List<Salle> findByCapaciteMin(int placesMin) {
        return em.createQuery("SELECT s FROM Salle s WHERE s.capacite >= :placesMin", Salle.class)
                .setParameter("placesMin", placesMin)
                .getResultList();
    }

    public List<Salle> findByCapaciteMinimum(int placesMin) {
        return findByCapaciteMin(placesMin);
    }

    public List<Salle> findByDisponible(boolean statutDisponibilite) {
        return em.createQuery("SELECT s FROM Salle s WHERE s.disponible = :statutDisponibilite", Salle.class)
                .setParameter("statutDisponibilite", statutDisponibilite)
                .getResultList();
    }
}
