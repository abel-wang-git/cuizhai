package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/18.
 * 员工service
 */
public interface CasesService extends BaseService<Cases,Long>  {


    void expCase(List<Cases> casesList);

    List<Map> groupCasesByArea();

    void allotCaseToCompany(List<String> data, String company);

    void allotStaff(List<Cases> cases, Staff staff);

    /**
     * 根据公司id查找未分配的case
     * @param company
     */
    List<Cases> listByCompanyNoStaff(Long company);

    List<Cases> listByStaff(String staff);
}
