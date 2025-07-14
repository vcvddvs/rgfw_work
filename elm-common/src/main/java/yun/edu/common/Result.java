package yun.edu.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code; // 1-成功，0-失败
    private String msg;   // 提示信息
    private T data;       // 数据

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(1, "操作成功");
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(1, "操作成功", data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(0, "操作失败");
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(0, msg);
    }

    public boolean isSuccess() {
        return this.code == 1;
    }
} 