package com.cch.cz.service.impl;
import com.cch.cz.entity.City;
import com.cch.cz.mapper.CityMapper;
import org.springframework.stereotype.Service;
import com.cch.cz.base.service.impl.BaseServiceImpl;
import com.cch.cz.service.CityService;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CityServiceImpl extends BaseServiceImpl<com.cch.cz.entity.City,java.lang.Long> implements CityService {

    @Resource
    private CityMapper cityMapper;
    @Override
    public List<City> province() {

        return cityMapper.privince();
    }

    @Override
    public List<City> city(City city) {
        return cityMapper.city(city);
    }
}