package com.example.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class GenericDao<T> {

    private final Class<T> classeEntite;
    protected EntityManager em;

    public GenericDao(Class<T> classeEntite, EntityManager entityManager) {
        this.classeEntite = classeEntite;
        this.em = entityManager;
    }

    public void create(T entite) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(entite);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public T find(Object identifiant) {
        return em.find(classeEntite, identifiant);
    }

    public Optional<T> findById(Object identifiant) {
        return Optional.ofNullable(em.find(classeEntite, identifiant));
    }

    public T update(T entite) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T entiteFusionnee = em.merge(entite);
            transaction.commit();
            return entiteFusionnee;
        } catch (RuntimeException ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public void delete(T entite) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.remove(em.contains(entite) ? entite : em.merge(entite));
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        }
    }

    public void deleteById(Object identifiant) {
        findById(identifiant).ifPresent(this::delete);
    }

    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + classeEntite.getSimpleName() + " e", classeEntite)
                .getResultList();
    }
}
