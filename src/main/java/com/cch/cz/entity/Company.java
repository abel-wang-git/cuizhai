package com.cch.cz.entity;

import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/3/28.
 * 公司
 */
@Entity
@Table(name = "t_company")
public class Company  extends BaseEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
