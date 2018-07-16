package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/18.
 * 员工service
 */
public interface StaffService extends BaseService<Staff,String>  {
    /**
     * 密码加密
     * @param staff
     */
    void save(Staff staff);
    void update(Staff staff,String oldId);


    List<Staff> listByCompany(String company);

    /**
     * 禁用员工
     *
     * @param cases
     * @param staff
     */
    void disable(List<Cases> cases, String[] staff);

    List<Map> listStaff(Long company);
    List<Map> listByStaff();
    List<Map> listBycompanyccaa(String company);
}
