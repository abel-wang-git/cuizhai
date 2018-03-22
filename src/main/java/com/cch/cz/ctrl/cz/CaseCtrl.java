package com.cch.cz.ctrl.cz;

import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.Cases;
import com.cch.cz.service.CasesService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @PostMapping(value = "/allot")
    public AjaxReturn allotCase(List<Cases> data){

        casesService.expCase(data);

        return new AjaxReturn(0,"导入成功");
    }
}
