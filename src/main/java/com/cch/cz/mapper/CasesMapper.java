package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.entity.Cases;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 委案
 */
@Mapper
public interface CasesMapper extends BaseMapper<Cases,Long> {

    @Select("SELECT count(*) count,left(customer_residence_address,instr(customer_residence_address,'市')) as area FROM cuizhai.t_case  where company_id is null and status!=1  group by area")
    @Results({
            @Result(property = "count", column = "count"),
            @Result(property = "area",column = "area"),
    })
    List<Map> groupCasesByArea();

    @Update("UPDATE t_case SET company_id=#{company} WHERE customer_residence_address like #{area}")
    void allotCompany(@Param("company") String company,@Param("area") String area);

    @Update("UPDATE t_case SET company_id=#{case} WHERE customer_residence_address like #{staff}")
    void allotStaff(@Param("case") String caseId, @Param("staff") String staff);
}
