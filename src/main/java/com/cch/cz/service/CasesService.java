package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;

import java.util.List;

/**
 * Created by Administrator on 2018/3/18.
 * 员工service
 */
public interface CasesService extends BaseService<Cases,Long>  {


    void expCase(List<Cases> casesList);
}
