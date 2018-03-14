package com.cch.cz.authority.service.impl;

import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.service.UserService;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService{
    public User getByuserName(String username) {
        return null;
    }

    public List<Role> getRoleList(Long userId) {
        return null;
    }
}
