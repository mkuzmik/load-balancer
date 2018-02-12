package com.loadbalancerproject.loadbalancer;

import com.loadbalancerproject.loadbalancer.config.DBConfig;
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

public class LoadBalancerImpl implements LoadBalancer {

    Collection<EntityManagerFactory> entityManagerFactories = new ArrayList<>();
    Collection<DataSource> dataSourceCollection = new ArrayList<>();


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


    public void addEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        entityManagerFactories.add(entityManagerFactory);
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

    }

    @Override
    public SelectQuery getSelectQuery() {
        return new EntityManagerAdapter(getEntityManager());
    }

    private EntityManager getEntityManager() {
        // should be improved (choosing proper emf, not "findFirst")
        return entityManagerFactories.stream().findFirst().get().createEntityManager();
    }
}
