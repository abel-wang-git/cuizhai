package com.cch.cz.authority.service;

import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.entity.key.UserRoleKey;
import com.cch.cz.base.service.BaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
public interface UserService  extends BaseService<User,Long> {
    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User getByuserName(String username) ;

    /**
     * 获取用户的角色
     * @param userId
     * @return
     */
    List<Role> getRoleList(Long userId);
    /**
     * 保存用户角色
     */
    void saveRoles(List<UserRoleKey> list);
}
