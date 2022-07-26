package com.example.demo.project1.web;

import com.example.demo.commons.basecode.Result;
import com.example.demo.commons.cache.RedisUtils;
import com.example.demo.commons.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liwenji
 * @ClassName TestRedis
 * @Description TODO,测试redis
 * @date 2022/6/22 16:26
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class TestRedis {

    @Resource
    RedisUtils redisUtils;

    @GetMapping("/set")
    public Result<String> setKeyOfExpireTime(String key,String value){
        return ResultUtils.success(redisUtils.setKeyOfExpireTime(key,value,60 * 2));
    }

}
