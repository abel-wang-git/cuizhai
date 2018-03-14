package com.cch.cz.authority.entity;

import com.cch.cz.authority.entity.key.RolePowerKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2018/3/14.
 * 角色和权限多对多映射
 */
@Entity
@Table(name = "t_role_power")
public class RolePower  {

    /**
     * 复合主键
     */
    @EmbeddedId
     private RolePowerKey rolePowerKey;


}
