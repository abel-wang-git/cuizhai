package com.cch.cz.entity;

import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/3/28.
 * 崔记
 */
@Entity
@Table(name = "t_urge_record")
public class UrgeRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String  rmarks;

    private String result;

    private String status;

    private String target;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRmarks() {
        return rmarks;
    }

    public void setRmarks(String rmarks) {
        this.rmarks = rmarks;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
