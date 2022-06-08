package com.example.demo.commons.configs.security.domain;

import java.util.List;

/**
 * @ClassName Role
 * @Description TODO
 * @date 2022/5/27 16:40
 * @Version 1.0
 */

public class Role {
    /**
     * 角色
     */
    private List<String> roles;

    public List<String> getRoles () {
        return roles;
    }

    public void setRoles (List<String> roles) {
        this.roles = roles;
    }
}
