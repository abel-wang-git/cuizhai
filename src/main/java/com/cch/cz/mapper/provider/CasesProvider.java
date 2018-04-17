package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CasesProvider extends BaseProvider<Cases, Long> {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public String listByCompanyNoStaff(Map<String, Object> para) {
        String sql = new SQL() {{
            SELECT(BuildSql.select(Cases.class));
            FROM(" t_case ");
            WHERE(" company_id=" + para.get("company") + " and staff_id is null");
        }}.toString();
        logger.info(sql);
        return sql;
    }

    public String listByStaff(Map<String, Object> para) {
        String sql = new SQL() {{
            SELECT(BuildSql.select(Cases.class));
            FROM(BuildSql.tablename(Cases.class));
            WHERE(" staff_id =#{staff}");
        }}.toString();
        logger.info(sql);
        return sql;
    }

    public String listByCompany(Map<String, Object> para) {
        String sql = new SQL() {{
            SELECT(" count(*) as num ,COALESCE(sum(service_charge),0) as money");
            StringBuilder where = new StringBuilder();
            FROM(BuildSql.tablename(Cases.class));
            where.append("status = #{status}");
            if (UtilFun.isEmptyString((String) para.get("staff"))) {
                where.append(" and staff_id=#{staff} ");
            }
            if (para.get("company") != null) {
                where.append(" and company_id =#{company}");
            }


            WHERE(where.toString());
        }}.toString();
        logger.info(sql);
        return sql;
    }


    public String dynamicList(Cases cases) {
        String sql = new SQL() {{
            SELECT(BuildSql.select(Cases.class));
            FROM(BuildSql.tablename(Cases.class));
            StringBuilder where = new StringBuilder(" 1=1 ");
            if (cases.getStatus() != -1)
                where.append(" and status = #{status} ");
            if (UtilFun.isEmptyString(cases.getName()))
                where.append(" and name = #{name} ");
            if (UtilFun.isEmptyString(cases.getCustomerPhoneNumber()))
                where.append(" and customer_phone_number = #{customerPhoneNumber}");
            WHERE(where.toString());
        }}.toString();
        logger.info(sql);
        return sql;
    }

    public String groupCasesByCaseName() {
        String sql = new SQL() {{
            StringBuilder where = new StringBuilder(" status=0 and company_id=-1 ");
             SELECT(" count(*) count ,COALESCE(case_name,'无名称') as caseName ");
             FROM(BuildSql.tablename(Cases.class));
            GROUP_BY( "case_name");
             WHERE(where.toString());
        }}.toString();
        logger.info(sql);
        return sql;
    }

    public String randomAllot(Map<String,Object> para){
        String sql=new SQL(){{
            UPDATE(BuildSql.tablename(Cases.class));
            SET(" company_id = #{company} ");
            WHERE(" id in( select id from (select @rownum:=@rownum+1 as rownum,id " +
                    "from (SELECT @rownum:=0) r " +
                    ",t_case where case_name=#{name} and status = 0 and company_id=-1) as a where  rownum <= #{num})");
        }}.toString();
        logger.info(sql);
        return sql ;
    }


    public String randomToStaff(Map<String,Object> para){
        String sql=new SQL(){{
            UPDATE(BuildSql.tablename(Cases.class));
            SET(" staff_id=#{staff} ");
            WHERE(" id in(select id from (select @rownum:=@rownum+1 as rownum,id from (SELECT @rownum:=0) r ,t_case where status = 0 and company_id=#{company} and staff_id is null) as a where  rownum <= #{num} )");
        }}.toString();
        logger.info(sql);
        return sql;
    }

}
