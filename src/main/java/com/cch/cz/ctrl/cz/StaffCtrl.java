package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.cch.cz.authority.entity.key.UserRoleKey;
import com.cch.cz.authority.service.RoleService;
import com.cch.cz.authority.service.UserService;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.common.UtilFun;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Staff;
import com.cch.cz.entity.enu.IsEnable;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.CompanyService;
import com.cch.cz.service.StaffService;
import com.cch.cz.service.UrgeGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
         model.addAttribute(staffService.findAll());
        return "/cz/staff/list";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Table hKlist(@RequestParam int page , @RequestParam int limit){
        List<Map> staffs =  staffService.listStaff();
        PageHelper.startPage(page,limit);
        Table table = new Table();
        Page<Map> staffa = (Page<Map>) staffService.listByStaff();
        for (Map map : staffs) {
            String id = (String) map.get("loginName");
            for (Map map1 : staffa) {
                if (id.equals(map1.get("staffId"))){
                    map.put("huikuan",Math.round(((double)map.get("arrears")/(double)map1.get("arrears"))*10000)/100d+"%");
                }
            }
        }
        table.setData(staffs);
        table.setCount((int) staffa.getTotal());
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

        model.addAttribute(new Staff());
        model.addAttribute("companys",companyService.findAll());
        model.addAttribute("roles",roleService.findAll());
        model.addAttribute("group",urgeGroupService.findAll());

        return "/cz/staff/add";
    }

    @GetMapping(value = "/toAddcompany")
    public String toAddcompany(){
        return "/cz/staff/addcompany";
    }



    //查询公司下的员工
    @PostMapping(value = "/companycom")
    @ResponseBody
    public  Table comp(@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "0") int limit,
                       @RequestParam String data){
        PageHelper.startPage(page, limit);
        Page<Map> staffq = (Page<Map>) staffService.listBycompanyccaa(data);
        return new Table((int) staffq.getTotal(), staffq);
    }



    @PostMapping(value = "/add",produces="application/json;charset=UTF-8")
    @ResponseBody
    public AjaxReturn add(@RequestBody Staff  data){
        try {
            staffService.save(data);
            return new AjaxReturn(0,"添加成功");
        }catch (Exception e){
            e.printStackTrace();
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
        try {
            staffService.update(JSON.parseObject(data,Staff.class),oldId);
            return new AjaxReturn(0,"修改成功");
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxReturn(1,"修改失败");
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
        return new AjaxReturn(0, "分配成功");
    }



}
