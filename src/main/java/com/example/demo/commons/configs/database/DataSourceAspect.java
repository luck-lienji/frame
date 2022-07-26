package com.example.demo.commons.configs.database;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author liwenji
 * @ClassName DataSourceAspect
 * @Description TODO
 * @date 2022/5/26 17:42
 * @Version 1.0
 */
@Aspect
@Component
@Order(-1)
public class DataSourceAspect {
    private static final Logger log = LoggerFactory.getLogger(DataSourceAspect.class);

    /**
     * 切换数据源
     *
     * @param point      切点
     * @param dataSource 要切换的数据源
     */
    @Before("@annotation(dataSource)")
    public void switchDataSource(JoinPoint point, DataSource dataSource) {
        if (!DynamicDataSourceContextHolder.containDataSourceKey(dataSource.value())) {
            log.error("DataSource [{}] doesn't exist, use default DataSource [{}] ",
                    dataSource.value(), DynamicDataSourceContextHolder.getDataSourceKey());
        } else {
            // 切换数据源
            DynamicDataSourceContextHolder.setDataSourceKey(dataSource.value());
            log.debug("Switch DataSource to [" + DynamicDataSourceContextHolder.getDataSourceKey()
                    + "] in Method [" + point.getSignature() + "]");
        }
    }

    /**
     * 重置数据源
     *
     * @param point      切点
     * @param dataSource 清除指定的数据源
     */
    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint point, DataSource dataSource) {
        // 将数据源置为默认数据源
        DynamicDataSourceContextHolder.clearDataSourceKey();
        log.debug("Restore DataSource to [" + DynamicDataSourceContextHolder.getDataSourceKey()
                + "] in Method [" + point.getSignature() + "]");
    }

}
