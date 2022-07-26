package com.example.demo.commons.configs.security.jwt;


import com.example.demo.commons.configs.security.domain.UserInfo;
import com.example.demo.commons.configs.security.service.impl.UserDetailsServiceImpl;
import com.example.demo.commons.execption.BusException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author liwenji
 * @ClassName JwtAuthenticationProvider
 * @Description TODO，自定义jwt的认证实现类
 * @date 2022/5/27 18:23
 * @Version 1.0
 */
@Service
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private PasswordEncoder passwordEncoder;

    private UserDetailsService userDetailsService;


    /**
     * 校验密码的正确性，如果正确就返回 token
     *
     * @param authentication
     * @return
     */
    @Override
    public Authentication authenticate (Authentication authentication) throws UsernameNotFoundException {
        // 获取前端表单中输入后返回的用户名、密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        //根据用户名，获取用户信息
        UserInfo userInfo = (UserInfo ) userDetailsService.loadUserByUsername(username);
        if (passwordEncoder.matches(password, passwordEncoder.encode (userInfo.getPassword()))) {
            //如果密码匹配，则返回 Authentication 接口的实现以及必要的详细信息
            return new UsernamePasswordAuthenticationToken(userInfo, null, userInfo.getAuthorities());
        } else {	//密码不匹配，抛出异常
            throw new BadCredentialsException ("账号或密码错误!");
        }


        //验证账号密码的正确性

    }

    /**
     * 如果该 AuthenticationProvider 支持传入的 Authentication 对象，则返回true
     *
     * @param authentication 参数
     * @return 布尔
     */
    @Override
    public boolean supports (Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


    /**
     * 自定义的认证逻辑
     *
     * @param userDetailsService
     */
    public void setUserDetailsService (UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    /**
     * 自定义的密码加密
     *
     * @param passwordEncoder
     */
    public void setPasswordEncoder (PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }



}
