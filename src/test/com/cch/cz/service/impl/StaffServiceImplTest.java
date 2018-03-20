package com.cch.cz.service.impl;


import com.cch.cz.App;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Staff;
import com.cch.cz.service.StaffService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class StaffServiceImplTest {



    @Resource
    private StaffService staffService;

    @Test
    public void findOne() throws Exception {
        UtilFun.prinrObject(staffService.findOne(7001l));
    }


    @Test
    public void save(){
        Staff staff = new Staff();
        for (int i =1 ;i<30 ;i++){
            staff.setNumber(i);
            staff.setName("test");
            staff.setPhone("1234567890");
            staff.setPlace("sdfasdf");
            staffService.save(staff);
        }
    }
}