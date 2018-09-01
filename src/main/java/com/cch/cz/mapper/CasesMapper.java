package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import com.cch.cz.entity.UrgeRecord;
import com.cch.cz.mapper.provider.CasesProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 委案
 */
@Mapper
public interface CasesMapper extends BaseMapper<Cases,Long> {
    /**
     * 按地区分组
     * @return
     */
    @Select("SELECT count(*) count,left(customer_residence_address,instr(customer_residence_address,'市')) as area FROM t_case  where company_id =-1 and status=0 group by area")
    @Results({
            @Result(property = "count", column = "count"),
            @Result(property = "area",column = "area"),
    })
    List<Map> groupCasesByArea();

    /**
     * 按案件名称分组
     * @return 案件名称 和该名称的案件数量
     */
    @SelectProvider(type = CasesProvider.class,method = "groupCasesByCaseName")
    @Results({
            @Result(property = "count", column = "count"),
            @Result(property = "caseName",column = "caseName"),
    })
    List<Map> groupCasesByCaseName();
    /**
     * 调案记录
     * @return
     */
    @SelectProvider(type = CasesProvider.class,method = "listByAdjust")
    List<Map> listByAdjust();


    /**
     * 按地区更新company
     * @param company
     * @param area
     */
    @Update("UPDATE t_case SET company_id=#{company} WHERE customer_residence_address like #{area} and status=0")
    void allotCompany(@Param("company") String company,@Param("area") String area);

    /**
     * 按id更新staffid
     * @param caseId
     * @param staff
     */
    @Update("UPDATE t_case SET staff_id=#{staff} WHERE id=#{case} and status=0")
    void allotStaff(@Param("case") Long caseId, @Param("staff") String staff);

    /**
     *
     * @param company
     * @return 某个公司还未分配的case
     */
    @SelectProvider(type = CasesProvider.class,method = "listByCompanyNoStaff")
    List<Cases> listByCompanyNoStaff(Cases company);

    /**
     *
     * @param cases
     * @return 属于某个员工的case
     *
     */
    @SelectProvider(type = CasesProvider.class,method = "listByStaff")
    List<Cases> listByStaff(Cases cases);


    @SelectProvider(type = CasesProvider.class,method = "listByCompany")
    @Results({
            @Result(property = "num", column = "num"),
            @Result(property = "money",column = "money"),
    })
    List<Map> listByCompany(@Param("company") Long company,@Param("staff") String staff, @Param("status") int status);

    @SelectProvider(type = CasesProvider.class,method = "dynamicList")
    List<Cases> dynamicList(Cases cases);

    @UpdateProvider(type = CasesProvider.class,method = "randomAllot")
    void randomAllot(@Param("company") String company,@Param("name")String name,@Param("num")int num);


    @Update("update t_case set staff_id = #{staffid} where id in (${ids})")
    void adjust(@Param("ids") String ids, @Param("staffid") String staffid);

    @Update("update t_case set status = 1 where stop_appoint = ${now} and status =0")
    void autoRevoke(@Param("now")String now);

    @Select("SELECT staff_id from t_case WHERE id_card='${idcard}' and staff_id is not null  limit 1 ,1; ")
    String findStaffByIdcard(@Param("idcard") String idcard);

    //已跟进未跟进
    @SelectProvider(type = CasesProvider.class, method = "isUrge")
    List<Map> isUrge(@Param("isUrge") String isUrge, @Param("company") Long company, @Param("staff") String staff);

    @Select("SELECT id as id ,contract_num as contractNum ,appoint_company as appointCompany ,appoint_data as appointData ,stop_appoint as stopAppoint ,city as city ,region as region ,customerid as CUSTOMERID ,name as name ,sex as sex ,contracts_num as contractsNum ,id_card as idCard ,contract_apply_date as contractApplyDate ,loan_type as loanType ,loan_principal as loanPrincipal ,installment_money as installmentMoney ,installment_num as installmentNum ,repayment_money as repaymentMoney ,deadline as deadline ,overranging_day as overrangingDay ,cancel_instalments as cancelInstalments ,nnpaid_installment as nnpaidInstallment ,late_fee as lateFee ,arrears as arrears ,service_charge as serviceCharge ,sum_arrears as sumArrears ,repayment_account as repaymentAccount ,account_master as accountMaster ,bank as bank ,commodity as commodity ,brand as brand ,pos_place as posPlace ,customer_phone_number as customerPhoneNumber ,customer_address as customerAddress ,customer_office_phone as customerOfficePhone ,customer_company as customerCompany ,customer_company_address as customerCompanyAddress ,customer_department as customerDepartment ,customer_residence_phone as customerResidencePhone ,customer_residence_address as customerResidenceAddress ,customer_mail as customerMail ,withholding as withholding ,withholding_account as withholdingAccount ,company_id as companyId ,staff_id as StaffId ,last_urge as lastUrge ,revoke_date as revokeDate ,end_date as endDate ,rethin_date as rethinDate ,rethin_day as rethinDay ,status as status ,type as type ,case_name as caseName ,end_reason as endReason ,retain_reason as retainReason ,revoke_reason as revokeReason ,supplement as supplement  FROM t_case WHERE  id_card in ( ${idcs}) and staff_id is not null")
    List<Cases> findinIdcard(@Param("idcs") String idcs);

    @Select("SELECT id as id ,contract_num as contractNum ,appoint_company as appointCompany ,appoint_data as appointData ,stop_appoint as stopAppoint ,city as city ,region as region ,customerid as CUSTOMERID ,name as name ,sex as sex ,contracts_num as contractsNum ,id_card as idCard ,contract_apply_date as contractApplyDate ,loan_type as loanType ,loan_principal as loanPrincipal ,installment_money as installmentMoney ,installment_num as installmentNum ,repayment_money as repaymentMoney ,deadline as deadline ,overranging_day as overrangingDay ,cancel_instalments as cancelInstalments ,nnpaid_installment as nnpaidInstallment ,late_fee as lateFee ,arrears as arrears ,service_charge as serviceCharge ,sum_arrears as sumArrears ,repayment_account as repaymentAccount ,account_master as accountMaster ,bank as bank ,commodity as commodity ,brand as brand ,pos_place as posPlace ,customer_phone_number as customerPhoneNumber ,customer_address as customerAddress ,customer_office_phone as customerOfficePhone ,customer_company as customerCompany ,customer_company_address as customerCompanyAddress ,customer_department as customerDepartment ,customer_residence_phone as customerResidencePhone ,customer_residence_address as customerResidenceAddress ,customer_mail as customerMail ,withholding as withholding ,withholding_account as withholdingAccount ,company_id as companyId ,staff_id as StaffId ,last_urge as lastUrge ,revoke_date as revokeDate ,end_date as endDate ,rethin_date as rethinDate ,rethin_day as rethinDay ,status as status ,type as type ,case_name as caseName ,end_reason as endReason ,retain_reason as retainReason ,revoke_reason as revokeReason ,supplement as supplement  FROM t_case WHERE  id_card in ( ${coms}) and staff_id is not null")
    List<Cases> findincom(@Param("coms") String coms);

    @Select("SELECT id as id ,contract_num as contractNum ,appoint_company as appointCompany ,appoint_data as appointData ,stop_appoint as stopAppoint ,city as city ,region as region ,customerid as CUSTOMERID ,name as name ,sex as sex ,contracts_num as contractsNum ,id_card as idCard ,contract_apply_date as contractApplyDate ,loan_type as loanType ,loan_principal as loanPrincipal ,installment_money as installmentMoney ,installment_num as installmentNum ,repayment_money as repaymentMoney ,deadline as deadline ,overranging_day as overrangingDay ,cancel_instalments as cancelInstalments ,nnpaid_installment as nnpaidInstallment ,late_fee as lateFee ,arrears as arrears ,service_charge as serviceCharge ,sum_arrears as sumArrears ,repayment_account as repaymentAccount ,account_master as accountMaster ,bank as bank ,commodity as commodity ,brand as brand ,pos_place as posPlace ,customer_phone_number as customerPhoneNumber ,customer_address as customerAddress ,customer_office_phone as customerOfficePhone ,customer_company as customerCompany ,customer_company_address as customerCompanyAddress ,customer_department as customerDepartment ,customer_residence_phone as customerResidencePhone ,customer_residence_address as customerResidenceAddress ,customer_mail as customerMail ,withholding as withholding ,withholding_account as withholdingAccount ,company_id as companyId ,staff_id as StaffId ,last_urge as lastUrge ,revoke_date as revokeDate ,end_date as endDate ,rethin_date as rethinDate ,rethin_day as rethinDay ,status as status ,type as type ,case_name as caseName ,end_reason as endReason ,retain_reason as retainReason ,revoke_reason as revokeReason ,supplement as supplement  FROM t_case WHERE  id_card in ( ${idcs}) and staff_id is null")
    List<Cases> findinIdcardno(@Param("idcs") String idcs);

    @Select("SELECT id as id ,contract_num as contractNum ,appoint_company as appointCompany ,appoint_data as appointData ,stop_appoint as stopAppoint ,city as city ,region as region ,customerid as CUSTOMERID ,name as name ,sex as sex ,contracts_num as contractsNum ,id_card as idCard ,contract_apply_date as contractApplyDate ,loan_type as loanType ,loan_principal as loanPrincipal ,installment_money as installmentMoney ,installment_num as installmentNum ,repayment_money as repaymentMoney ,deadline as deadline ,overranging_day as overrangingDay ,cancel_instalments as cancelInstalments ,nnpaid_installment as nnpaidInstallment ,late_fee as lateFee ,arrears as arrears ,service_charge as serviceCharge ,sum_arrears as sumArrears ,repayment_account as repaymentAccount ,account_master as accountMaster ,bank as bank ,commodity as commodity ,brand as brand ,pos_place as posPlace ,customer_phone_number as customerPhoneNumber ,customer_address as customerAddress ,customer_office_phone as customerOfficePhone ,customer_company as customerCompany ,customer_company_address as customerCompanyAddress ,customer_department as customerDepartment ,customer_residence_phone as customerResidencePhone ,customer_residence_address as customerResidenceAddress ,customer_mail as customerMail ,withholding as withholding ,withholding_account as withholdingAccount ,company_id as companyId ,staff_id as StaffId ,last_urge as lastUrge ,revoke_date as revokeDate ,end_date as endDate ,rethin_date as rethinDate ,rethin_day as rethinDay ,status as status ,type as type ,case_name as caseName ,end_reason as endReason ,retain_reason as retainReason ,revoke_reason as revokeReason ,supplement as supplement  FROM t_case WHERE  id_card in ( ${coms}) and staff_id is null")
    List<Cases> findincomno(@Param("coms") String coms);
    @Select("SELECT id as id ,contract_num as contractNum ,appoint_company as appointCompany ,appoint_data as appointData ,stop_appoint as stopAppoint ,city as city ,region as region ,customerid as CUSTOMERID ,name as name ,sex as sex ,contracts_num as contractsNum ,id_card as idCard ,contract_apply_date as contractApplyDate ,loan_type as loanType ,loan_principal as loanPrincipal ,installment_money as installmentMoney ,installment_num as installmentNum ,repayment_money as repaymentMoney ,deadline as deadline ,overranging_day as overrangingDay ,cancel_instalments as cancelInstalments ,nnpaid_installment as nnpaidInstallment ,late_fee as lateFee ,arrears as arrears ,service_charge as serviceCharge ,sum_arrears as sumArrears ,repayment_account as repaymentAccount ,account_master as accountMaster ,bank as bank ,commodity as commodity ,brand as brand ,pos_place as posPlace ,customer_phone_number as customerPhoneNumber ,customer_address as customerAddress ,customer_office_phone as customerOfficePhone ,customer_company as customerCompany ,customer_company_address as customerCompanyAddress ,customer_department as customerDepartment ,customer_residence_phone as customerResidencePhone ,customer_residence_address as customerResidenceAddress ,customer_mail as customerMail ,withholding as withholding ,withholding_account as withholdingAccount ,company_id as companyId ,staff_id as StaffId ,last_urge as lastUrge ,revoke_date as revokeDate ,end_date as endDate ,rethin_date as rethinDate ,rethin_day as rethinDay ,status as status ,type as type ,case_name as caseName ,end_reason as endReason ,retain_reason as retainReason ,revoke_reason as revokeReason ,supplement as supplement  FROM t_case WHERE  last_urge like concat(#{lastUrge},'%')")
    List<Cases> todayUrge(@Param("lastUrge") String s);
}
