package com.cch.cz.entity;

import javax.persistence.*;


@Entity
@Table(name = "t_message_status")
public class MessageStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id;

    //接受人
    private String receiver;
    /**
     * 状态
     * 已读
     * 未读
     */
    private Integer status;
    //消息id
    public Long MessageId;


}
