package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.Cases;
import com.cch.cz.entity.Company;
import com.cch.cz.entity.Staff;
import com.cch.cz.service.CasesService;
import com.github.pagehelper.PageHelper;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @GetMapping(value = "/up")
    public String toCase(){
        return "/cz/cases/case";
    }

    @PostMapping(value = "/exp")
    @ResponseBody
    public AjaxReturn expCase(@RequestBody List<Cases> data){

        casesService.expCase(data);

        return new AjaxReturn(0,"导入成功");
    }

    @PostMapping(value = "/noallot")
    @ResponseBody
    public Table noAllotCase(@RequestParam int page , @RequestParam int limit){

        PageHelper.startPage(page,limit);
        Table table = new Table();
        table.setData(casesService.findAll());
        table.setCount(casesService.count(new Cases()).intValue());
        return table;
    }

    @GetMapping(value = "/allot")
    public String allotCase(){
        return "/cz/cases/allot";
    }

    @PostMapping(value = "/grouparea")
    @ResponseBody
    public Table groupArea(){
        List list=casesService.groupCasesByArea();
        Table table = new Table();
        table.setData(list);
        table.setCount(list.size());
        return table;
    }

    @PostMapping(value = "/allot")
    @ResponseBody
    public AjaxReturn allotCompany(@RequestParam("area[]")List<String>  areas,
                                @RequestParam("company")String  company){
        /*根据城市给委案分配公司ID*/
        casesService.allotCaseToCompany(areas,company);
        return new AjaxReturn(0,"分配成功");
    }


    @PostMapping(value = "/list/nostaff")
    @ResponseBody
    public Table allotStaff(@RequestParam("company") Long company){
       List<Cases> cases= casesService.listByCompanyNoStaff(company);

        return new Table(cases.size(),cases);
    }

    /**
     * 分配给员工
     * @param cases
     * @param staff
     * @return
     */
    @PostMapping(value = "/allotstaff")
    @ResponseBody
    public AjaxReturn allotStaff(@RequestParam("cases")String cases,
                                 @RequestParam("staff") String staff){
        /*根据城市给委案分配公司ID*/
        casesService.allotStaff(JSON.parseArray(cases,Cases.class),JSON.parseObject(staff,Staff.class));
        return new AjaxReturn(0,"分配成功");
    }









}
