package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.entity.WhiteList;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2018/3/18.
 *白名单mapper
 */
@Mapper
public interface WhiteListMapper extends BaseMapper<WhiteList,String> {

}
