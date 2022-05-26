package com.example.demo.commons.basecode;

/**
 * @author liwenji
 * @ClassName Result
 * @Description TODO，返回前端的结构体
 * @date 2022/5/24 15:15
 * @Version 1.0
 */
public class Result<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态信息
     */
    private String message;

    /**
     * 真正数据
     */
    private T data;

    private long total;

    public Integer getCode () {
        return code;
    }

    public void setCode (Integer code) {
        this.code = code;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public T getData () {
        return data;
    }

    public void setData (T data) {
        this.data = data;
    }

    public long getTotal () {
        return total;
    }

    public void setTotal (long total) {
        this.total = total;
    }

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
        this.total = 0;
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = 0;
    }
    public Result(Integer code, String message, T data,long total) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.total = total;
    }
}
