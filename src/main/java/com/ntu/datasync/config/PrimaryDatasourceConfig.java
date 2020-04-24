package com.ntu.datasync.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
@Configuration
public class PrimaryDatasourceConfig {
    @Value("${spring.datasource.primary.jdbc-url}")
    private String url;
    @Value("${spring.datasource.primary.username}")
    private String user;
    @Value("${spring.datasource.primary.password}")
    private String password;
    @Value("${spring.datasource.primary.driver-class-name}")
    private String driverClass;

    @Value("${spring.datasource.primary.maxActive}")
    private Integer maxActive;
    @Value("${spring.datasource.primary.minIdle}")
    private Integer minIdle;
    @Value("${spring.datasource.primary.initialSize}")
    private Integer initialSize;
    @Value("${spring.datasource.primary.maxWait}")
    private Long maxWait;
    @Value("${spring.datasource.primary.timeBetweenEvictionRunsMillis}")
    private Long timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.primary.minEvictableIdleTimeMillis}")
    private Long minEvictableIdleTimeMillis;
    @Value("${spring.datasource.primary.testWhileIdle}")
    private Boolean testWhileIdle;
    @Value("${spring.datasource.primary.testWhileIdle}")
    private Boolean testOnBorrow;
    @Value("${spring.datasource.primary.testOnBorrow}")
    private Boolean testOnReturn;


    @Bean(name = "PrimaryDataSource")
    @Primary
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
