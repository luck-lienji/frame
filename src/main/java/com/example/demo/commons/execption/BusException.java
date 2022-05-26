package com.example.demo.commons.execption;

/**
 * @author liwenji
 * @ClassName BusException
 * @Description TODO，自定义业务异常处理
 * @date 2022/5/24 16:22
 * @Version 1.0
 */
public class BusException extends RuntimeException{

    private static final long serialVersionUID = 3143009648268347307L;

    /** 错误码 */
    private String  errorCode;

    /** 错误信息描述 */
    private String errorMsg;

    public String getErrorCode () {
        return errorCode;
    }

    public void setErrorCode (String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg () {
        return errorMsg;
    }

    public void setErrorMsg (String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public BusException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusException(String message, String errorCode, String errorMsg) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusException(String message, Throwable cause, String errorCode, String errorMsg) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusException(Throwable cause, String errorCode, String errorMsg) {
        super(cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errorCode, String errorMsg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


}
