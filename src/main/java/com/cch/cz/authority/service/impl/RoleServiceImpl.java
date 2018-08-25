package com.cch.cz.authority.service.impl;

import com.cch.cz.authority.entity.Power;
import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.key.RolePowerKey;
import com.cch.cz.authority.mapper.RoleMapper;
import com.cch.cz.authority.service.RoleService;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.entity.Staff;
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

    @Override
    public Role getByname(String name) {
        Role roles = roleMapper.getByName(name);
        if (roles != null) {
            return roles;
        }
        return null;
    }

    @Override
    public List<Role> findByStaff(Staff staff) {
        Role staffrole= findOne(Long.valueOf(staff.getPlace()));
        List<Role> result=null;
        switch (staffrole.getDesign()){
            case Role.ADMIN:
                result=roleMapper.findNoSuper();
                break;
            default:
                result=roleMapper.findNoAdmin();
        }
        return result;
    }
}
