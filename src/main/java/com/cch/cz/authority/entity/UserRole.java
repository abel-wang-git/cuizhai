package com.cch.cz.authority.entity;

import com.cch.cz.authority.entity.key.UserRoleKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2018/3/14.
 * 用户和角色多对多映射
 */
@Entity
@Table(name = "t_user_role")
public class UserRole {

    @EmbeddedId
    private UserRoleKey userRoleKey;

}
