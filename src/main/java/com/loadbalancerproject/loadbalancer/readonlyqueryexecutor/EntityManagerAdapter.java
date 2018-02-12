package com.loadbalancerproject.loadbalancer.readonlyqueryexecutor;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public class EntityManagerAdapter implements SelectQuery {

    private EntityManager entityManager;

    public EntityManagerAdapter(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public CriteriaQuery getCriteriaQuery(Class clazz) {
        return entityManager.getCriteriaBuilder().createQuery(clazz);
    }

    @Override
    public TypedQuery getTypedQuery(CriteriaQuery criteriaQuery) {
        return entityManager.createQuery(criteriaQuery);
    }
}
