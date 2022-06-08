package com.example.demo.commons.configs.security.service.impl;

import com.example.demo.commons.configs.security.domain.UserInfo;
import com.example.demo.commons.configs.security.service.UserInfoService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.naming.Name;
import java.util.List;

/**
 * @author liwenji
 * @ClassName UserDetailsServiceImpl
 * @Description TODO，UserDetailsService 是需要实现的登录用户查询的 service 接口，实现 loadUserByUsername() 方法
 * @Description TODO，再通过User（UserDetails）返回details。
 * @date 2022/5/27 16:28
 * @Version 1.0
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource
    UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername (String userName) throws UsernameNotFoundException {
        //根据用户名获取用户信息
        List<UserInfo> userInfos = userInfoService.getUserInfoByUsername(userName);
        if( CollectionUtils.isEmpty(userInfos) ){
            throw new UsernameNotFoundException("用户不存在");
        }

        return userInfos.get (0);
    }
}
