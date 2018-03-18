package com.cch.cz.authority.entity.key;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
@Embeddable
public class RolePowerKey implements Serializable {
    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long RoleId;
    /**
     * 权限Id
     */
    @Column(name = "power_id")
    private String PowerId;


    public Long getRoleId() {
        return RoleId;
    }

    public void setRoleId(Long roleId) {
        RoleId = roleId;
    }

    public String getPowerId() {
        return PowerId;
    }

    public void setPowerId(String powerId) {
        PowerId = powerId;
    }
}
