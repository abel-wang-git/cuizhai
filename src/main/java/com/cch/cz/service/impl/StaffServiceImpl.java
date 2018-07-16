package com.cch.cz.service.impl;

import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.mapper.UserMapper;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import com.cch.cz.mapper.CasesMapper;
import com.cch.cz.mapper.StaffMapper;
import com.cch.cz.mapper.UrgeRecordMapper;
import com.cch.cz.service.StaffService;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

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

    @Resource
    private CasesMapper casesMapper;

    @Resource
    private UrgeRecordMapper urgeRecordMapper;

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
    public void disable(List<Cases> cases, String[] staff) {
        int index = 0;
        for (int i = 0; i < cases.size(); i++) {
            Cases c = cases.get(i);
            if (index == staff.length) index = 0;
            c.setStaffId(staff[index]);
            urgeRecordMapper.updateByCase(staff[index], c.getId());
            casesMapper.update(c);
            index++;

        }
    }

    @Override
    public List<Map> listStaff(Long company) {
        return staffMapper.listStaff(company);
    }

    @Override
    public List<Map> listByStaff(){
        return staffMapper.listByStaff();
    }

    @Override
    public List<Map> listBycompanyccaa(String company){
        return staffMapper.listByCompanyccaa(company);}

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
