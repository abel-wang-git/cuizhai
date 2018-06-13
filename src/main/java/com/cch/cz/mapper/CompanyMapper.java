package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.entity.Company;
import com.cch.cz.mapper.provider.CompanyProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * 委案
 */
@Mapper
public interface CompanyMapper extends BaseMapper<Company,Long> {
    @SelectProvider(type = CompanyProvider.class,method = "listByStaff")
    List<Company> listByStaff(@Param("company") Long company);
    @SelectProvider(type = CompanyProvider.class,method = "companyByCompanyId")
    Company companyByCompanyId(@Param("company") Long companyId);
}
