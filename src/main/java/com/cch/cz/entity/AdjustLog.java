package com.cch.cz.entity;

import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_adjust_log")
public class AdjustLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oldStaff;

    private String newStaff;

    private Date date;

    private Long caseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOldStaff() {
        return oldStaff;
    }

    public void setOldStaff(String oldStaff) {
        this.oldStaff = oldStaff;
    }

    public String getNewStaff() {
        return newStaff;
    }

    public void setNewStaff(String newStaff) {
        this.newStaff = newStaff;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
}
