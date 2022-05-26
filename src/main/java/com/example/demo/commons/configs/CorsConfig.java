package com.example.demo.commons.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liwenji
 * @ClassName CorsConfig
 * @Description TODO，跨域配置
 * @date 2022/5/24 15:56
 * @Version 1.0
 */
@Configuration
public class CorsConfig{

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // 允许源
                        .allowedOriginPatterns("*")
                        // 允许的HTTP方法
                        .allowedMethods("*")
                        // 允许的字段
                        .allowedHeaders("*")
                        // 可从请求头中获取的字段
                        .exposedHeaders("redirectUrl,Link,X-Total-Count,Content-Disposition")
                        // 允许发送处理COOKIE
                        .allowCredentials(true)
                        // 缓存时间
                        .maxAge(1800);
            }
        };
    }
}
