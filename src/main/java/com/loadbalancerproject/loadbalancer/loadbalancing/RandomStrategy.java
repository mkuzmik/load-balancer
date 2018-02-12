package com.loadbalancerproject.loadbalancer.loadbalancing;

import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.Random;

public class RandomStrategy implements LoadBalancingStrategy {

    @Override
    public EntityManagerFactory getEntityManager(Collection<EntityManagerFactory> entityManagerFactories) {
        Random random = new Random();

        EntityManagerFactory emf = null;

        int index = random.nextInt(entityManagerFactories.size());

        for(EntityManagerFactory item : entityManagerFactories) {
            emf = item;

            if (index == 0) {
                return emf;
            }

            index--;
        }

        return emf;
    }
}
