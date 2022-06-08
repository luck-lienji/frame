package com.example.demo.commons.configs.security.service;

import com.example.demo.commons.configs.security.domain.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liwenji
 * @ClassName UserInfoService
 * @Description TODO，通过数据库获取用户信息
 * @date 2022/5/27 16:26
 * @Version 1.0
 */
public interface UserInfoService {
    /**
     * 通过用户名获取用户信息，这里应该是根据 手机号或者账号 获取用户信息
     *
     * @param userName 用户名
     * @return
     */
    List<UserInfo> getUserInfoByUsername(String userName);
}
