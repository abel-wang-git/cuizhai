package com.cch.cz.ctrl.cz;

import com.alibaba.fastjson.JSON;
import com.cch.cz.authority.entity.key.RolePowerKey;
import com.cch.cz.authority.entity.key.UserRoleKey;
import com.cch.cz.authority.service.UserService;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.Staff;
import com.cch.cz.service.StaffService;
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

    @GetMapping(value = "/list")
    public  String list(Model model){
         model.addAttribute(staffService.findAll());
        return "/cz/staff/list";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(@RequestParam int page , @RequestParam int limit){
        PageHelper.startPage(page,limit);
        Table table = new Table();
        table.setData(staffService.findAll());
        table.setCount(staffService.count(new Staff()).intValue());
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

        return "/cz/staff/add";
    }

    @PostMapping(value = "/add",produces="application/json;charset=UTF-8")
    @ResponseBody
    public AjaxReturn add(@RequestBody Staff  data){
        staffService.save(data);
        return new AjaxReturn(0,"添加成功");
    }

}
