package com.cch.cz.authority.service;

import com.cch.cz.authority.entity.Power;
import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.key.RolePowerKey;
import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.Staff;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
public interface RoleService  extends BaseService<Role,Long> {
    /**
     *获取角色的权限
     * @param roleId
     * @return
     */
    List<Power> getPowers(Long roleId);

    /**
     * 保存角色
     * @param list
     */

    void savePowers(List<RolePowerKey> list);

    Role getByname(String name);

    /**
     * 根据用户的角色返回用户可以操作的角色
     * @return
     */
    List<Role> findByStaff(Staff staff);
}
