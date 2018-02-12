package com.loadbalancerproject.loadbalancer.readonlyqueryexecutor;

import com.loadbalancerproject.loadbalancer.loadbalancing.LoadCache;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public class EntityManagerAdapter implements SelectQuery {

    private EntityManager entityManager;

    private EntityManagerFactory factoryRef;

    public EntityManagerAdapter(EntityManager entityManager, EntityManagerFactory emf) {
        this.entityManager = entityManager;
        this.factoryRef = emf;
    }

    @Override
    public CriteriaQuery getCriteriaQuery(Class clazz) {
        return entityManager.getCriteriaBuilder().createQuery(clazz);
    }

    @Override
    public TypedQuery getTypedQuery(CriteriaQuery criteriaQuery) {
        return entityManager.createQuery(criteriaQuery);
    }

    @Override
    public void close() {
        LoadCache.getInstance().release(factoryRef);
        entityManager.close();
    }
}
