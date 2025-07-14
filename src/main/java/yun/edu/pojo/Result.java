package yun.edu.pojo;

public class Result {
    private boolean success;
    private String message;
    private Object data;

    // 构造函数
    public Result() {
    }

    // 成功的静态方法
    public static Result success(Object data) {
        Result res = new Result();
        res.setSuccess(true);
        res.setData(data);
        return res;
    }

    // 失败的静态方法
    public static Result failure(String message) {
        Result res = new Result();
        res.setSuccess(false);
        res.setMessage(message);
        return res;
    }

    // success字段的getter和setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // message字段的getter和setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // data字段的getter和setter
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}