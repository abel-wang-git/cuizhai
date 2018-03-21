package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.entity.Cases;
import org.apache.ibatis.annotations.Mapper;

/**
 * 委案
 */
@Mapper
public interface CasesMapper extends BaseMapper<Cases,Long> {
}
