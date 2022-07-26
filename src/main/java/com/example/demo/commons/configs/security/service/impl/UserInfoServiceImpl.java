package com.example.demo.commons.configs.security.service.impl;

import com.example.demo.commons.configs.security.domain.UserInfo;
import com.example.demo.commons.configs.security.service.UserInfoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liwenji
 * @ClassName UserInfoServiceImpl
 * @Description TODO
 * @date 2022/5/27 16:27
 * @Version 1.0
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Override
    public List<UserInfo> getUserInfoByUsername (String userName) {
        if ( "liwenji".equals (userName) ){
            List<UserInfo> userInfos = new ArrayList<>();
            UserInfo userInfo = new UserInfo ();
            userInfo.setUserId(123456L);
            userInfo.setUsername("liwenji");
            userInfo.setPassword("123456");
            userInfos.add(userInfo);
            return userInfos;
        }else {
            return null;
        }

    }
}
