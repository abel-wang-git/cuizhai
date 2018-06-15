package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.*;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.CompanyService;
import com.cch.cz.service.StaffService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 * 委案管理
 */
@Controller
@RequestMapping(value = "/case")
public class CaseCtrl {
    @Resource
    private CasesService casesService;
    @Resource
    private CompanyService companyService;
    @Resource
    private StaffService staffService;

    @GetMapping(value = "/up")
    public String toCase() {
        return "/cz/cases/case";
    }

    @PostMapping(value = "/exp")
    @ResponseBody
    public AjaxReturn expCase(@RequestBody List<Cases> data) {

        casesService.expCase(data);

        return new AjaxReturn(0, "导入成功");
    }

    @PostMapping(value = "/noallot")
    @ResponseBody
    public Table noAllotCase(@RequestParam int page, @RequestParam int limit) {
        PageHelper.startPage(page, limit);
        Table table = new Table();
        table.setData(casesService.findAll());
        table.setCount(casesService.count(new Cases()).intValue());
        return table;
    }

    @GetMapping(value = "/allot")
    public String allotCase(Model model) {
        Staff staff = (Staff) SecurityUtils.getSubject().getSession().getAttribute("staff");
        Company company = companyService.findOne(staff.getCompanyId());

        model.addAttribute((company == null ? new Company() : company));
        List<Company> list = companyService.listBystaff(staff);

        List<Staff> staffs = staffService.listByCompany((company == null ? null : Long.toString(company.getId())));
        Cases acases = new Cases();
        acases.setCompanyId(staff.getCompanyId());
        List<Cases> cases = casesService.listByCompanyNoStaff(acases);
        model.addAttribute("staffs", staffs);

        model.addAttribute("coms", list);
        model.addAttribute("cases", cases);
        return "/cz/cases/allot";
    }

    /**
     * @return 按地区分组后的case
     */
    @PostMapping(value = "/grouparea")
    @ResponseBody
    public Table groupArea() {
        List list = casesService.groupCasesByArea();
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }

    /**
     *
     *
     */
    @PostMapping(value = "/allot")
    @ResponseBody
    public AjaxReturn allotCompany(@RequestParam("checks") String checks,
                                   @RequestParam("company") String company) {
        List<Cases> cases = JSON.parseArray(checks, Cases.class);
        /*根据城市给委案分配公司ID*/
        casesService.allotToCompany(cases, company);
        return new AjaxReturn(0, "分配成功");
    }

    /**
     * 查找该公司未分配的case
     *
     * @param data case
     * @return
     */
    @PostMapping(value = "/list/nostaff")
    @ResponseBody
    public Table noStaff(@RequestParam int page, @RequestParam int limit,
                         @RequestParam("data") String data) {
        PageHelper.startPage(page, limit);
        Cases cases = JSON.parseObject(data, Cases.class);
        Page<Cases> caseslist = (Page<Cases>) casesService.listByCompanyNoStaff(cases);
        return new Table((int) caseslist.getTotal(), caseslist);
    }

    /**
     * 分配给员工
     *
     * @param cases
     * @param staff
     * @return
     */
    @PostMapping(value = "/allotstaff")
    @ResponseBody
    public AjaxReturn allotStaff(@RequestParam("cases") String cases,
                                 @RequestParam("staff") String staff) {
        /*根据城市给委案分配公司ID*/
        casesService.allotStaff(JSON.parseArray(cases, Cases.class), JSON.parseObject(staff, Staff.class));
        return new AjaxReturn(0, "分配成功");
    }


    /**
     * 查找属于该公司的case
     *
     * @param cases 条件
     * @return
     */
    @PostMapping(value = "/list/bystaff")
    @ResponseBody
    public Table listByStaff(@RequestParam() int page,
                             @RequestParam() int limit,
                             @RequestParam("staff") String cases) {
        Cases where = JSON.parseObject(cases, Cases.class);
        PageHelper.startPage(page, limit);
        List<Cases> caseslist = casesService.listByStaff(where);
        Page<Cases> pages = (Page<Cases>) caseslist;

        return new Table((int) pages.getTotal(), caseslist);
    }

    /**
     * 撤案，留案，结案
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/manager")
    @ResponseBody
    public AjaxReturn manager(@RequestParam("cases") String ids) {
        List<Cases> casess = JSON.parseArray(ids, Cases.class);
        casesService.managerCase(casess);
        return new AjaxReturn(0, "操作成功");
    }

    /**
     * 委案管理查询
     *
     * @param data
     * @return
     */
    @PostMapping(value = "/dynamic")
    @ResponseBody
    public Table dynamic(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "0") int limit,
                         @RequestParam String data) {
        PageHelper.startPage(page, limit);
        Page<Cases> list = (Page<Cases>) casesService.dynamicList(JSON.parseObject(data, Cases.class));


        return new Table((int) list.getTotal(), list);
    }

    @PostMapping(value = "/random")
    @ResponseBody
    public AjaxReturn randomAllot(@RequestParam("companies") String casename, @RequestParam("names[]") String[] company) {
        List<Map> cases = JSONArray.parseArray(casename, Map.class);
        casesService.randomAllot(company, cases);
        return new AjaxReturn(0, "分配成功");
    }

    /**
     * 随机分配到员工
     *
     * @param staff
     * @return
     */
    @PostMapping(value = "/randomtostaff")
    @ResponseBody
    public AjaxReturn randomToStaff(@RequestParam("cases") String casess, @RequestParam("staff[]") String[] staff) {
        Staff curr = (Staff) SecurityUtils.getSubject().getSession().getAttribute("staff");

        List<Cases> cases = JSON.parseArray(casess, Cases.class);

        casesService.randomToStaff(staff, cases);

        return new AjaxReturn(0, "分配成功");
    }

    @PostMapping(value = "/groupByname")
    @ResponseBody
    public Table groupByCaseName() {
        List<Map> list = casesService.groupByCaseName();
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }

    /**
     * 调案
     */
    @PostMapping(value = "/adjust")
    @ResponseBody
    public AjaxReturn adjust(@RequestParam("cases") String ids, @RequestParam("staff") String staffid) {
        List<Cases> cases = JSON.parseArray(ids, Cases.class);
        casesService.adjust(cases, staffid);
        return new AjaxReturn(0, "调案成功");
    }

    /**
     * 调案记录
     *
     * @return
     */
    @PostMapping(value = "/listByAdjust")
    @ResponseBody
    public Table listByAdjust() {
        List<Map> list = casesService.listByAdjust();
        List<Map> staffs= new ArrayList<>();
        for(Map map : list){
            String oldstaff = (String)map.get("oldstaff");
            String newstaff = (String)map.get("newstaff");
            Staff staff =staffService.findOne(oldstaff);
            Staff staff1 =staffService.findOne(newstaff);
            map.put("oldstaff",staff.getName());
            map.put("newstaff",staff1.getName());
            staffs.add(map);
        }
        Table table = new Table();
        table.setData(staffs);
        table.setCount(staffs.size());
        return table;
    }




    @PostMapping(value = "/noallotcom")
    @ResponseBody
    public Table listNoAllot() {
        Cases cases = new Cases();
        cases.setCompanyId(-1L);
        Table table = new Table();
        List<Cases> noAllotCom = casesService.dynamicList(cases);
        table.setData(noAllotCom);
        table.setCount(noAllotCom.size());
        return table;
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public AjaxReturn update(@RequestParam String cases ) {
        casesService.update(JSON.parseObject(cases,Cases.class));
        return new AjaxReturn(0,"操作成功");
    }

}
