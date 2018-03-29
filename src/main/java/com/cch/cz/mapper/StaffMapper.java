package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.entity.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2018/3/18.
 * 员工mapper
 */
@Mapper
public interface StaffMapper extends BaseMapper<Staff,Long> {
    @Select("SELECT * FROM t_staff where company_id =#{company}")
    List<Staff> listByCompany(@Param("company") String company);
}
