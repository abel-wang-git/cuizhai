package com.cch.cz.authority.entity;

/**
 * Created by Administrator on 2018/3/14.
 * 权限类
 */
public class Power {
    /**
     * 权限Id
     */
    private Long id;
    /**
     * 权限设计概要
     */
    private String design;
    /**
     * 权限名称
     */
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
