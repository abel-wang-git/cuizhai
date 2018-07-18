package com.cch.cz.entity;

import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/3/28.
 * 协查崔记
 */
@Entity
@Table(name = "t_supplement_urge")
public class SupplementUrge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 备注
     */
    private String suRemark;
    /**
     * 催收结果
     */
    private String suResult;
    /**
     * 接通状态
     */
    private String suStatus;
    /**
     * 催收对象
     */
    private String suTarget;
    /**
     * 关联cases
     */
    private Long caseId;
    /**
     * 催收日期
     */
    private String createDate;

    /**
     * 催收员
     */
    private String staffId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuRemark() {
        return suRemark;
    }

    public void setSuRemark(String suRemark) {
        this.suRemark = suRemark;
    }

    public String getSuResult() {
        return suResult;
    }

    public void setSuResult(String suResult) {
        this.suResult = suResult;
    }

    public String getSuStatus() {
        return suStatus;
    }

    public void setSuStatus(String suStatus) {
        this.suStatus = suStatus;
    }

    public String getSuTarget() {
        return suTarget;
    }

    public void setSuTarget(String suTarget) {
        this.suTarget = suTarget;
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
