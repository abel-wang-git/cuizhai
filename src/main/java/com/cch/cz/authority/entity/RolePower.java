package com.cch.cz.authority.entity;

/**
 * Created by Administrator on 2018/3/14.
 * 角色和权限多对多映射
 */
public class RolePower  {
    /**
     * 角色id
     */
    private Long RoleId;
    /**
     * 权限Id
     */
    private Long PowerId;


    public Long getRoleId() {
        return RoleId;
    }

    public void setRoleId(Long roleId) {
        RoleId = roleId;
    }

    public Long getPowerId() {
        return PowerId;
    }

    public void setPowerId(Long powerId) {
        PowerId = powerId;
    }
}
