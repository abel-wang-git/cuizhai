package com.cch.cz.mapper.provider;

import com.cch.cz.base.dao.BuildSql;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.AdjustLog;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import jdk.nashorn.internal.objects.annotations.Where;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CasesProvider extends BaseProvider<Cases, Long> {
    org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public String listByCompanyNoStaff(Cases para) {
        String sql = new SQL() {{
            SELECT(BuildSql.select(Cases.class));
            FROM(BuildSql.tablename(Cases.class));
            StringBuilder where = new StringBuilder(" 1=1 and staff_id is null");
            if (null!=para.getCompanyId())
                where.append(" and company_id = #{companyId}");
            if (null != para.getStatus() && para.getStatus() != -1)
                where.append(" and status = #{status} ");
            if  (UtilFun.isEmptyString(para.getContractNum()))
                where.append(" and contract_num= #{contractNum} ");
            if(UtilFun.isEmptyString(para.getCustomerAddress()))
                where.append(" and customer_address like #{customerAddress}");
            if(UtilFun.isEmptyString(para.getAppointData()))
                where.append(" and DATE_FORMAT(left(appoint_data,10),'%Y-%m-%d') =  DATE_FORMAT(left(TRIM(#{appointData}),10),'%Y-%m-%d')");
            if (UtilFun.isEmptyString(para.getArrears()))
                where.append(" and sum_arrears between #{arrears}+0 and #{sumArrears}+0");
            WHERE(where.toString());
        }}.toString();
        logger.info(sql);
        return sql;
    }

    public String listByStaff(Cases cases) {
        String sql = new SQL() {{
            StringBuilder where = new StringBuilder();
            SELECT(BuildSql.select(Cases.class));
            FROM(BuildSql.tablename(Cases.class));
            where.append(" staff_id =#{staffId}");
            if(UtilFun.isEmptyString(cases.getName()))
                where.append( " and name = #{name}");
            if(UtilFun.isEmptyString(cases.getCustomerPhoneNumber()))
                where.append( " and customer_phone_number = #{customerPhoneNumber}");
            if(UtilFun.isEmptyString(cases.getCustomerAddress()))
                where.append(" and customer_address like #{customerAddress}");
            if (UtilFun.isEmptyString(cases.getIdCard()))
                where.append(" and id_card = #{idCard}");
            if (UtilFun.isEmptyString(cases.getContractNum()))
                where.append(" and contract_num= #{contractNum} ");
            if (UtilFun.isEmptyString(cases.getLastUrge())) {
                if (cases.getLastUrge().equals("yes"))
                    where.append(" and last_urge is not null ");
                if (cases.getLastUrge().equals("no"))
                    where.append("and last_urge is  null");
            }
            if (UtilFun.isEmptyString(cases.getOverrangingDay())) {
                String d = cases.getOverrangingDay();
                switch (d) {
                    case "M1":
                        where.append(" and overranging_day > 0 and overranging_day <31 ");
                        break;
                    case "M2":
                        where.append(" and overranging_day > 30 and overranging_day <61 ");
                        break;
                    case "M3":
                        where.append(" and overranging_day > 60 and overranging_day <91 ");
                        break;
                    case "M4-M6":
                        where.append(" and overranging_day > 90 and overranging_day <181 ");
                        break;
                    case "M6-M12":
                        where.append(" and overranging_day > 180 and overranging_day <360 ");
                        break;
                    case "M12":
                        where.append(" and overranging_day > 360 ");
                        break;

                }
            }
            WHERE(where.toString());

        }}.toString();
        logger.info(sql);
        return sql;
    }

    public String listByCompany(Map<String, Object> para) {
        String sql = new SQL() {{
            SELECT(" count(*) as num ,COALESCE(sum(service_charge),0) as money");
            StringBuilder where = new StringBuilder(" 1=1");
            FROM(BuildSql.tablename(Cases.class));

            if(Integer.parseInt(para.get("status").toString())!=-1)
            where.append(" and status = #{status}");
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
            if (null != cases.getStatus() && cases.getStatus() != -1)
                where.append(" and status = #{status} ");
            if (UtilFun.isEmptyString(cases.getName()))
                where.append(" and name = #{name} ");
            if (UtilFun.isEmptyString(cases.getCustomerPhoneNumber()))
                where.append(" and customer_phone_number = #{customerPhoneNumber}");
            if  (UtilFun.isEmptyString(cases.getContractNum()))
                where.append(" and contract_num= #{contractNum} ");
            if (null!=cases.getCompanyId()&&cases.getCompanyId()==-1)
                where.append(" and company_id = -1");
            if(UtilFun.isEmptyString(cases.getCustomerAddress()))
                where.append(" and customer_address like #{customerAddress}");
            if(UtilFun.isEmptyString(cases.getAppointData()))
                where.append(" and DATE_FORMAT(left(appoint_data,10),'%Y-%m-%d') =  DATE_FORMAT(left(TRIM(#{appointData}),10),'%Y-%m-%d')");
            if (UtilFun.isEmptyString(cases.getIdCard()))
                where.append(" and id_card = #{idCard}");
            if (UtilFun.isEmptyString(cases.getStaffId()))
                where.append(" and staff_id = #{StaffId}");
            if (UtilFun.isEmptyString(cases.getArrears()))
                where.append(" and sum_arrears between #{arrears}+0 and #{sumArrears}+0");
            
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
    public String listByAdjust() {
        String sql = new SQL() {{
            StringBuilder where = new StringBuilder("1=1");
             SELECT("DATE_FORMAT(date,'%Y%m%d') as date,id,case_id as caseId,old_staff as oldstaff,new_staff as newstaff ");
             FROM(BuildSql.tablename(AdjustLog.class));
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

    public String isUrge(Map<String, Object> para) {
        String sql = new SQL() {{
            SELECT(" staff_id as staff ,count(*) as count ,sum(arrears) as money ");
            FROM(BuildSql.tablename(Cases.class));
            StringBuilder where = new StringBuilder(" staff_id is not null");
            if (para.get("company") != null)
                where.append("  and company_id = #{company} ");
            if (UtilFun.isEmptyString((String) para.get("isUrge")))
                where.append(" and last_urge is not null  ");
            if (UtilFun.isEmptyString((String) para.get("staff")))
                where.append(" and staff_id=#{staff} ");

            WHERE(where.toString());
            GROUP_BY(" staff_id ");
        }}.toString();
        logger.info(sql);
        return sql;
    }


}
