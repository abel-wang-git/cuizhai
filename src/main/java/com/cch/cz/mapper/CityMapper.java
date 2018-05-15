package com.cch.cz.mapper;
import com.cch.cz.entity.City;
import com.cch.cz.mapper.provider.CityProvider;
import org.apache.ibatis.annotations.Mapper;
import com.cch.cz.base.dao.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


/**

*

*/
@Mapper
public interface CityMapper extends BaseMapper<com.cch.cz.entity.City,java.lang.Long>
{

    @SelectProvider(type = CityProvider.class,method = "privince")
    List<City> privince();
    @SelectProvider(type = CityProvider.class,method = "city")
    List<City> city(City city);
}