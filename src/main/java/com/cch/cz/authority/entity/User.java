package com.cch.cz.authority.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 * 用户基类
 */

public class User {

    private Long id;

    private List<Long> roleId;

    private String userName;

    private String passWd ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<Long> roleId) {
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }
}
