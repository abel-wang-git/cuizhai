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
    //日期
    private String date;
    /**
     * 消息类型
     * 结案申请
     * 留案申请
     * 公告
     */
    private Integer type;

}
