package com.loadbalancerproject.loadbalancer;

import javax.persistence.EntityManager;

public class TransactionalEntityManager {

    EntityManager entityManager;

    public TransactionalEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void persist(Object object) {
        entityManager.getTransaction().begin();
        entityManager.persist(object);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void remove(Object object) {
        entityManager.getTransaction().begin();
        entityManager.remove(entityManager.merge(object));
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
