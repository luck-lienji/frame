package com.example.demo.commons.configs.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static com.example.demo.commons.enmus.CommonConstants.DS1_DATA_SOURCE;
/**
 * @author liwenji
 * @ClassName DynamicDataSourceContextHolder
 * @Description TODO，动态数据源名称上下文
 * @date 2022/5/24 17:00
 * @Version 1.0
 */
public class DynamicDataSourceContextHolder {
    //这边定义了一个和线程绑定的ThreadLocal变量，用于存放需要使用的数据源的名称
    /* 动态数据源名称上下文*/
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>() {
        /**
         * 将 read 数据源的 key作为默认数据源的 key
         */
        @Override
        protected String initialValue() {
            return DS1_DATA_SOURCE  ;
        }
    };


    /**
     * 数据源的 key集合，用于切换时判断数据源是否存在
     */
    public static List<Object> dataSourceKeys = new ArrayList<> ();

    /**
     * 切换数据源
     * @param key 数据源key
     */
    public static void setDataSourceKey(String key) {
        CONTEXT_HOLDER.set(key);
    }

    /**
     * 获取数据源
     * @return string
     */
    public static String getDataSourceKey() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 重置数据源
     */
    public static void clearDataSourceKey() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 判断是否包含数据源
     * @param key 数据源key
     * @return 布尔
     */
    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }

    /**
     * 添加数据源keys
     * @param keys 数据源名称集合
     */
    public static void addDataSourceKeys(Collection < ?> keys) {
        dataSourceKeys.addAll (keys);
    }
}
