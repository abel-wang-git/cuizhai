package com.cch.cz.mapper;

import com.cch.cz.authority.entity.Power;
import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.entity.Cases;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 委案
 */
@Mapper
public interface CasesMapper extends BaseMapper<Cases,Long> {

    @Select("SELECT count(*) count,substring(customer_residence_address,instr(customer_residence_address,'省')+1,instr(customer_residence_address,'市')-instr(customer_residence_address,'省')) as area FROM cuizhai.t_case group by area")
    @Results({
            @Result(property = "count", column = "count"),
            @Result(property = "area",column = "area"),
    })
    List<Map> getCasesByArea();
}
