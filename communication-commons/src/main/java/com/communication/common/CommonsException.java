package com.communication.common;



public class CommonsException extends RuntimeException {

    private Integer code = StatusCode.FAIL;

    public CommonsException() {
    }

    public CommonsException(String message) {
        super(message);
    }

    public CommonsException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
