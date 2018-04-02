package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.entity.Staff;
import com.cch.cz.mapper.provider.StaffProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * Created by Administrator on 2018/3/18.
 * 员工mapper
 */
@Mapper
public interface StaffMapper extends BaseMapper<Staff,String> {

    @SelectProvider(type = StaffProvider.class ,method = "listByCompany")
    List<Staff> listByCompany(@Param("company") String company);
}
