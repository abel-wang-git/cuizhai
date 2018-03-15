package com.cch.cz.authority.service.impl;

import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.entity.key.UserRoleKey;
import com.cch.cz.authority.mapper.UserMapper;
import com.cch.cz.authority.service.UserService;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService{

    @Resource
    private UserMapper userMapper;

    public User getByuserName(String username) {
        return userMapper.getByUserName(username);
    }

    public List<Role> getRoleList(Long userId) {
        return userMapper.getRoleList(userId);
    }

    @Override
    @Transactional
    public void saveRoles(List<UserRoleKey> list) {
        for (UserRoleKey u:list) {
            userMapper.saveRoles(u.getUserId(),u.getRoleId());
        }
    }
}
