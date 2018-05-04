package com.cch.cz.service;

import com.cch.cz.base.service.BaseService;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/18.
 * 员工service
 */
public interface CasesService extends BaseService<Cases,Long>  {


    void expCase(List<Cases> casesList);

    List<Map> groupCasesByArea();

    void allotCaseToCompany(List<String> data, String company);

    void allotStaff(List<Cases> cases, Staff staff);

    /**
     * 根据公司id查找未分配的case
     * @param company
     */
    List<Cases> listByCompanyNoStaff(Long company);

    /**
     * 查找某个员工的cases
     * @param staff
     * @return
     */
    List<Cases> listByStaff(String staff);
    /**
     * 查找某个员工的cases总数
     * @param staff
     * @return
     */
    Long countByStaff( String staff);

    /**
     *
     * @param company 公司id
     * @param status 状态id
     * @return
     */
    List<Map> listByCompany(Long company,String staff,int status );

    /**
     *留案 撤案 结案
     * @param id 操作的cases
     * @param day 留案期
     * @param status  撤案=1 留案=2  结案=3
     */
    void managerCase(Long[] id, int status, int day);

    /**
     * 动态查询
     * @param cases
     */
    List<Cases> dynamicList(Cases cases);


    /**
     * 分配ｃａｓｅ　根据案件名称
     * @param company 公司ｉｄ
     * @param cases　要分配的ｃａｓｅ数量和案件名称
     */
    void randomAllot(String[] company,List<Map> cases);

    /**
     * 按案件名称分组查询
     */
    List<Map> groupByCaseName();

    /**
     *
     * @param staff，
     */
    void randomToStaff(String[] staff,int num,Long company);

    /**
     * 调案
     * @param ids case
     * @param staffid 要分配到的员工
     */
    void adjust(String[] ids, String staffid);
}
