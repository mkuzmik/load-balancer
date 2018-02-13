package com.loadbalancerproject.loadbalancer.config;


import com.loadbalancerproject.loadbalancer.exception.DataSourceParametersException;
import com.loadbalancerproject.loadbalancer.factory.DataSourceFactory;
import com.loadbalancerproject.loadbalancer.loadbalancing.EqualDistributionStrategy;
import com.loadbalancerproject.loadbalancer.loadbalancing.LoadBalancingStrategy;

import javax.sql.DataSource;
import java.util.*;
import java.util.stream.Collectors;

public class DatabaseConfiguration {

   private Set<DataSource> dataSources;
   private LoadBalancingStrategy strategy;

   public DatabaseConfiguration(ConfigurationBuilder configurationBuilder) {
      this.dataSources = configurationBuilder.dataSources;
      this.strategy = configurationBuilder.strategy;
   }

   public Set<DataSource> getDataSources(){
      return dataSources;
   }

   public LoadBalancingStrategy getStrategy(){
       return strategy;
   }

   public static class ConfigurationBuilder {
       private Set<DataSource> dataSources = new HashSet<DataSource>();
       private LoadBalancingStrategy strategy = new EqualDistributionStrategy();

       public ConfigurationBuilder dataSources(Collection<LoadBalancerDataSource> dataSourcesCollecion){
         Set<DataSource> set = dataSourcesCollecion.parallelStream().map(
                 loadBalancerDataSource ->  {
                        try {
                            return DataSourceFactory.createDataSource(loadBalancerDataSource);
                        }catch (DataSourceParametersException e) {
                            e.printStackTrace();
                        return null;
                }
        }).collect(Collectors.toSet());
         this.dataSources.addAll(set);
         return this;
       }

       public ConfigurationBuilder strategy(LoadBalancingStrategy strategy) {
            this.strategy = strategy;
            return this;
       }

       public DatabaseConfiguration build(){
         return new DatabaseConfiguration(this);
      }


   }


}
