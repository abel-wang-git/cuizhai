package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.Company;
import com.cch.cz.entity.Staff;

import java.util.List;

/**
 *
 */
public interface CompanyService extends BaseService<Company,Long>  {

    /**
     * 根据用户获取公司和下级公司
     */

    List<Company> listBystaff(Staff staff);

    /**
     * 根据公司Id获取公司信息
     */
    Company companyByCompanyId(Long companyId);

}
