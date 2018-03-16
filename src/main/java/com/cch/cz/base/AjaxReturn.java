package com.cch.cz.base;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/31.
 * 前台返回值
 */
public class AjaxReturn {

    public static  final  int CODE_SUCCESS=0;
    public static  final  int CODE_FAILD=1;
    private int code;
    private String message;
    private Map<Object,Object> Data;

    public AjaxReturn() {
    }



    public AjaxReturn(String message) {
        this.message = message;
    }

    public AjaxReturn(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AjaxReturn(int code, String message, Map data) {
        this.code = code;
        this.message = message;
        Data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map getData() {
        return Data;
    }

    public void setData(Map data) {
        Data = data;
    }

}
