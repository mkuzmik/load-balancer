package com.loadbalancerproject.loadbalancer.factory;


import com.loadbalancerproject.loadbalancer.prototype.DataSourcePrototype;
import com.loadbalancerproject.loadbalancer.exception.DataSourceParametersException;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Iterator;


public class DataSourceFactory {
    private static final HashSet<DataSourcePrototype> DATA_SOURCE_WRAPPERS_HASH_SET = new HashSet<>();
    public static DataSource createDataSource(DataSourcePrototype dataSourcePrototype) throws DataSourceParametersException {
        if(DATA_SOURCE_WRAPPERS_HASH_SET.contains(dataSourcePrototype)){
            Iterator<DataSourcePrototype> iterator = DATA_SOURCE_WRAPPERS_HASH_SET.iterator();
            while(iterator.hasNext()){
                DataSourcePrototype tmp = iterator.next();
                if(tmp.equals(dataSourcePrototype)) return tmp.toDataSource();
            }
        }
        DATA_SOURCE_WRAPPERS_HASH_SET.add(dataSourcePrototype);
        return dataSourcePrototype.toDataSource();
    }
}
