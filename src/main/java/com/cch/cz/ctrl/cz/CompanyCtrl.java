package com.cch.cz.ctrl.cz;

import com.cch.cz.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
