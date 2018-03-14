package com.cch.cz.authority.entity;

/**
 * Created by Administrator on 2018/3/14.
 * 用户和角色多对多映射
 */
public class UserRole {
    /**
     * 用户ID
     */
    private Long UserId;
    /**
     * 角色Id
     */
    private Long RoleId;

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public Long getRoleId() {
        return RoleId;
    }

    public void setRoleId(Long roleId) {
        RoleId = roleId;
    }
}
