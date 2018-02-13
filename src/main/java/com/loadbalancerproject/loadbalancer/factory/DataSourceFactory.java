package com.loadbalancerproject.loadbalancer.factory;


import com.loadbalancerproject.loadbalancer.config.LoadBalancerDataSource;
import com.loadbalancerproject.loadbalancer.exception.DataSourceParametersException;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Iterator;


public class DataSourceFactory {
    private static final HashSet<LoadBalancerDataSource> DATA_SOURCE_WRAPPERS_HASH_SET = new HashSet<>();
    public static DataSource createDataSource(LoadBalancerDataSource loadBalancerDataSource) throws DataSourceParametersException {
            if (DATA_SOURCE_WRAPPERS_HASH_SET.contains(loadBalancerDataSource)) {
                Iterator<LoadBalancerDataSource> iterator = DATA_SOURCE_WRAPPERS_HASH_SET.iterator();
                while (iterator.hasNext()) {
                    LoadBalancerDataSource tmp = iterator.next();
                    if (tmp.equals(loadBalancerDataSource)) return tmp.toDataSource();
                }
            }
            DATA_SOURCE_WRAPPERS_HASH_SET.add(loadBalancerDataSource);
            return loadBalancerDataSource.toDataSource();
    }
}
