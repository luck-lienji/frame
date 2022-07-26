package com.example.demo.commons.configs.security.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * @ClassName Role
 * @Description TODO
 * @date 2022/5/27 16:40
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
    /**
     * 角色id
     */
    private long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色产权所属类型
     */
    private String propertyTypeId;
    /**
     * 角色产权所属名称
     */
    private String propertyTypeName;
    /**
     * 角色状态
     */
    private String roleStatus;

    private ZonedDateTime createTime;
    private ZonedDateTime updataTime;
}
