package com.loadbalancerproject.loadbalancer;

import com.loadbalancerproject.loadbalancer.config.DBConfig;
import com.loadbalancerproject.loadbalancer.loadbalancing.LoadBalancingStrategy;
import com.loadbalancerproject.loadbalancer.loadbalancing.RandomStrategy;
import com.loadbalancerproject.loadbalancer.readonlyqueryexecutor.EntityManagerAdapter;
import com.loadbalancerproject.loadbalancer.readonlyqueryexecutor.SelectQuery;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class LoadBalancerImpl implements LoadBalancer {

    private static Logger LOGGER = Logger.getLogger(LoadBalancerImpl.class.getName());

    Collection<EntityManagerFactory> entityManagerFactories = new ArrayList<>();

    Collection<DataSource> dataSourceCollection = new ArrayList<>();

    LoadBalancingStrategy strategy = new RandomStrategy();

    public LoadBalancerImpl(DBConfig configuration) {
        this.dataSourceCollection=configuration.getDataSourcesList();
        setupEntityManagers();
    }

    private void setupEntityManagers(){
        for(DataSource dataSource : dataSourceCollection){
            Map<String, Object> props = new HashMap<String, Object>();
            props.put("javax.persistence.nonJtaDataSource", dataSource);
            props.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
            //TODO dynamic persistanceUnitName, for now it is hardcode
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("manager1", props);
            entityManagerFactories.add(factory);
        }
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
        entityManagerFactories.stream()
                .map(EntityManagerFactory::createEntityManager)
                .map(TransactionalEntityManager::new)
                .forEach(transactionalEntityManager -> transactionalEntityManager.update(obj));
    }

    @Override
    public SelectQuery getSelectQuery() {
        return new EntityManagerAdapter(getEntityManager());
    }

    private EntityManager getEntityManager() {

        EntityManagerFactory entityManagerFactory = strategy.chooseEntityManagerFactory(entityManagerFactories);

        LOGGER.info("Retrieving data from " + entityManagerFactory.toString());

        return entityManagerFactory.createEntityManager();
    }
}
