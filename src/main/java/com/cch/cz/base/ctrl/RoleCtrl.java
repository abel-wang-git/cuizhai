package com.cch.cz.base.ctrl;

import com.cch.cz.authority.entity.Role;
import com.cch.cz.authority.service.RoleService;
import com.cch.cz.base.AjaxReturn;
import com.cch.cz.base.Table;
import com.cch.cz.entity.Staff;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2018/3/18.
 * 角色管理
 */
@Controller
@RequestMapping(value = "/role")
public class RoleCtrl {
    @Resource
    private RoleService roleService;



    @GetMapping(value = "/list")
    public  String list(Model model){
        model.addAttribute(roleService.findAll());
        return "/base/role/list";
    }

    @PostMapping(value = "/list")
    @ResponseBody
    public Table list(@RequestParam int page , @RequestParam int limit){
        PageHelper.startPage(page,limit);
        Table table = new Table();
        table.setData(roleService.findAll());
        table.setCount(roleService.count(new Role()).intValue());
        return table;
    }

    @GetMapping(value = "/add")
    @RequiresPermissions(value = "role:add")
    public String add(Model model){

        model.addAttribute(new Role());

        return "/base/role/add";
    }

    @PostMapping(value = "/add",produces="application/json;charset=UTF-8")
    @ResponseBody
    @RequiresPermissions(value = "role:add")
    public AjaxReturn add(@RequestBody Role data){
        roleService.save(data);
        return new AjaxReturn(0,"添加成功");
    }

}
