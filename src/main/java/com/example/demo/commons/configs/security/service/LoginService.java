package com.example.demo.commons.configs.security.service;

import com.example.demo.commons.basecode.Result;
import com.example.demo.commons.configs.security.domain.UserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liwenji
 * @ClassName LoginService
 * @Description TODO
 * @date 2022/6/24 15:19
 * @Version 1.0
 */
public interface LoginService {
    /**
     * 用户登录
     *
     * @return
     */
    Result<UserInfo> login(UserInfo userInfo, HttpServletRequest httpServletRequest);
}
