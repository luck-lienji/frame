package com.example.demo.commons.configs.database;


import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author liwenji
 * @ClassName DynamicDataSource
 * @Description TODO，默认数据源设置，以及数据源集合设置
 * @date 2022/5/24 16:58
 * @Version 1.0
 */
public class DynamicDataSourceAbstract extends AbstractRoutingDataSource {
    /**
     * 如果不希望数据源在启动配置时就加载好，可以定制这个方法，从任何你希望的地方读取并返回数据源
     * 比如从数据库、文件、外部接口等读取数据源信息，并最终返回一个DataSource实现类对象即可
     */
    @Override
    protected DataSource determineTargetDataSource() {
        return super.determineTargetDataSource();
    }

    /**
     * 如果希望所有数据源在启动配置时就加载好，这里通过设置数据源Key值来切换数据，定制这个方法
     * 根据用户定义的规则选择当前的数据源，这样我们可以在执行查询之前，设置使用的数据源。实现可动态路由的数据源，
     * 在每次数据库查询操作前执行。它的抽象方法 determineCurrentLookupKey() 决定使用哪个数据源。
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceContextHolder.getDataSourceKey();
    }

    /**
     * 设置默认数据源
     *
     * @param defaultDataSource 默认数据源
     */
    public void setDefaultDataSource(Object defaultDataSource) {
        super.setDefaultTargetDataSource(defaultDataSource);
    }

    /**
     * 设置所有要切换的数据源集合
     *
     * @param dataSources 数据源集合
     */
    public void setDataSources(Map<Object, Object> dataSources) {
        super.setTargetDataSources(dataSources);
        // 将数据源的 key 放到数据源上下文的 key 集合中，用于切换时判断数据源是否有效
        DynamicDataSourceContextHolder.addDataSourceKeys(dataSources.keySet());
    }
}
