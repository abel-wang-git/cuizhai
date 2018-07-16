package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class StaffProvider extends BaseProvider<Staff,String> {

    org.slf4j.Logger logger =  LoggerFactory.getLogger(this.getClass());

    public String listByCompany(){
        String sql =new SQL(){{
            SELECT(BuildSql.select(Staff.class));
            FROM(" t_staff");
            WHERE("company_id =#{company} and is_enable=0");
        }}.toString();
        logger.info(sql);
        return sql;
    }

    public String listByStaff(Map<String, Object> para) {
        String sql = new SQL() {{
            SELECT(" s.name sName ,s.login_name as loginName,s.is_enable as isEnable, max(phone) phone ,max(urge_group) ugroup ,c.name cName , ifnull(sum(sum_arrears),0) as arrears  , count(ca.sum_arrears) size,s.place,s.company_id as companyId ");
            FROM(" t_staff as s left  join t_company c on s.company_id=c.id  left join t_case ca on( s.login_name = ca.staff_id and ca.status=4) ");
            if (para.get("company") != null)
                WHERE(" s.company_id=#{company} ");
            GROUP_BY(" login_name ,ca.status ");
        }}.toString();
        logger.info(sql);
        return sql;
    }
}
