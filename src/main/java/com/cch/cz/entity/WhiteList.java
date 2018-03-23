package com.cch.cz.entity;

import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2018/3/23.
 * 白名单
 */
@Entity
@Table(name = "t_whitelist")
public class WhiteList extends BaseEntity {

    @Id
    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
