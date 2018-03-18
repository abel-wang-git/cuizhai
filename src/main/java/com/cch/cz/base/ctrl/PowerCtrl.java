package com.cch.cz.base.ctrl;

import com.cch.cz.authority.entity.Power;
import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.service.PowerService;
import com.cch.cz.base.Table;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/3/18.
 */
@Controller
@RequestMapping(value = "/power")
public class PowerCtrl {

    @Resource
    private PowerService powerService;
    @GetMapping(value = "/list")
    public  String list(Model model){
        return "/base/power/list";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(@RequestParam int page , @RequestParam int limit){
        PageHelper.startPage(page,limit);
        Table table = new Table();
        table.setData(powerService.findAll());
        table.setCount(powerService.count(new Power()).intValue());
        return table;
    }

}
