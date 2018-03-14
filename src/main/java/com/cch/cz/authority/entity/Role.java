package com.cch.cz.authority.entity;

import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 * 角色
 */
@Entity
@Table(name = "t_role")
public class Role extends BaseEntity {
    /**
     *角色id
     */
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long Id;
    /**
     * 角色名字
     */
    private String name;
    /**
     * 设计概要
     */
    private String design;

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

}
