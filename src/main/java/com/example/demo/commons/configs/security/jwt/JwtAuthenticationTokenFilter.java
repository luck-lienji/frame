package com.example.demo.commons.configs.security.jwt;

import com.example.demo.commons.configs.security.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @author liwenji
 * @ClassName JWTAuthenticationTokenFilter
 * @Description TODO, JWT接口请求校验拦截器
 * TODO: BasicAuthenticationFilter 此过滤器会自动解析HTTP请求中头部名字为Authentication，且以Basic开头的头部信息
 * @date 2022/5/27 16:00
 * @Version 1.0
 */
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    public JwtAuthenticationTokenFilter (AuthenticationManager authenticationManager) {
        super (authenticationManager);
    }
    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {


        // 从 HTTP 请求中获取 token
        String token = JwtUtils.getTokenFromHttpRequest(request);
        // 验证 token 是否有效
        if ( StringUtils.hasText(token) && JwtUtils.validateToken(token)) {
            // 获取认证信息
            Authentication authentication = JwtUtils.getAuthentication(token);
            // 将认证信息存入 Spring 安全上下文中
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        //调用后续filter
        filterChain.doFilter(request, response);

    }



}
