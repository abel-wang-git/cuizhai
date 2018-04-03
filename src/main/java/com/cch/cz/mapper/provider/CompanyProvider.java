package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.Company;
import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.ibatis.jdbc.SQL;

public class CompanyProvider extends BaseProvider<Company,Long> {

    public String listByStaff(){
        return new SQL(){{
            SELECT(BuildSql.select(Company.class));
            FROM(BuildSql.tablename(Company.class));
            WHERE("id=#{company} or p_id = #{company}");
        }}.toString();
    }
}
