package com.cch.cz.authority.service.impl;

import com.cch.cz.authority.entity.Power;
import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.key.RolePowerKey;
import com.cch.cz.authority.mapper.RoleMapper;
import com.cch.cz.authority.service.RoleService;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    public List<Power> getPowers(Long roleId) {
        return roleMapper.getPowers(roleId);
    }

    @Transactional
    public void savePowers(List<RolePowerKey> list) {
        for (RolePowerKey p:list) {
            roleMapper.savePowers(p.getRoleId(),p.getPowerId());
        }
    }
}
