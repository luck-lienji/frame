package com.example.demo.commons.basecode;

/**
 * @author liwenji
 * @ClassName ResultCode
 * @Description TODO,返参状态码
 * @date 2022/5/24 15:24
 * @Version 1.0
 */
public class ResultCode {
    /**
     * 成功
     */
    public final static int SUCCESS = 1;
    /**
     * 成功
     */
    public final static int OK = 200;
    // 通用错误以9开头
    /**
     * 未知错误
     */
    public final static int ERROR = 9999;
    /**
     * 应用级错误
     */
    public final static int APPLICATION_ERROR = 9000;
    /**
     * 参数验证错误
     */
    public final static int VALIDATE_ERROR = 9001;
    /**
     * 业务逻辑验证错误
     */
    public final static int SERVICE_ERROR = 9002;
    /**
     * 缓存访问错误
     */
    public final static int CACHE_ERROR = 9003;
    /**
     * 数据访问错误
     */
    public final static int DAO_ERROR = 9004;
    /**
     * 访问远程接口错误
     */
    public final static int REQUEST_ERROR = 9005;

    public final static String SUCESSS_MESSAGE = "操作成功";
    public final static String FAILURE_MESSAGE = "操作失败";
    public final static String ERROR_MESSAGE = "操作异常";
}
