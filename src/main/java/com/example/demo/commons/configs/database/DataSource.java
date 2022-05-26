package com.example.demo.commons.configs.database;


import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author liwenji
 * @ClassName DataSource
 * @Description TODO，自定义数据源注解
 * @date 2022/5/24 16:31
 * @Version 1.0
 */
@Retention ( RetentionPolicy.RUNTIME)
@Target ( {ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface DataSource{

    /**
     * 数据源key值
     */
    String value();

}
