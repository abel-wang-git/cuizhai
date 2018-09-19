package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.common.UtilFun;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SupplementUrgeProvider extends BaseProvider<com.cch.cz.entity.SupplementUrge, java.lang.Long> {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public String manager(Map<String, Object> para) {
        String sql = new SQL() {{
            StringBuilder where = new StringBuilder(" su.case_id=c.id and su.staff_id=s.login_name ");
            SELECT(" s.name as sname ,c.name as cname ,su.case_id as caseId ,su.create_date as createDate ,su.staff_id as staffId ,su.su_result as  suResult ,su.su_status as suStatus ,su.su_target as suTarget ,su.su_remark as suRemark ,c.contract_num as contractNum ,c.sex,c.id_card as idCard,c.sum_arrears as sumArrears,c.customer_phone_number as customerPhoneNumber ,c.status,c.customer_address as customerAddress ,c.appoint_data as appointData");
            FROM(" t_supplement_urge su, t_case c, t_staff s");
            if (UtilFun.isEmptyString((String) para.get("sname"))) where.append(" and s.name=#{sname}");
            if (UtilFun.isEmptyString((String) para.get("company"))) where.append(" and c.company_id=#{company}");
            if (UtilFun.isEmptyString((String) para.get("appointData")))
                where.append(" and c.appoint_data=#{appointData}");
            if (UtilFun.isEmptyString((String) para.get("cname"))) where.append(" and c.name=#{cname}");
            if (UtilFun.isEmptyString((String) para.get("customerPhoneNumber")))
                where.append(" and c.customer_phone_number=#{customerPhoneNumber} ");
            if (UtilFun.isEmptyString((String) para.get("type"))) where.append(" and c.type=#{type} ");
            WHERE(where.toString());
        }}.toString();

        logger.debug(sql);
        return sql;
    }

}