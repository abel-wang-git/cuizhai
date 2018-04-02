package com.cch.cz.entity;

import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/3/18.
 * 员工表
 */
@Entity
@Table(name = "t_staff")
public class Staff  extends BaseEntity{
    /**
     * 姓名
     */
    private String name ;
    /**
     * 工号
     */
    @Id
    private  String loginName;

    /**
     * 职位
     */
    private  String place;

    /**
     * 电话
     */
    private  String phone;
    /**
     * 所属公司
     */
    private  Long CompanyId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(Long companyId) {
        CompanyId = companyId;
    }
}
