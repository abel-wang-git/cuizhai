package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

public class StaffProvider extends BaseProvider<Staff,String> {

    org.slf4j.Logger logger =  LoggerFactory.getLogger(this.getClass());

    public String listByCompany(){
        String sql =new SQL(){{
            SELECT(BuildSql.select(Staff.class));
            FROM(" t_staff");
            WHERE("company_id =#{company}");
        }}.toString();
        logger.info(sql);
        return sql;
    }
}
