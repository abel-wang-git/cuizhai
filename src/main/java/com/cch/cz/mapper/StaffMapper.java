package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.entity.Staff;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2018/3/18.
 * 员工mapper
 */
@Mapper
public interface StaffMapper extends BaseMapper<Staff,Long> {

}
