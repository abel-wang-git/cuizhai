package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.Cases;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

public class CasesProvider extends BaseProvider<Cases,Long> {
    org.slf4j.Logger logger =  LoggerFactory.getLogger(this.getClass());

    public String listByCompanyNoStaff(Long company){
           String sql =new SQL(){{
            SELECT(BuildSql.select(Cases.class));
            FROM(" t_case");
            WHERE(" company_id=#{company} and staff_id is null");
        }}.toString();
        logger.info(sql);
        return sql;
    }
}
