package com.cch.cz.authority.entity;

import com.cch.cz.base.entity.BaseEntity;
import org.apache.shiro.crypto.hash.Md5Hash;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 * 用户基类
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String passWd ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWd() {
        return passWd;
    }

    public void setPassWd(String passWd) {
        this.passWd = passWd;
    }


    public String getSal(){
        return new Md5Hash(this.getPassWd() + this.getUserName()).toString();
    }
}
