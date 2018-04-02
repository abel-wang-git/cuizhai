package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.Staff;

import java.util.List;

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


    List<Staff> listByCompany(String company);
}
