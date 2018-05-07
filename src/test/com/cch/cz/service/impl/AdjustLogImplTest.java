package com.cch.cz.service.impl;


import com.cch.cz.App;
import com.cch.cz.entity.AdjustLog;
import com.cch.cz.entity.Staff;
import com.cch.cz.service.AdjustLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class AdjustLogImplTest {

    @Resource
    private AdjustLogService adjustLogService;

    @Test
    public void save(){
        AdjustLog adjustLog = new AdjustLog();
        adjustLog.setDate(new Date());
        adjustLog.setNewStaff("s");
        adjustLogService.save(adjustLog);
    }
}