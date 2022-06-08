//package com.example.demo.commons.configs.swagger;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author liwenji
// * @ClassName SwaggerConfig
// * @Description TODO，swagger配置类，除了常规配置外，加了一个令牌属性，可以在接口调用的时候传递令牌。加了令牌属性后的 Swagger 接口调用界面，会多出一个令牌参数，在发起请求的时候一起发送令牌。
// * @date 2022/5/27 9:16
// * @Version 1.0
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//
//
//    @Bean
//    public Docket createRestApi(){
//        // 添加请求参数，我们这里把token作为请求头部参数传入后端
//        ParameterBuilder parameterBuilder = new ParameterBuilder();
//        List<Parameter> parameters = new ArrayList<Parameter> ();
//        parameterBuilder.name("Authorization").description("令牌").modelRef(new ModelRef ("string")).parameterType("header")
//                .required(false).build();
//        parameters.add(parameterBuilder.build());
//        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any()).build().globalOperationParameters(parameters);
//    }
//
//    private ApiInfo apiInfo(){
//        return new ApiInfoBuilder ()
//                .title("SpringBoot API Doc")
//                .description("This is a restful api document of Spring Boot.")
//                .version("1.0")
//                .build();
//    }
//
//}
