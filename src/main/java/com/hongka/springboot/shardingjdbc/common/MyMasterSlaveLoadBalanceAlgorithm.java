package com.hongka.springboot.shardingjdbc.common;

import io.shardingsphere.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithm;

import java.util.List;

public class MyMasterSlaveLoadBalanceAlgorithm implements MasterSlaveLoadBalanceAlgorithm{

    @Override
    public String getDataSource(String name, String masterDataSourceName, List<String> slaveDataSourceNames) {
        return null;
    }
}
