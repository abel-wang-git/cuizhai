package com.cch.cz.mapper;

import com.cch.cz.base.dao.BaseMapper;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import com.cch.cz.mapper.provider.StaffProvider;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/18.
 * 员工mapper
 */
@Mapper
public interface StaffMapper extends BaseMapper<Staff,String> {

    @SelectProvider(type = StaffProvider.class ,method = "listByCompany")
    List<Staff> listByCompany(@Param("company") String company);
    @Select("select s.name sName ,s.login_name as loginName,s.is_enable as isEnable, max(phone) phone ,max(urge_group) ugroup ,c.name cName , ifnull(sum(sum_arrears),0) as arrears  , count(ca.sum_arrears) size from t_staff as s left  join t_company c on s.company_id=c.id  left join t_case ca on( s.login_name = ca.staff_id and ca.status=3) group by login_name ,ca.status")
    List<Map> listStaff();
    @Select("select staff_id staffId, sum(sum_arrears) as arrears ,count(*) size  from t_case where staff_id is not null  group by staff_id")
    List<Map> listByStaff();

    @Select("select s.login_name sloginName,s.company_id scomId,s.is_enable sIs,s.name sName,s.phone sPhone,s.place sPlace,s.urge_group sGroup ,c.name cName \n" +
            "from t_staff as s,t_company as c \n" +
            "where\n" +
            "c.id = s.company_id\n" +
            "and c.name like #{company}")
    List<Map> listByCompanyccaa(@Param("company") String company);

}
