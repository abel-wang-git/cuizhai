package com.cch.cz.entity;


import com.cch.cz.base.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "t_message")
public class Message extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //内容
    private String message;
    //发送人
    private String sender;
    //接受人
    private String receiver;
    /**
     * 状态
     * 已读
     * 未读
     */
    private Integer status;
    //日期
    private String date;
    /**
     * 消息类型
     * 结案申请
     * 留案申请
     */
    private Integer type;

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


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
