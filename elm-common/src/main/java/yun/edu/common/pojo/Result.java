package yun.edu.common.pojo;

import java.io.Serializable;

public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int code;
    private String msg;
    private T data;

    // 构造函数
    public Result() {
    }

    // 成功的静态方法
    public static <T> Result<T> success(T data) {
        Result<T> res = new Result<>();
        res.setCode(1);
        res.setMsg("success");
        res.setData(data);
        return res;
    }

    // 带消息的成功方法
    public static <T> Result<T> success(String message, T data) {
        Result<T> res = new Result<>();
        res.setCode(1);
        res.setMsg(message);
        res.setData(data);
        return res;
    }

    // 简单成功方法
    public static <T> Result<T> success() {
        return success(null);
    }

    // 失败的静态方法
    public static <T> Result<T> failure(String message) {
        Result<T> res = new Result<>();
        res.setCode(0);
        res.setMsg(message);
        return res;
    }

    // 带数据的失败方法
    public static <T> Result<T> failure(String message, T data) {
        Result<T> res = new Result<>();
        res.setCode(0);
        res.setMsg(message);
        res.setData(data);
        return res;
    }

    // code字段的getter和setter
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    // msg字段的getter和setter
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    // data字段的getter和setter
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
} 