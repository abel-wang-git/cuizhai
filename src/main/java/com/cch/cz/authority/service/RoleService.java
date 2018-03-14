package com.cch.cz.authority.service;

import com.cch.cz.authority.entity.Power;
import com.cch.cz.authority.entity.Role;
import com.cch.cz.base.service.BaseService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
public interface RoleService  extends BaseService<Role,Long> {

    List<Power> getPowers(Long roleId);
}
