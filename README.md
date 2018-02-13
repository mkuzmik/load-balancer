# Load Balancer

## Overview

This project contains load balancing library for Java. It helps you to use many relational databases in your application.
Load Balancer replicates all data across all accessible data sources. When reading data, Load Balancer choose datasource using 
given strategy.

## How to run

Requirements:
- Java 8
- Maven

To build this project, just type in terminal:
```
mvn clean install
```

## Configuration

1. Configure `META-INF/persistence.xml` file in your application project directory. Example:
```xml
       <persistence-unit name="manager1" transaction-type="RESOURCE_LOCAL">
           <provider>org.hibernate.ejb.HibernatePersistence</provider>
           <properties>
               <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
               <property name="hibernate.hbm2ddl.auto" value="create-drop"/>
               <property name="hibernate.show_sql" value="true"/>
           </properties>
       </persistence-unit>
```

2...

## Authors
- Mateusz Kuźmik
- Jakub Kacorzyk
- Bartłomiej Łazarczyk
- Szymon Jakóbczyk
