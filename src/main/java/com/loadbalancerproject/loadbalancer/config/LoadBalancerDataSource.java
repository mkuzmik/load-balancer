package com.loadbalancerproject.loadbalancer.config;

import com.loadbalancerproject.loadbalancer.exception.DataSourceParametersException;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;


public class LoadBalancerDataSource {

    private Map<String, String> dataSourceParameters = new LinkedHashMap<>();

    public Map<String, String> getDataSourceParameters() {
        return dataSourceParameters;
    }

    private BasicDataSource dataSource = new BasicDataSource();

    private LoadBalancerDataSource(DataSourceBuilder builder){
        dataSourceParameters.put("driverClassName", builder.driverClassName);
        dataSourceParameters.put("url", builder.url);
        dataSourceParameters.put("user",builder.user);
        dataSourceParameters.put("pass",builder.pass);

    }

    private boolean areDataSourceParametersNotNull() {
        return !(dataSourceParameters.containsValue(null));
    }

    @Override
    public boolean equals(Object loadBalancerDataSource){
        if (this == loadBalancerDataSource)
            return true;
        if(loadBalancerDataSource == null) return false;
        if (getClass() != loadBalancerDataSource.getClass())
            return false;
        LoadBalancerDataSource dataSourceData = (LoadBalancerDataSource) loadBalancerDataSource;
        return (dataSourceParameters.get("driverClassName").equals(dataSourceData.getDataSourceParameters().get("driverClassName")) &&
                dataSourceParameters.get("url").equals(dataSourceData.getDataSourceParameters().get("url")) &&
                dataSourceParameters.get("user").equals(dataSourceData.getDataSourceParameters().get("user")) &&
                dataSourceParameters.get("pass").equals(dataSourceData.getDataSourceParameters().get("pass")));

    }

    public DataSource toDataSource() throws DataSourceParametersException {
        if(areDataSourceParametersNotNull()) {
            dataSource.setDriverClassName(dataSourceParameters.get("driverClassName"));
            dataSource.setUrl(dataSourceParameters.get("url"));
            dataSource.setUsername(dataSourceParameters.get("user"));
            dataSource.setPassword(dataSourceParameters.get("pass"));

            return dataSource;
        }
        throw new DataSourceParametersException();
    }

    public static class DataSourceBuilder{
       public String driverClassName = "org.postgresql.Driver";
       public String url;
       public String user= "postgres";
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
       public DataSourceBuilder password(String password){
           this.pass = password;
           return this;
       }

       public LoadBalancerDataSource build(){
           return new LoadBalancerDataSource(this);
       }
    }
}
