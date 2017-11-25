package com.loadbalancerproject.loadbalancer.config;


import javax.sql.DataSource;
import java.util.*;

public class DBConfig {

   private Set<DataSource> dataSourcesList ;

   public DBConfig(DBConfigBuilder dbConfigBuilder) {
      this.dataSourcesList = dbConfigBuilder.dataSourcesList;
   }

   public Set<DataSource> getDataSourcesList(){
      return dataSourcesList;
   }

   public static class DBConfigBuilder{
      public Set<DataSource> dataSourcesList = new HashSet<DataSource>();

      public DBConfigBuilder dataSourceList(Collection<DataSource> dataSourceList){
         this.dataSourcesList.addAll(dataSourceList);
         return this;
      }

      public DBConfig build(){
         return new DBConfig(this);
      }


   }


}
