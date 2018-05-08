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
    /**
     * 备注
     */
    private String  rmarks;
    /**
     * 催收结果
     */
    private String result;
    /**
     * 接通状态
     */
    private String status;
    /**
     * 催收对象
     */
    private String target;
    /**
     * 关联cases
     */
    private  Long caseId;
    /**
     * 催收日期
     */
    private String createDate;

    /**
     *催收员
     */
    private String staffId;


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

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
