package com.example.demo.project2.web;

import com.example.demo.commons.configs.security.domain.UserInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

/**
 * @ClassName TestLogin
 * @Description TODO
 * @date 2022/6/7 17:01
 * @Version 1.0
 */
@RestController
@RequestMapping ()
public class TestLogin {
    @PostMapping ("/login")
    public String login(@RequestBody UserInfo userInfo)  {
        try {
            return "登陆成功";
        }catch ( Exception e ){
            e.printStackTrace ();
            return e.getMessage ();
        }
    }
}
