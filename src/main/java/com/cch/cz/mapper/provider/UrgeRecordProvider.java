package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.UrgeRecord;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class UrgeRecordProvider extends BaseProvider<UrgeRecord,Long> {
    org.slf4j.Logger logger =  LoggerFactory.getLogger(this.getClass());
    public String findByCase(Map<String, Object> para){
        String sql =new SQL(){{
            SELECT(BuildSql.select(UrgeRecord.class));
            FROM(BuildSql.tablename(UrgeRecord.class));
            WHERE(" case_id =#{case}");
        }}.toString();
        logger.info(sql);
        return sql;
    }

    public String manager(Map<String, Object> para){
        String sql=new SQL(){{
            StringBuilder where =  new StringBuilder(" u.case_id=c.id and u.staff_id=s.login_name ");
            SELECT(" u.result,u.rmarks,u.status,u.target,u.create_date as createDate, s.name as sname,c.name as cname,c.contract_num as contractNum ,c.sex,c.id_card as idCard,c.sum_arrears as sumArrears" +
                    "  ,c.customer_phone_number as customerPhoneNumber ,c.status,c.customer_address as customerAddress ");
            FROM(" t_urge_record as u ,t_case as c,t_staff as s");
            if(UtilFun.isEmptyString((String) para.get("sname"))) where.append(" and s.name=#{sname}");
            if(UtilFun.isEmptyString((String) para.get("cname"))) where.append(" and c.name=#{cname}");
            if(UtilFun.isEmptyString((String) para.get("customerPhoneNumber"))) where.append(" and c.customer_phone_number=#{customerPhoneNumber} ");
            WHERE(where.toString());
        }}.toString();

        logger.info(sql);
        return sql;
    }
}
