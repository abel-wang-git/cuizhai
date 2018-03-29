package com.cch.cz.service.impl;

import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.mapper.UserMapper;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.entity.Staff;
import com.cch.cz.mapper.StaffMapper;
import com.cch.cz.service.StaffService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Administrator on 2018/3/18.
 *
 */
@Service
public class StaffServiceImpl extends BaseServiceImpl<Staff,Long> implements StaffService {

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public void save(Staff staff) {
        staffMapper.save(staff);
        User user = new User();
        user.setUserName(Integer.toString(staff.getNumber()));
        user.setPassWd("123456");
        user.setPassWd(user.Sal());
        userMapper.save(user);
    }

    @Override
    public List<Staff> listByCompany(String company) {
        return staffMapper.listByCompany(company);
    }


}
