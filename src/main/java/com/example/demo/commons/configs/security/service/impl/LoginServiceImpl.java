package com.example.demo.commons.configs.security.service.impl;

import com.example.demo.commons.basecode.Result;
import com.example.demo.commons.configs.security.domain.UserInfo;
import com.example.demo.commons.configs.security.service.LoginService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liwenji
 * @ClassName LoginServiceImpl
 * @Description TODO
 * @date 2022/6/24 15:20
 * @Version 1.0
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Override
    public Result<UserInfo> login(UserInfo userInfo, HttpServletRequest httpServletRequest) {
        //1、判断用户当前的token是否有效

        //2、验证用户名密码
        return null;
    }
}
