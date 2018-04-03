package com.cch.cz.service.impl;

import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.entity.Company;
import com.cch.cz.entity.Staff;
import com.cch.cz.mapper.CompanyMapper;
import com.cch.cz.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class CompanyServiceImpl extends BaseServiceImpl<Company,Long> implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public List<Company> listBystaff( Staff staff) {

        if(staff.getCompanyId()==null){
            return companyMapper.findAll(new Company());
        }else {
            return companyMapper.listByStaff(staff.getCompanyId());
        }
    }
}
