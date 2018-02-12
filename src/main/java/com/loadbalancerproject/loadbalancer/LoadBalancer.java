package com.loadbalancerproject.loadbalancer;

import com.loadbalancerproject.loadbalancer.loadbalancing.LoadBalancingStrategy;
import com.loadbalancerproject.loadbalancer.readonlyqueryexecutor.SelectQuery;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public interface LoadBalancer {

    void save(Object obj, Class clazz);

    void delete(Object obj, Class clazz);

    void update(Object obj, Class clazz);

    void setLoadBalancingStrategy(LoadBalancingStrategy strategy);

    SelectQuery getSelectQuery();
}
