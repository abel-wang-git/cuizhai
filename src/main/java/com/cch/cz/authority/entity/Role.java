package com.cch.cz.authority.entity;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 * 角色
 */
public class Role {
    /**
     *角色id
     */
    private Long Id;
    /**
     * 角色名字
     */
    private String name;
    /**
     * 设计概要
     */
    private String design;
    /**
     * 权限
     */
    private List<Long> PowerId;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public List<Long> getPowerId() {
        return PowerId;
    }

    public void setPowerId(List<Long> powerId) {
        PowerId = powerId;
    }
}
