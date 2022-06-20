package com.example.demo.commons.configs.security;

import com.example.demo.commons.configs.security.jwt.JwtAuthenticationProvider;
import com.example.demo.commons.configs.security.jwt.JwtAuthenticationTokenFilter;
import com.example.demo.commons.configs.security.jwt.JwtLoginFilter;
import com.example.demo.commons.configs.security.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liwenji
 * @ClassName WebSecurityConfig
 * @Description TODO,Spring Security的关键配置。Spring Security的核心配置类是  WebSecurityConfigurerAdapter 抽象类，这是权限管理启动的入口 在这个配置类中，我们主要做了以下几个配置：
 * 1. 访问路径URL的授权策略，如登录、Swagger访问免登录认证等
 * 2. 指定了登录认证流程过滤器 JwtLoginFilter，由它来触发登录认证
 * 3. 指定了自定义身份认证组件 JwtAuthenticationProvider，并注入 UserDetailsService
 * 4. 指定了访问控制过滤器 JwtAuthenticationFilter，在授权时解析令牌和设置登录状态
 * 5. 指定了退出登录处理器，因为是前后端分离，防止内置的登录处理器在后台进行跳转
 * @date 2022/5/27 9:24
 * @Version 1.0
 */
//定义配置类被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被
//AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，
//并用于构建bean定义，初始化Spring容器。
@Configuration
//加载了WebSecurityConfiguration配置类, 配置安全认证策略。
//加载了AuthenticationConfiguration,
@EnableWebSecurity
//用来构建一个全局的AuthenticationManagerBuilder的标志注解
//开启基于方法的安全认证机制，也就是说在web层的controller启用注解机制的安全确认
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 实现 UserDetailService 接口用来做登录认证
     */
    @Resource
    UserDetailsServiceImpl userDetailsService;

    /**
     * 当我们接受身份验证请求时，我们需要使用提供的凭据从数据库中检索正确的身份，然后对其进行验证。为此，我们需要实现 UserDetailsService 接口，定义如下：
     * public interface UserDetailsService {
     * <p>
     * UserDetails loadUserByUsername(String username)
     * throws UsernameNotFoundException;
     * <p>
     * }
     * 在这里，我们可以看到需要返回实现 UserDetails 接口的对象，而我们的 User 实体实现了它（具体实现请看样例项目的仓库）。
     * 考虑到它只暴露了单函数原型，我们可以把它当作一个函数式接口，并以 lambda 表达式的形式提供实现。
     */

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        // 创建 JwtAuthenticationProvider 实例
        JwtAuthenticationProvider authProvider = new JwtAuthenticationProvider();
        // 将自定义的认证逻辑添加到 JwtAuthenticationProvider
        authProvider.setUserDetailsService(userDetailsService);
        // 设置自定义的密码加密
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * 使用正确的提供者配置身份验证管理器,
     * 用于通过允许 AuthenticationProvider 容易地添加来建立认证机制。也就是说用来记录账号，密码，角色信息。
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure (AuthenticationManagerBuilder auth){
        // 使用自定义登录身份认证组件
        auth.authenticationProvider(jwtAuthenticationProvider());
    }

    /**
     * 配置网络安全（公共 URL、私有 URL、授权等）
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure (HttpSecurity http) throws Exception {
        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
        http.cors ().and ().csrf ().disable ()
                // 由于使用jwt,不创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //设置各个接口的权限，公共的接口设置为允许，如 swagger、登录接口、预检请求等
                .authorizeRequests ()
                // 跨域预检请求，预检请求
                //与前述简单请求不同，“需预检的请求”要求必须首先使用 OPTIONS 方法发起一个预检请求到服务器，以获知服务器是否允许该实际请求。"预检请求“的使用，可以避免跨域请求对服务器的用户数据产生未预期的影响。
                .antMatchers (HttpMethod.OPTIONS, "/**").permitAll ()
                .antMatchers ("/login").permitAll ()
                .antMatchers ("/swagger**/**").permitAll ()
                .antMatchers("/static/**").permitAll()// 不拦截静态资源
                // 其他所有请求需要身份认证
                .anyRequest ().authenticated ();
        // 退出登录处理器
        http.logout ().logoutSuccessHandler (new HttpStatusReturningLogoutSuccessHandler ());
        // 设置未授权请求异常处理程序
        http.exceptionHandling().authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    "用户未授权，请登陆后重试"
                            );
                        }
                )
                .and();
        //添加过滤器，如 JWT、

        // 访问 /login 开启登录认证流程过滤器
        http.addFilterBefore (new JwtLoginFilter (authenticationManager ()), UsernamePasswordAuthenticationFilter.class);
        // 访问其他路径时登录状态检查过滤器
        http.addFilterBefore (new JwtAuthenticationTokenFilter (authenticationManager ()), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 公开访问身份验证管理器，在这里如果使用其他的认证管理器，可以将它作为参数传进去
     * 在实现我们的登录 API 功能之前，我们还需要处理一个步骤——我们需要访问身份验证管理器。默认情况下，它是不可公开访问的，我们需要在我们的配置类中将其显式公开为 bean。
     *
     * @return AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager () throws Exception {
        return super.authenticationManager ();
    }

    /**
     * 除了身份验证提供程序，我们还需要使用正确的密码编码模式配置身份验证管理器，该模式将用于凭据验证。为此，我们需要将 PasswordEncoder 接口的首选实现公开为 bean。
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder ();
    }
}
