package com.loadbalancerproject.loadbalancer;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;


public class DataSourcePrototype {

    private Map<String, String>  dataSourceParamiters = new LinkedHashMap<>();

    public Map<String, String> getDataSourceParamiters() {
        return dataSourceParamiters;
    }
    private BasicDataSource dataSource = new BasicDataSource();


    private DataSourcePrototype(DataSourceBuilder builder){
        dataSourceParamiters.put("driverClassName", builder.driverClassName);
        dataSourceParamiters.put("url", builder.url);
        dataSourceParamiters.put("user",builder.user);
        dataSourceParamiters.put("pass",builder.pass);

    }
    private boolean areDataSourceParamitersNotNull() {
        return !(dataSourceParamiters.containsValue(null));
    }
    public boolean equals(DataSourcePrototype dataSourcePrototype){
        if(dataSourcePrototype == null) return false;
        if(dataSourcePrototype instanceof DataSourcePrototype)
            return  (dataSourceParamiters.get("driverClassName").equals(dataSourcePrototype.getDataSourceParamiters().get("driverCLassName")) &&
                    dataSourceParamiters.get("url").equals(dataSourcePrototype.getDataSourceParamiters().get("url")) &&
                    dataSourceParamiters.get("user").equals(dataSourcePrototype.getDataSourceParamiters().get("user")) &&
                    dataSourceParamiters.get("pass").equals(dataSourcePrototype.getDataSourceParamiters().get("pass")));
        return false;

    }
    public DataSource toDataSource() throws DataSourceParametersException {
        if(areDataSourceParamitersNotNull()) {
            dataSource.setDriverClassName(dataSourceParamiters.get("driverClassName"));
            dataSource.setUrl(dataSourceParamiters.get("url"));
            dataSource.setUsername(dataSourceParamiters.get("user"));
            dataSource.setPassword(dataSourceParamiters.get("pass"));

            return dataSource;
        }
        throw new DataSourceParametersException();
    }
   public static class DataSourceBuilder{
       public String driverClassName = "org.postgresql.Driver";
       public String url;
       public String user = "postgres";
       public String pass = "";

       public DataSourceBuilder driverClassName(String driverClassName){
           this.driverClassName = driverClassName;
           return this;
       }

       public DataSourceBuilder url(String url){
            this.url = url;
            return this;
       }

       public DataSourceBuilder user(String user){
           this.user = user;
           return this;
       }
       public DataSourceBuilder pass(String pass){
           this.pass = pass;
           return this;
       }

       public DataSourcePrototype build(){
           return new DataSourcePrototype(this);
       }
    }
}
