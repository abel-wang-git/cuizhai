package com.cch.cz.service;

import com.alibaba.fastjson.JSON;
import com.cch.cz.App;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.City;
import com.cch.cz.entity.CityTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class CityServiceTest {
    @Resource
    private CityService cityService;
    @Test
    public void province() throws Exception {
        UtilFun.prinrObject(cityService.province());
    }

    @Test
    public void city() throws Exception {
    }
    @Test
    public void save(){
        City city = new City();
        city.setName("北京");
        city.setType(CityTypeEnum.PROVINCE);
        cityService.save(city);
    }

}