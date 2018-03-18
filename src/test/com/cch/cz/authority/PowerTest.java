package com.cch.cz.authority;

import com.cch.cz.App;
import com.cch.cz.authority.entity.Power;
import com.cch.cz.authority.service.PowerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class PowerTest {

    @Resource
    private PowerService powerService;

    @Test
    public void save(){
        Power power = new Power();
        power.setName("test");

        powerService.save(power);
    }
}
