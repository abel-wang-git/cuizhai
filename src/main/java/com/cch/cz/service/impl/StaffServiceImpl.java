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
public class StaffServiceImpl extends BaseServiceImpl<Staff,String> implements StaffService {

    @Resource
    private StaffMapper staffMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public void save(Staff staff) {
        staffMapper.save(staff);
        User user = new User();
        user.setUserName(staff.getLoginName());
        user.setPassWd("123456");
        user.setPassWd(user.Sal());
        userMapper.save(user);
        userMapper.saveRoles(user.getUserName(),Long.parseLong(staff.getPlace()));
    }

    @Override
    public List<Staff> listByCompany(String company) {
        return staffMapper.listByCompany(company);
    }

    @Override
    @Transactional
    public void update(Staff staff,String oldId) {
           Staff staffold= staffMapper.findOne(staff,oldId);
        if(staff.getPlace()!=staffold.getPlace()){
            userMapper.deleteRoles(oldId);
            userMapper.saveRoles(staff.getLoginName(),Long.parseLong(staff.getPlace()));
        }
        staffMapper.update(staff);
        User user= userMapper.findOne(new User(),staff.getLoginName());
//        if(null==user)user=new User();
        user.setUserName(staff.getLoginName());
        user.setPassWd("123456");
        user.setPassWd(user.Sal());
        userMapper.update(user);
    }


}
