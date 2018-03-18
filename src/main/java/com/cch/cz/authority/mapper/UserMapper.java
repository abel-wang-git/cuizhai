package com.cch.cz.authority.mapper;

import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.User;
import com.cch.cz.base.dao.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
@Mapper
public interface UserMapper extends BaseMapper<User,Long> {

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Select("select * from t_user where user_name=#{username}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "passWd", column = "pass_wd"),
            @Result(property = "userName",column = "user_name"),
    })
    User getByUserName(@Param("username") String username);

    /**
     * 获取用户的角色
     * @param userId
     * @return
     */
    @Select("select * from t_role r,t_user_role ur where r.id=ur.role_id and ur.user_id=#{userId}")
    List<Role> getRoleList(@Param("userId") Long userId);

    /**
     * 保存用户角色
     */
    @Insert({"insert into t_user_role (user_id,role_id) values(#{userId},#{roleId})"})
    void saveRoles(@Param("userId") Long userId,@Param("roleId") Long roleId);
}
