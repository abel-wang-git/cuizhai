package com.cch.cz.service;
import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.City;

import java.util.List;

public interface CityService extends BaseService<com.cch.cz.entity.City,java.lang.Long>  {

    /**
     * 省
     */
    List<City> province();
    /**
     * 根据pid查询
     */
    List<City> city(City city);
}