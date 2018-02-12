package com.loadbalancerproject.loadbalancer.loadbalancing;

import javax.persistence.EntityManagerFactory;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class LoadCache {

    private static Logger LOGGER = Logger.getLogger(LoadCache.class.getName());

    private static LoadCache instance;

    public static LoadCache getInstance() {
        if (instance == null) {
            synchronized (LoadCache.class) {
                instance = new LoadCache();
            }
        }

        return instance;
    }

    private Map<EntityManagerFactory, Integer> load;

    private LoadCache() {
        load = new Hashtable<>();
    }

    public synchronized void load(EntityManagerFactory entityManagerFactory) {
        load.put(entityManagerFactory, getLoad(entityManagerFactory) + 1);
        LOGGER.info(entityManagerFactory + " load increased to " + getLoad(entityManagerFactory));
    }

    public synchronized void release(EntityManagerFactory entityManagerFactory) {
        int counter = getLoad(entityManagerFactory) - 1;
        counter = counter > 0 ? counter : 0;

        load.put(entityManagerFactory, counter);
        LOGGER.info(entityManagerFactory + " load decreased to " + getLoad(entityManagerFactory));
    }

    public Integer getLoad(EntityManagerFactory entityManagerFactory) {
        return Optional.ofNullable(load.get(entityManagerFactory)).orElse(0);
    }
}