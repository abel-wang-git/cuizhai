package com.cch.cz.authority.entity;

import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/3/14.
 * 权限类
 */
@Entity
@Table(name = "t_power")
public class Power extends BaseEntity {

    /**
     * 权限名称
     */
    @Id
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
