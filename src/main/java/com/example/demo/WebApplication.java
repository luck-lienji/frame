package com.example.demo;

import com.example.demo.commons.utils.DefaultProfileUtil;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author liwenji
 */
@SpringBootApplication(scanBasePackages={"com.example.demo.*"})
@MapperScan (basePackages="com.example.demo.*",annotationClass= Mapper.class)
public class WebApplication {

    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);

    private static final String SERVER_SSL_KEY_STORE = "server.ssl.key-store";

    public static void main (String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(WebApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
        String protocol = "http";
        if (env.getProperty(SERVER_SSL_KEY_STORE) != null) {
            protocol = "https";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}\n\t" +
                        "External: \t{}://{}:{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                env.getProperty("server.port"),
                protocol,
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getActiveProfiles());
    }

}
