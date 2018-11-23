package com.hongka.springboot.shardingjdbc;

import com.hongka.springboot.shardingjdbc.common.MyMasterSlaveLoadBalanceAlgorithm;
import io.shardingsphere.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithm;
import io.shardingsphere.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import io.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import io.shardingsphere.shardingjdbc.util.DataSourceUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@SpringBootApplication
@MapperScan({"com.hongka.springboot.shardingjdbc.mapper"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class ShardingJDBCApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingJDBCApplication.class, args);
    }

    //@Autowired
    //private MasterSlaveRuleConfiguration masterSlaveRuleConfig;
    /**
     * 配置读写分离数据源
     *
     * @return
     * @throws FileNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    @Bean("dataSource2")
    DataSource getMasterSlaveDataSource() throws SQLException, ReflectiveOperationException {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration();
        masterSlaveRuleConfig.setName("ds_master_slave");
        masterSlaveRuleConfig.setMasterDataSourceName("ds_master");
        masterSlaveRuleConfig.setSlaveDataSourceNames(Arrays.asList("ds_slave0","ds_slave1"));
        //MasterSlaveLoadBalanceAlgorithmType.ROUND_ROBIN;
//        MasterSlaveLoadBalanceAlgorithm b1=new MyMasterSlaveLoadBalanceAlgorithm();
//        masterSlaveRuleConfig.setLoadBalanceAlgorithm(b1);
        return MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(), masterSlaveRuleConfig, new LinkedHashMap<String, Object>(), new Properties());
    }

    Map<String, DataSource> createDataSourceMap() throws ReflectiveOperationException {
        Map<String, DataSource> result = new HashMap<>();
        String params="?autoReconnect=true&useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&zeroDateTimeBehavior=convertToNull&useSSL=false";
        String className = "com.alibaba.druid.pool.DruidDataSource";
        Map<String, Object> map = new HashMap<>();
        map.put("url", "jdbc:mysql://localhost:3306/demo_ds_master"+params);
        map.put("driver-class-name","com.mysql.jdbc.Driver");
        map.put("username", "root");
        map.put("password", "123456");

        Map<String, Object> map1 = new HashMap<>();
        map1.put("url", "jdbc:mysql://192.168.110.138:3306/demo_ds_master"+params);
        map1.put("driver-class-name","com.mysql.jdbc.Driver");
        map1.put("username", "root");
        map1.put("password", "123456");
        result.put("ds_master", DataSourceUtil.getDataSource(className, map));
        result.put("ds_slave0", DataSourceUtil.getDataSource(className, map1));
        result.put("ds_slave1", DataSourceUtil.getDataSource(className, map));
        return result;
    }
}
