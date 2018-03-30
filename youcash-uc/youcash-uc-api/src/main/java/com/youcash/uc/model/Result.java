package com.youcash.uc.model;

import java.io.Serializable;

/**
 * @author linhansheng
 * @date 2018/03/26
 */
public class Result<T> implements Serializable {

    public static <T> Result<T> success(T data) {
        Result<T> rs = new Result();
        rs.setData(data);
        rs.setSuccess(true);
        rs.setCode("200");
        return rs;
    }

    public static <T> Result<T> success() {
        Result<T> rs = new Result();
        rs.setSuccess(true);
        rs.setCode("200");
        return rs;
    }

    public static <T> Result<T> failure(String msg) {
        Result<T> rs = new Result();
        rs.setMsg(msg);
        rs.setSuccess(false);
        return rs;
    }

    public static <T> Result<T> failure() {
        Result<T> rs = new Result();
        rs.setSuccess(false);
        return rs;
    }

    private boolean success;
    private T data;
    private String msg;
    private String code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
