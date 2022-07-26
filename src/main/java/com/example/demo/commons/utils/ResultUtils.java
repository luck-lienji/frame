package com.example.demo.commons.utils;

import com.example.demo.commons.basecode.Result;
import com.example.demo.commons.basecode.ResultCode;

import javax.validation.constraints.NotNull;


/**
 * @author liwenji
 * @ClassName ResultUtils
 * @Description TODO，获取返参结构体
 * @date 2022/5/24 15:20
 * @Version 1.0
 */
public class ResultUtils {


    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS, ResultCode.SUCESSS_MESSAGE, data);
    }

    public static <T> Result<T> success(@NotNull String message) {
        return new Result<>(ResultCode.SUCCESS, message, null);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCode.SUCCESS, message, data);
    }

    public static <T> Result<T> success(String message, T data, long total) {
        return new Result<>(ResultCode.SUCCESS, message, data, total);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(ResultCode.OK, ResultCode.SUCESSS_MESSAGE, data);
    }

    public static <T> Result<T> ok(@NotNull String message) {
        return new Result<>(ResultCode.OK, message, null);
    }

    public static <T> Result<T> ok(String message, T data) {
        return new Result<>(ResultCode.OK, message, data);
    }

    public static <T> Result<T> failure(int code, String message) {
        return new Result<>(code, message, null);
    }


}
