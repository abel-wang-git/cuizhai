package com.cch.cz.authority.entity;

import com.cch.cz.base.entity.BaseEntity;
import org.apache.shiro.crypto.hash.Md5Hash;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2018/3/14.
 * 用户基类
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {


    @Id
    private String userName;

    private String passWd ;


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


    public String Sal(){
        return new Md5Hash(this.getPassWd() + this.getUserName()).toString();
    }
}
