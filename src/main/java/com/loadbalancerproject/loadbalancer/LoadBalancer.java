package com.loadbalancerproject.loadbalancer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public interface LoadBalancer {

    void addEntityManagerFactory(EntityManagerFactory emf);

    void save(Object obj, Class clazz);

    void delete(Object obj, Class clazz);

    void update(Object obj, Class clazz);

    EntityManager getEntityManager();
}
