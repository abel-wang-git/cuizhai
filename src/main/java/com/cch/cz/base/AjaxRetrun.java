package com.cch.cz.base;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/31.
 */
public class AjaxRetrun {
    private int code;
    private String message;
    private Map Data;

    public AjaxRetrun() {
    }

    public AjaxRetrun(String message) {
        this.message = message;
    }

    public AjaxRetrun(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public AjaxRetrun(int code, String message, Map data) {
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
