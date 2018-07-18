package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.mapper.provider.SupplementUrgeProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;


/**

 *

 */
@Mapper
public interface SupplementUrgeMapper extends BaseMapper<com.cch.cz.entity.SupplementUrge, java.lang.Long> {

    @SelectProvider(type = SupplementUrgeProvider.class, method = "manager")
    List<Map> manager(Map c);
}