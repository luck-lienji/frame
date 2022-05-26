package com.example.demo.commons.configs.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.demo.commons.enmus.CommonConstants.DS1_DATA_SOURCE;
import static com.example.demo.commons.enmus.CommonConstants.DS2_DATA_SOURCE;
import static com.example.demo.commons.enmus.CommonConstants.DS3_DATA_SOURCE;


/**
 * @author liwenji
 * @ClassName DynamicDataSourceConfig
 * @Description TODO，数据源配置，根据连接信息，把数据源注入到 Spring 中，添加 DynamicDataSourceConfig 文件
 * @date 2022/5/24 16:41
 * @Version 1.0
 */
@Configuration
public class DynamicDataSourceConfig {
    @Primary
    @Bean (name = "ds1DataSourceProperties")
    @ConfigurationProperties (prefix = "spring.datasource.datasource1")
    public DataSourceProperties ds1DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "ds2DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.datasource2")
    public DataSourceProperties ds2DataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean(name = "ds3DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.datasource3")
    public DataSourceProperties ds3DataSourceProperties() {
        return new DataSourceProperties();
    }


    @Bean(name = "hikariProperties")
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    /**
     * 空间资源
     * @return DataSource
     */
    @Primary
    @Bean(name = DS1_DATA_SOURCE)
    public DataSource ds1DataSource(@Qualifier ("ds1DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSource(dataSourceProperties);
//        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 骨干资源
     * @return DataSource
     */
    @Bean(name =DS2_DATA_SOURCE)
    public DataSource ds2DataSource(@Qualifier("ds2DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSource(dataSourceProperties);
//        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
    /**
     * 门户
     * @return DataSource
     */
    @Bean(name = DS3_DATA_SOURCE)
    public DataSource ds3DataSource(@Qualifier("ds3DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSource(dataSourceProperties);
//        return dataSourceProperties.initializeDataSourceBuilder().build();
    }


    /**
     * 获取数据源
     * @param dataSourceProperties 数据库配置属性
     * @return DataSource
     */
    private DataSource dataSource(DataSourceProperties dataSourceProperties){
        HikariDataSource dataSource = (HikariDataSource)dataSourceProperties.initializeDataSourceBuilder().build();
        dataSource.setMinimumIdle(hikariConfig().getMinimumIdle());
        dataSource.setMaximumPoolSize(hikariConfig().getMaximumPoolSize());
        dataSource.setIdleTimeout(hikariConfig().getIdleTimeout());
        dataSource.setValidationTimeout(hikariConfig().getValidationTimeout());
        dataSource.setConnectionTimeout(hikariConfig().getConnectionTimeout());
        dataSource.setMaxLifetime(hikariConfig().getMaxLifetime());
        dataSource.setConnectionTestQuery(hikariConfig().getConnectionTestQuery());
        return dataSource;
    }

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSourceAbstract dynamicDataSourceAbstract = new DynamicDataSourceAbstract ();
        Map<Object, Object> dataSourceMap = new HashMap<> (3);
        dataSourceMap.put(DS1_DATA_SOURCE, ds1DataSource(ds1DataSourceProperties()));
        dataSourceMap.put(DS2_DATA_SOURCE, ds2DataSource(ds2DataSourceProperties()));
        dataSourceMap.put(DS3_DATA_SOURCE, ds3DataSource(ds3DataSourceProperties()));
        // 将 read 数据源作为默认指定的数据源
        dynamicDataSourceAbstract.setDefaultDataSource(ds1DataSource(ds1DataSourceProperties()));
        // 将 read 和 write 数据源作为指定的数据源
        dynamicDataSourceAbstract.setDataSources(dataSourceMap);
        return dynamicDataSourceAbstract;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 配置数据源，此处配置为关键配置，如果没有将 dynamicDataSource作为数据源则不能实现切换
        sessionFactory.setDataSource(dynamicDataSource());
        // 扫描Model
        sessionFactory.setTypeAliasesPackage("com.example.demo");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // 扫描映射文件,有的话就开启
//        sessionFactory.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
        //开启驼峰转换
        Objects.requireNonNull (sessionFactory.getObject ()).getConfiguration().setMapUnderscoreToCamelCase(true);
        return sessionFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        // 配置事务管理, 使用事务时在方法头部添加@Transactional注解即可
        return new DataSourceTransactionManager (dynamicDataSource());
    }
    
}
