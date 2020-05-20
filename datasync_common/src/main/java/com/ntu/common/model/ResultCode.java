package com.ntu.common.model;

public enum ResultCode {

    SUCCESS(true,10000,"操作成功！"),
    //---系统错误返回码-----
    FAIL(false,10001,"当日没有相关日志"),
    ERROR_NOT_FOUND(false,10002,"当日没有错误日志"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),

    //---用户操作返回码----
    MOBILE_PASSWORD_ERROR(false,20001,"用户名或密码错误！");
    //---企业操作返回码----
    //---权限操作返回码----
    //---其他操作返回码----

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

}
