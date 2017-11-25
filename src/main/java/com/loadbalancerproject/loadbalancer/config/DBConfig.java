package com.loadbalancerproject.loadbalancer.config;


import javax.sql.DataSource;
import java.util.LinkedList;
import java.util.List;

public class DBConfig {

   public List<DataSource> dataSourcesList = new LinkedList<DataSource>();

   public List<DataSource> getDataSourcesList(){
      return dataSourcesList;
   }

}
