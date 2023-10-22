package com.eat.untils;

import lombok.Data;

@Data
public class R {
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_FAILED = 400;


    //是否成功
    private boolean success;
    //状态码
    private int code;
    //描述
    private String msg;
    //数据
    private Object data;

    public R setData(Object data) {
        this.data = data;
        return this;
    }



    public static R SUCCESS(String msg) {
        R r = new R();
        r.success = true;
        r.code = CODE_SUCCESS;
        r.msg = msg;
        return r;
    }

    public static R SUCCESS(String msg, Object data) {
        R success = SUCCESS(msg);
        success.data = data;
        return success;
    }

    public static R FAILED(String msg) {
        R r = new R();
        r.success = true;
        r.code = CODE_FAILED;
        r.msg = msg;
        return r;
    }

    public static R FAILED(String msg, Object data) {
        R failed = FAILED(msg);
        failed.data = data;
        return failed;
    }

}
