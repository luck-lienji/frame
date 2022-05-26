package com.example.demo.project1.mapper;

import com.example.demo.commons.configs.database.DataSource;
import com.example.demo.project1.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import static com.example.demo.commons.enmus.CommonConstants.DS1_DATA_SOURCE;
import static com.example.demo.commons.enmus.CommonConstants.DS2_DATA_SOURCE;
import static com.example.demo.commons.enmus.CommonConstants.DS3_DATA_SOURCE;

/**
 * @author liwenji
 * @ClassName Dynamic
 * @Description TODO，动态数据源测试，查询SQL
 * @date 2022/5/25 11:44
 * @Version 1.0
 */
@Mapper
public interface Dynamic {
    /**
     * 获取数据库1的数据
     * @return List<User>
     */
    @Select ("select id,user_name,password,note from user ")
    @DataSource (DS1_DATA_SOURCE)
    List<User> getUserInfoByDs1();

    /**
     * 获取数据库2的数据
     * @return List<User>
     */
    @Select ("select id,user_name,password,note from user ")
    @DataSource (DS2_DATA_SOURCE)
    List<User> getUserInfoByDs2();

    /**
     * 获取数据库3的数据
     * @return List<User>
     */
    @DataSource (DS3_DATA_SOURCE)
    @Select ("select id,user_name,password,note from user ")
    List<User> getUserInfoByDs3();


}
