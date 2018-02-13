# Load Balancer

## Overview

This is Load Balancer library.

## How to run

Requirements:
- Java 8
- Maven

To build this project, just type in terminal:
```
mvn clean install
```

## How to configure Load Balancer using Spring Framework

**1**. Create persistence.xml file in your \resources\META-INF directory. Next you have to copy below configuration into this file. 

```
<persistence xmlns="http://java.sun.com/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd"
             version="2.0">
    <persistence-unit name="manager1" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.ejb.HibernatePersistence</provider>
        <properties>
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
            <property name="hibernate.hbm2ddl.auto" value="create-drop"/>
            <property name="hibernate.show_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

**2**.Create Spring configuration class PersistanceConfig in your config directory </br>
**3**.Because Spring requires one DataSource specified as Bean, you should create it but it is unnecessery to our library </br>
**4**.To achieve successful LoadBalancer configuration you need to use LoadBalancerDataSource, DatabaseConfiguration,  LoadBalancingStrategy classes and create LoadBalancer class bean.  </br> </br>
**Class description:** </br>
**LoadBalancerDataSource** class is used to specify database connection details. You must pass driverClassName, url, username, password. By default driverClassName="org.postgresql.Driver", user= "postgres", pass = "", so if you have database with these parameters you need to give just url. </br>
**DatabaseConfiguration** class is used to specify whole database settings. That includes collection of LoadBalancerDataSources and LoadBalancingStrategy. It has to be passed as an argument in LoadBalancer constructor  </br>
**LoadBalancingStrategy** class is used to specify concrete load balancing strategy. There are two available strategies: EqualDistributionStrategy and RandomStrategy.</br>

**Configuration example:**
```

@Configuration
@EnableTransactionManagement
public class PersistenceConfig {

    //Simple DataSource is created because of Spring restrictions
    @Bean
    public DataSource primaryDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://192.168.99.100:5432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("");

        return dataSource;
    }

    @Bean
    public DatabaseConfiguration prepareDatabaseConfiguration(){
        DatabaseConfiguration config = new DatabaseConfiguration.ConfigurationBuilder()
                .dataSources(createLoadBalancerDataSourceList())
                .strategy(new EqualDistributionStrategy())
                .build();
        return config;
    }

    @Bean
    public LoadBalancer getLoadBalancer(){

        LoadBalancer loadBalancer = new LoadBalancerImpl(prepareDatabaseConfiguration());
        return loadBalancer;
    }

    private List<LoadBalancerDataSource> createLoadBalancerDataSourceList() {
        List<LoadBalancerDataSource> list = new LinkedList<>();
        list.add(new LoadBalancerDataSource.DataSourceBuilder()
                .url("jdbc:postgresql://192.168.99.100:5432/postgres")
                .build());
        list.add(new LoadBalancerDataSource.DataSourceBuilder()
                .url("jdbc:postgresql://192.168.99.100:5433/postgres")
                .build());
        list.add(new LoadBalancerDataSource.DataSourceBuilder()
                .url("jdbc:postgresql://192.168.99.100:5434/postgres")
                .build());
        list.add(new LoadBalancerDataSource.DataSourceBuilder()
                .url("jdbc:postgresql://192.168.99.100:5435/postgres")
                .build());
        list.add(new LoadBalancerDataSource.DataSourceBuilder()
                .url("jdbc:postgresql://192.168.99.100:5436/postgres")
                .build());
        list.add(new LoadBalancerDataSource.DataSourceBuilder()
                .url("jdbc:postgresql://192.168.99.100:5437/postgres")
                .build());
        list.add(new LoadBalancerDataSource.DataSourceBuilder()
                .url("jdbc:postgresql://192.168.99.100:5438/postgres")
                .build());
        list.add(new LoadBalancerDataSource.DataSourceBuilder()
                .url("jdbc:postgresql://192.168.99.100:5439/postgres")
                .build());
        list.add(new LoadBalancerDataSource.DataSourceBuilder()
                .url("jdbc:postgresql://192.168.99.100:5440/postgres")
                .build());
        list.add(new LoadBalancerDataSource.DataSourceBuilder()
                .url("jdbc:postgresql://192.168.99.100:5441/postgres")
                .build());
        return list;
    }
}
```



## Authors
- Mateusz Kuźmik
- Jakub Kacorzyk
- Bartłomiej Łazarczyk
- Szymon Jakóbczyk
