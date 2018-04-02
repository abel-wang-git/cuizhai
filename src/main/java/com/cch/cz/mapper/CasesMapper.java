package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.Cases;
import com.cch.cz.mapper.provider.CasesProvider;
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

    @Update("UPDATE t_case SET staff_id=#{staff} WHERE id=#{case}")
    void allotStaff(@Param("case") Long caseId, @Param("staff") String staff);

    @SelectProvider(type = CasesProvider.class,method = "listByCompanyNoStaff")
    List<Cases> listByCompanyNoStaff(@Param("company") Long company);
}
