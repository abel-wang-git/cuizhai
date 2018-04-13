package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Company;
import com.cch.cz.entity.Staff;
import com.cch.cz.service.CasesService;
import com.cch.cz.service.CompanyService;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
        model.addAttribute("coms",list);
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
     * 把悬着的地区的case分配个该公司
     *
     * @param areas   要分配的地区
     * @param company 公司的id
     * @return
     */
    @PostMapping(value = "/allot")
    @ResponseBody
    public AjaxReturn allotCompany(@RequestParam("area[]") List<String> areas,
                                   @RequestParam("company") String company) {
        /*根据城市给委案分配公司ID*/
        casesService.allotCaseToCompany(areas, company);
        return new AjaxReturn(0, "分配成功");
    }

    /**
     * 查找该公司未分配的case
     *
     * @param company 公司id
     * @return
     */
    @PostMapping(value = "/list/nostaff")
    @ResponseBody
    public Table noStaff(@RequestParam("company") Long company) {
        List<Cases> cases = casesService.listByCompanyNoStaff(company);

        return new Table(cases.size(), cases);
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
     * @param staff 员工
     * @return
     */
    @PostMapping(value = "/list/bystaff")
    @ResponseBody
    public Table listByStaff(@RequestParam("staff") String staff) {

        List<Cases> cases = casesService.listByStaff(staff);

        return new Table(cases.size(), cases);
    }

    /**
     * 撤案，留案，结案
     * @param ids
     * @param status
     * @param rethinDay
     * @return
     */
    @PostMapping(value = "/manager")
    @ResponseBody
    public AjaxReturn manager(@RequestParam("cases[]") Long[] ids, @RequestParam("status") int status, @RequestParam(value = "rethinDay", defaultValue = "0") int rethinDay) {
        casesService.managerCase(ids, status, rethinDay);
        return new AjaxReturn(0, "撤案成功");
    }

    /**
     * 委案管理查询
     * @param data
     * @return
     */
    @PostMapping(value = "/dynamic")
    @ResponseBody
    public List<Cases> manager(@RequestBody Cases data) {

        return casesService.dynamicList(data);
    }

    @PostMapping(value = "/random")
    @ResponseBody
    public AjaxReturn randomAllot(@RequestParam("company[]")String[] company){

        casesService.randomAllot(company);
        return new AjaxReturn(0,"分配成功");
    }

    @PostMapping(value = "/groupByname")
    @ResponseBody
    public Table groupByCaseName(){
        List<Map> list=casesService.groupByCaseName(null);
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }
}
