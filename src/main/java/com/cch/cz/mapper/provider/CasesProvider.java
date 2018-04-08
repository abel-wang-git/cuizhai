package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.Cases;
import org.apache.ibatis.annotations.Case;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CasesProvider extends BaseProvider<Cases,Long> {
    org.slf4j.Logger logger =  LoggerFactory.getLogger(this.getClass());

    public String listByCompanyNoStaff(Map<String, Object> para){
           String sql =new SQL(){{
            SELECT(BuildSql.select(Cases.class));
            FROM(" t_case");
            WHERE(" company_id="+para.get("company")+" and staff_id ='0'");
        }}.toString();
        logger.info(sql);
        return sql;
    }

    public  String listByStaff(Map<String, Object> para){
        String sql =new SQL(){{
            SELECT(BuildSql.select(Cases.class));
            FROM(BuildSql.tablename(Cases.class));
            WHERE(" staff_id =#{staff}");
        }}.toString();
        logger.info(sql);
        return sql;
    }
}
