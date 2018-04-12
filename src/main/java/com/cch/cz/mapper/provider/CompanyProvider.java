package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.Company;
import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CompanyProvider extends BaseProvider<Company,Long> {
    org.slf4j.Logger logger =  LoggerFactory.getLogger(this.getClass());
    public String listByStaff(Map<String, Object> para){
        String sql= new SQL(){{
            SELECT(BuildSql.select(Company.class));
            FROM(BuildSql.tablename(Company.class));
            WHERE("id=#{company} or p_id = #{company}");
        }}.toString();
        logger.info(sql);
        return sql;
    }
}
