package com.cch.cz.authority.service;

import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.User;
import com.cch.cz.base.service.BaseService;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
public interface UserService  extends BaseService<User,Long> {
    User getByuserName(String username) ;

    List<Role> getRoleList(Long userId);
}
