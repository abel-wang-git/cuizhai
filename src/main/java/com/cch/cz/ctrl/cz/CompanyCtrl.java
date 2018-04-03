package com.cch.cz.ctrl.cz;

import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.Company;
import com.cch.cz.entity.Staff;
import com.cch.cz.service.CompanyService;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 *公司管理
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyCtrl {
    @Resource
    private CompanyService companyService;

    @GetMapping(value = "/list")
    public  String list(){
        return "/cz/company/list";
    }


    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(@RequestParam int page , @RequestParam int limit){
        PageHelper.startPage(page,limit);
        Table table = new Table();
       Staff staff = (Staff) SecurityUtils.getSubject().getSession().getAttribute("staff");
       List<Company> list=companyService.listBystaff(staff);
        table.setData(list);
        table.setCount(list.size());

        return table;
    }

    @GetMapping(value = "/add")
    public String add(Model model){
        model.addAttribute("companies",companyService.findAll());
        return "/cz/company/add";
    }
    @PostMapping(value = "/add")
    @ResponseBody
    public AjaxReturn list(@RequestBody Company company){
       companyService.save(company);
       return new AjaxReturn(0,"添加成功");
    }




}
