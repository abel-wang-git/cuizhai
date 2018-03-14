package com.cch.cz.authority.mapper;

import com.cch.cz.authority.entity.User;
import com.cch.cz.base.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator on 2018/3/14.
 *
 */
@Mapper
public interface UserMapper extends BaseMapper<User,Long> {
}
