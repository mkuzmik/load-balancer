package com.loadbalancerproject.loadbalancer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.Collection;

public class LoadBalancerImpl implements LoadBalancer {

    Collection<EntityManagerFactory> entityManagerFactories = new ArrayList<>();

    public LoadBalancerImpl() {
    }

    public LoadBalancerImpl(Collection<EntityManagerFactory> entityManagerFactories) {
        this.entityManagerFactories = entityManagerFactories;
    }

    public void addEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        entityManagerFactories.add(entityManagerFactory);
    }

    public void save(Object obj, Class clazz) {
        entityManagerFactories.stream()
                .map(EntityManagerFactory::createEntityManager)
                .map(TransactionalEntityManager::new)
                .forEach(transactionalEntityManager -> transactionalEntityManager.persist(obj));
    }

    public void delete(Object obj, Class clazz) {
        entityManagerFactories.stream()
                .map(EntityManagerFactory::createEntityManager)
                .map(TransactionalEntityManager::new)
                .forEach(transactionalEntityManager -> transactionalEntityManager.remove(obj));
    }

    public void update(Object obj, Class clazz) {

    }

    @Override
    public EntityManager getEntityManager() {
        // should be improved (choosing proper emf, not "findFirst")
        return entityManagerFactories.stream().findFirst().get().createEntityManager();
    }
}
