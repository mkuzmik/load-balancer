package com.loadbalancerproject.loadbalancer.loadbalancing;

import javax.persistence.EntityManagerFactory;
import java.util.Collection;

public interface LoadBalancingStrategy {

    EntityManagerFactory chooseEntityManagerFactory(Collection<EntityManagerFactory> entityManagerFactories);
}
