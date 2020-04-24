package com.ntu.datasync.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SecondaryDatasourceConfig {
    @Value("${spring.datasource.secondary.jdbc-url}")
    private String url;
    @Value("${spring.datasource.secondary.username}")
    private String user;
    @Value("${spring.datasource.secondary.password}")
    private String password;
    @Value("${spring.datasource.secondary.driver-class-name}")
    private String driverClass;

    @Value("${spring.datasource.secondary.maxActive}")
    private Integer maxActive;
    @Value("${spring.datasource.secondary.minIdle}")
    private Integer minIdle;
    @Value("${spring.datasource.secondary.initialSize}")
    private Integer initialSize;
    @Value("${spring.datasource.secondary.maxWait}")
    private Long maxWait;
    @Value("${spring.datasource.secondary.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.secondary.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;
    @Value("${spring.datasource.secondary.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${spring.datasource.secondary.testWhileIdle}")
    private Boolean testOnBorrow;
    @Value("${spring.datasource.secondary.testOnBorrow}")
    private Boolean testOnReturn;


    @Bean(name = "SecondaryDataSource")
    public DataSource ds1DataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);

        //连接池配置
        dataSource.setMaxActive(maxActive);
        dataSource.setMinIdle(minIdle);
        dataSource.setInitialSize(initialSize);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setValidationQuery("SELECT 'x'");

        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

        return dataSource;
    }


}
