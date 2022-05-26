package com.example.demo.project1.web;

import com.example.demo.commons.basecode.Result;
import com.example.demo.commons.basecode.ResultCode;
import com.example.demo.commons.utils.ResultUtils;
import com.example.demo.project1.domain.User;
import com.example.demo.project1.mapper.Dynamic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.example.demo.commons.enmus.CommonConstants.DS1_DATA_SOURCE;
import static com.example.demo.commons.enmus.CommonConstants.DS2_DATA_SOURCE;
import static com.example.demo.commons.enmus.CommonConstants.DS3_DATA_SOURCE;

/**
 * @author liwenji
 * @ClassName TestDynamicDataSourceWeb
 * @Description TODO
 * @date 2022/5/25 11:39
 * @Version 1.0
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class TestDynamicDataSourceWeb {

    @Resource
    Dynamic dynamic;

    @GetMapping("/dynamic")
    public Result<List<User>> getDynamic(String datasource){
        switch ( datasource ) {
            case DS1_DATA_SOURCE:{
                return ResultUtils.ok (datasource, dynamic.getUserInfoByDs1 ());
            }
            case DS2_DATA_SOURCE: {
                return ResultUtils.ok (datasource, dynamic.getUserInfoByDs2 ());
            }
            case DS3_DATA_SOURCE:{
                List<User> list = dynamic.getUserInfoByDs3 ();
                return ResultUtils.ok (datasource, list);
            }
            default:
                return ResultUtils.failure (ResultCode.ERROR, datasource);
        }

    }
}
