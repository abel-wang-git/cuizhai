package com.cch.cz.ctrl.cz;

import com.cch.cz.base.Table;
import com.cch.cz.entity.Company;
import com.cch.cz.entity.Staff;
import com.cch.cz.service.CompanyService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *公司管理
 */
@Controller
@RequestMapping(value = "/company")
public class CompanyCtrl {
    @Resource
    private CompanyService companyService;

    @GetMapping(value = "/list")
    public  String list(Model model){
        model.addAttribute(companyService.findAll());
        return "/cz/company/list";
    }


    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(@RequestParam int page , @RequestParam int limit){
        PageHelper.startPage(page,limit);
        Table table = new Table();
        table.setData(companyService.findAll());
        table.setCount(companyService.count(new Company()).intValue());
        return table;
    }


}
