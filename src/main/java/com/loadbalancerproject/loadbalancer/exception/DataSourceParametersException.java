package com.loadbalancerproject.loadbalancer;

public class DataSourceParametersException extends Throwable {
    public DataSourceParametersException(Exception e){
        super(e);
    }
    public DataSourceParametersException(Exception e, String mess){
        super(mess,e);
    }
    public DataSourceParametersException(){
        super("DataSourceParameters are null");
    }
}
