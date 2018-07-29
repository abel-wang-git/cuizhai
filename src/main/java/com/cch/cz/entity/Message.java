package com.cch.cz.entity;


import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "t_message")
public class Message extends BaseEntity {


    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //内容
    @Column(length = 16777216)
    private String message;
    //发送人
    private String sender;
    //日期
    private String date;
    /**
     * 消息类型
     * 结案申请
     * 留案申请
     * 公告
     */
    private Integer type;

    private Long caseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }
}
