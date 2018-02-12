package com.loadbalancerproject.loadbalancer.loadbalancing;

import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.logging.Logger;

public class EqualDistributionStrategy implements LoadBalancingStrategy {

    LoadCache loadCache;

    public EqualDistributionStrategy() {
        loadCache = LoadCache.getInstance();
    }

    /*test only constructor*/
    EqualDistributionStrategy(LoadCache loadCache) {
        this.loadCache = loadCache;
    }

    @Override
    public synchronized EntityManagerFactory chooseEntityManagerFactory(Collection<EntityManagerFactory> entityManagerFactories) {
        Integer minLoad = entityManagerFactories.stream().map(emf -> loadCache.getLoad(emf)).min(Integer::compareTo).orElse(0);
        EntityManagerFactory entityManagerFactory = null;

        for (EntityManagerFactory emf : entityManagerFactories) {
            if (loadCache.getLoad(emf).equals(minLoad) || entityManagerFactory == null) {
                entityManagerFactory = emf;
            }
        }

        return entityManagerFactory;
    }
}
