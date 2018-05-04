package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.base.dao.provider.BaseProvider;
import com.cch.cz.entity.Cases;
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
    List<Cases> listByCompanyNoStaff(@Param("company") Long company);

    /**
     *
     * @param staff 员工id
     * @return 属于某个员工的case
     *
     */
    @SelectProvider(type = CasesProvider.class,method = "listByStaff")
    List<Cases> listByStaff(@Param("staff") String staff);

    @SelectProvider(type = CasesProvider.class,method = "countByStaff")
    Long countByStaff(@Param("staff") String staff);

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

    @UpdateProvider(type = CasesProvider.class,method = "randomToStaff")
    void randomToStaff(@Param("staff") String staff,@Param("num") int num,@Param("company") Long company);

    @Update("update t_case set staff_id = #{staffid} where id in (#{ids})")
    void adjust(@Param("ids") String ids, @Param("staffid") String staffid);
}
