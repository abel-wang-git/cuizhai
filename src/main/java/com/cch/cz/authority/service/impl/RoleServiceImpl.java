package com.cch.cz.authority.service.impl;

import com.cch.cz.authority.entity.Power;
import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.service.RoleService;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {

    public List<Power> getPowers(Long roleId) {
        return null;
    }
}
