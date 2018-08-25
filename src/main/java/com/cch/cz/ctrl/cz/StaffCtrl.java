package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.entity.User;
import com.cch.cz.authority.entity.key.UserRoleKey;
import com.cch.cz.authority.service.RoleService;
import com.cch.cz.authority.service.UserService;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Company;
import com.cch.cz.entity.Staff;
import com.cch.cz.entity.enu.IsEnable;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.CompanyService;
import com.cch.cz.service.StaffService;
import com.cch.cz.service.UrgeGroupService;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/18.
 */
@Controller
@RequestMapping(value = "/staff")
public class StaffCtrl {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private StaffService staffService;
    @Resource
    private UserService userService;
    @Resource
    private CompanyService companyService;
    @Resource
    private RoleService roleService;
    @Resource
    private CasesService casesService;
    @Resource
    private UrgeGroupService urgeGroupService;

    @GetMapping(value = "/list")
    public  String list(Model model){
        return "/cz/staff/list";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Table hKlist(@RequestParam int page , @RequestParam int limit,@RequestParam(defaultValue = "") String where){
        Staff staff = (Staff) SecurityUtils.getSubject().getSession().getAttribute("staff");
        Staff wherea = JSON.parseObject(where,Staff.class);
        if(wherea==null){
            wherea= new Staff();
            wherea.setCompanyId(staff.getCompanyId());
        }
        PageHelper.startPage(page,limit);
        Page<Map> staffs =(Page<Map>) staffService.listStaff(wherea.getCompanyId());
        Table table = new Table();
        List<Map> staffa = staffService.listByStaff();
        for (Map map : staffs) {
            String id = (String) map.get("loginName");
            for (Map map1 : staffa) {
                if (id.equals(map1.get("staffId"))){
                    map.put("huikuan",Math.round(((double)map.get("arrears")/(double)map1.get("arrears"))*10000)/100d+"%");
                }
            }
        }
        table.setData(staffs);
        table.setCount((int) staffs.getTotal());
        return table;
    }

    @PostMapping(value = "/addRole")
    @ResponseBody
    public AjaxReturn addRole(@RequestBody List<UserRoleKey> data){
        userService.saveRoles(data);
        return new AjaxReturn(0,"添加成功");
    }


    @GetMapping(value = "/toAdd")
    public String toAdd(Model model){
        Staff staff = (Staff) SecurityUtils.getSubject().getSession().getAttribute("staff");
        model.addAttribute(new Staff());
        model.addAttribute("companys", staff.getCompanyId() == null ? companyService.findAll() : companyService.listBystaff(staff));
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("group",urgeGroupService.findAll());

        return "/cz/staff/add";
    }

    @PostMapping(value = "/add",produces="application/json;charset=UTF-8")
    @ResponseBody
    public AjaxReturn add(@RequestBody Staff  data){
        try {
            staffService.save(data);
            return new AjaxReturn(0,"添加成功");
        }catch (Exception e){
            return new AjaxReturn(1,"添加失败");
        }
    }

    @PostMapping(value = "/listbycompany")
    @ResponseBody
    public Table listByCompany(@RequestParam("company")String company){
        List<Staff> staffs= staffService.listByCompany(company);
        Table table = new Table();
        table.setData(staffs);
        table.setCount(staffs.size());
        return table;
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public AjaxReturn add(@RequestParam String  data,@RequestParam String oldId){
            Staff staff = JSON.parseObject(data,Staff.class);
            Cases cases = new Cases();
            cases.setStaffId(staff.getLoginName());
            List<Cases> list = casesService.findByEntity(cases);
            if (UtilFun.isEmptyList(list)){
                Map map = new HashMap();
                map.put("cases",list);
                return new AjaxReturn(0,"该催收员存在案件无法修改",map);
            }else{
//                staff.setIsEnable(IsEnable.DISENABLE.value());
                staffService.update(staff,oldId);
                return new AjaxReturn(1,"修改成功");
            }

    }

    @PostMapping(value = "/isenable")
    @ResponseBody
    public AjaxReturn isenable(@RequestParam String staff) {
        Staff staff1 = JSON.parseObject(staff, Staff.class);
        Cases c = new Cases();
        c.setStaffId(staff1.getLoginName());
        List<Cases> list = casesService.findByEntity(c);
        if (UtilFun.isEmptyList(list)) {
            Map data = new HashMap();
            data.put("cases", list);
            return new AjaxReturn(1, "该催收员存在未处理的案件", data);
        } else {
            staff1.setIsEnable(IsEnable.DISENABLE.value());
            staffService.update(staff1);
            return new AjaxReturn(0, "操作成功");
        }
    }

    @PostMapping(value = "/disenable")
    @ResponseBody
    public AjaxReturn disenable(@RequestParam("cases") String casess, @RequestParam("staff[]") String[] staff) {
        List<Cases> cases = JSON.parseArray(casess, Cases.class);
        staffService.disable(cases, staff);
        return new AjaxReturn(0, "操作成功");
    }

    @PostMapping(value = "/randomTooStaff")
    @ResponseBody
    public Table rts(@RequestParam int page,@RequestParam int limit){
        PageHelper.startPage(page,limit);
        Table table = new Table();
        table.setData(staffService.findAll());
        table.setCount(staffService.count(new Staff()).intValue());
        return table;
    }


    /**
     * 获取岗位下来列表
     */
    @PostMapping(value = "/getRole")
    @ResponseBody
    public AjaxReturn getRole(){
        Staff staff = (Staff) SecurityUtils.getSubject().getSession().getAttribute("staff");
        Map map = new HashMap();
        map.put("roles",(staff.getPlace()==null)?roleService.findAll():roleService.findByStaff(staff));
        return new AjaxReturn(0,"",map);
    }
    /**
     * 获取公司下拉列表
     */

    @PostMapping(value = "/getCompany")
    @ResponseBody
    public AjaxReturn getCompany(){
        Staff staff = (Staff) SecurityUtils.getSubject().getSession().getAttribute("staff");
        List<Company> companies = companyService.listBystaff(staff);
        Map map = new HashMap();
        map.put("companies",companies);
        return new AjaxReturn(0,"",map);
    }
}
