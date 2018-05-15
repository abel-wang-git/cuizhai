package com.cch.cz.ctrl;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.City;
import com.cch.cz.service.CityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/city")
public class  CityCtrl {

    @Resource
    private CityService cityService;
    @PostMapping(value = "/bycity")
    public AjaxReturn listByCity(@RequestParam String city){
        City city1 = JSON.parseObject(city,City.class);
        Map<Object,Object> data = new HashMap();
        data.put("cities",cityService.city(city1));
        return new AjaxReturn(0,"",data);
    }

}