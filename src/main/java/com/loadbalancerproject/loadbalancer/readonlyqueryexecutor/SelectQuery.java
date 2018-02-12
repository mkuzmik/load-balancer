package com.loadbalancerproject.loadbalancer.readonlyqueryexecutor;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public interface SelectQuery {

    CriteriaQuery getCriteriaQuery(Class clazz);

    TypedQuery getTypedQuery(CriteriaQuery criteriaQuery);

    void close();
}