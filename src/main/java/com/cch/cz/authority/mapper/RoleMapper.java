package com.cch.cz.authority.mapper;

import com.cch.cz.authority.entity.Power;
import com.cch.cz.authority.entity.Role;
import com.cch.cz.base.dao.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role,Long> {
    @Select({"select * from t_power p , t_role_power rp where p.id=rp.power_id and rp.role_id=#{roleId}"})
    List<Power> getPowers(Long roleId);
    @Insert({"insert into t_role_power (role_id,power_id) values (#{roleId},#{powerId})"})
    void savePowers(@Param("roleId")Long roleId, @Param("powerId")Long powerId);

}
